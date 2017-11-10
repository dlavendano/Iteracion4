package dao;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;

	import vos.Menu;

	public class DAOMenus {
	/**
	* Arraylits de recursos que se usan para la ejecución de sentencias SQL
	*/
	private ArrayList<Object> recursos;

	/**
	* Atributo que genera la conexión a la base de datos
	*/
	private Connection conn;

	/**
	* Metodo constructor que crea DAORestaurantes
	* <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	*/
	public DAOMenus() {
	recursos = new ArrayList<Object>();
	}

	/**
	* Metodo que cierra todos los recursos que estan enel arreglo de recursos
	* <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	*/
	public void cerrarRecursos() {
	for(Object ob : recursos){
	if(ob instanceof PreparedStatement)
	try {
	((PreparedStatement) ob).close();
	} catch (Exception ex) {
	ex.printStackTrace();
	}
	}
	}

	/**
	* Metodo que inicializa la connection del DAO a la base de datos con la conexión que entra como parametro.
	* @param con  - connection a la base de datos
	*/
	public void setConn(Connection con){
	this.conn = con;
	}


	/**
	* Metodo que, usando la conexión a la base de datos, saca todos los videos de la base de datos
	* <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	* @return Arraylist con los videos de la base de datos.
	* @throws SQLException - Cualquier error que la base de datos arroje.
	* @throws Exception - Cualquier error que no corresponda a la base de datos
	*/
	public ArrayList<Menu> darMenus() throws SQLException, Exception {
	ArrayList<Menu> Menus = new ArrayList<Menu>();

	String sql = "SELECT * FROM MENU";

	PreparedStatement prepStmt = conn.prepareStatement(sql);
	recursos.add(prepStmt);
	ResultSet rs = prepStmt.executeQuery();

	while (rs.next()) {
	String nombre = rs.getString("NOMBRE");
	Integer precio = rs.getInt("PRECIO");
	Integer costo = rs.getInt("COSTO");
	Menus.add(new Menu(nombre));
	}
	return Menus;
	}


	/**
	* Metodo que busca el/los videos con el nombre que entra como parametro.
	* @param name - Nombre de el/los videos a buscar
	* @return ArrayList con los videos encontrados
	* @throws SQLException - Cualquier erdror que la base de datos arroje.
	* @throws Exception - Cualquier error que no corresponda a la base de datos
	*/
	public Menu buscarMenuPorNombre(String name) throws SQLException, Exception {
	Menu Menu = new Menu("");

	String sql = "SELECT * FROM MENU WHERE NOMBRE ='" + name + "'";

	PreparedStatement prepStmt = conn.prepareStatement(sql);
	recursos.add(prepStmt);
	ResultSet rs = prepStmt.executeQuery();

	if(rs.next()) {
	String nombre = rs.getString("NOMBRE");

	Menu = new Menu(nombre);
	}

	return Menu;
	}


	/**
	* Metodo que agrega el video que entra como parametro a la base de datos.
	* @param video - el video a agregar. video !=  null
	* <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	* haga commit para que el video baje  a la base de datos.
	* @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	* @throws Exception - Cualquier error que no corresponda a la base de datos
	*/
	public void addMenus(Menu Menu) throws SQLException, Exception {

	String sql = "INSERT INTO MENU(NOMBRE ) VALUES ('";
	sql += Menu.getNombre() + "')";

	PreparedStatement prepStmt = conn.prepareStatement(sql);
	recursos.add(prepStmt);
	prepStmt.executeQuery();

	}

	

	/**
	* Metodo que elimina el video que entra como parametro en la base de datos.
	* @param video - el video a borrar. video !=  null
	* <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
	* haga commit para que los cambios bajen a la base de datos.
	* @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	* @throws Exception - Cualquier error que no corresponda a la base de datos
	*/
	public void deleteMenu(Menu Menu) throws SQLException, Exception {

	String sql = "DELETE FROM MENU";
	sql += " WHERE NOMBRE = '" + Menu.getNombre()+ "'";

	PreparedStatement prepStmt = conn.prepareStatement(sql);
	recursos.add(prepStmt);
	prepStmt.executeQuery();
	}
	}