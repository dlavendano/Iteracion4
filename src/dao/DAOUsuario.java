package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.omg.PortableInterceptor.AdapterManagerIdHelper;

import vos.Usuario;
import vos.VerificacionUsuario;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicaci贸n
 * @author Monitores 2017-20
 */
public class DAOUsuario {


	/**
	 * Arraylits de recursos que se usan para la ejecuci贸n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexi贸n a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOUsuario() {
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
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexi贸n que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}


	/**
	 * Metodo que, usando la conexi贸n a la base de datos, saca todos los videos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Usuario> darUsuarios() throws SQLException, Exception {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		String sql = "SELECT * FROM USUARIO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Integer id = rs.getInt("ID");
			String name = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO");
			Integer identificacion = rs.getInt("IDENTIFICACION");
			Integer rol = rs.getInt("ROL");
         	String contra = rs.getString("CONTRASE袮");
			
			usuarios.add(new Usuario(id, name, identificacion, correo, rol,contra));
		}
		return usuarios;
	}


	/**
	 * Metodo que busca el/los videos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Usuario> buscarUsuariosPorNombre(String name) throws SQLException, Exception {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		String sql = "SELECT * FROM USUARIO WHERE NOMBRE ='" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Integer id = rs.getInt("ID");
			String name2 = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO");
			Integer identificacion = rs.getInt("IDENTIFICACION");
			Integer rol = rs.getInt("ROL");
			String contra = rs.getString("CONTRASE袮");
			usuarios.add(new Usuario(id, name2,identificacion,correo,rol,contra));
		}
		return usuarios;
	}

	/**
	 * Metodo que busca el video con el id que entra como parametro.
	 * @param name - Id de el video a buscar
	 * @return Video encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Usuario buscarUsuarioPorId(Integer id) throws SQLException, Exception 
	{
		Usuario usuario = null;

		String sql = "SELECT * FROM USUARIO WHERE ID =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Integer id2 = rs.getInt("ID");
			String name = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO");
			Integer identificacion = rs.getInt("IDENTIFICACION");
			Integer rol = rs.getInt("ROL");
			String contra = rs.getString("CONTRASE袮");
			usuario = new Usuario(id2, name,identificacion,correo,rol,contra);
		}
		return usuario;
	}

	
	public ArrayList<Usuario> buscarUsuariosPorRol(Integer rol) throws SQLException, Exception 
	{
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		String sql = "SELECT * FROM USUARIO WHERE ROL =" + rol;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()) {
			Integer id = rs.getInt("ID");
			String name = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO");
			Integer identificacion = rs.getInt("IDENTIFICACION");
			Integer rol2 = rs.getInt("ROL");
			String contra = rs.getString("CONTRASE袮");
			usuarios.add(new Usuario(id, name,identificacion,correo,rol2,contra));
		}
		return usuarios;
	}

	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addUsuario(Usuario user) throws SQLException, Exception {
		
		String sql = "INSERT INTO USUARIO VALUES (";
		sql += user.getId() + ",'";
		sql += user.getNombre() + "',";
		sql += user.getIdentificacion() + ",'";
		sql += user.getCorreo() + "',";
		sql += user.getRol() + ",'";
		sql += user.getContrase馻() +"')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Metodo que actualiza el video que entra como parametro en la base de datos.
	 * @param video - el video a actualizar. video !=  null
	 * <b> post: </b> se ha actualizado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateUsuario(Usuario usuario) throws SQLException, Exception {

		String sql = "UPDATE USUARIO SET ";
		sql += "NOMBRE='" + usuario.getNombre() + "',";
		sql += "IDENTIFICACION=" + usuario.getIdentificacion()+ ",";
		sql += "CORREO= ' " + usuario.getCorreo()+"',";
		sql += "ROL=" + usuario.getRol() +",";
		sql += "CONTRASE袮 = '" +usuario.getContrase馻() + "'";
		sql += " WHERE ID = " + usuario.getId();


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
	public void deleteUsuario(Usuario usuario) throws SQLException, Exception {

		String sql = "DELETE FROM USUARIO";
		sql += " WHERE ID = " + usuario.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}


