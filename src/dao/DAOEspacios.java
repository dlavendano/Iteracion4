package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Espacios;
import vos.Restaurante;

public class DAOEspacios {
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
	public DAOEspacios() {
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
	public ArrayList<Espacios> darEspacios() throws SQLException, Exception {
		ArrayList<Espacios> espacios = new ArrayList<Espacios>();

		String sql = "SELECT * FROM ESPACIO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Integer id_zona = rs.getInt("ID_ZONA");
			String es_abierto = rs.getString("ES_ABIERTO");
			Integer capacidad = rs.getInt ("CAPACIDAD");
			String es_apto = rs.getString("ES_APTO");
			espacios.add(new Espacios(nombre,id_zona,es_abierto,capacidad,es_apto));
		}
		return espacios;
	}


	/**
	 * Metodo que busca el/los videos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Espacios buscarEspacioPorNombre(String name) throws SQLException, Exception {
		Espacios espacios = new Espacios("",0,"F",0,"F");

		String sql = "SELECT * FROM ESPACIO WHERE NOMBRE = '" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Integer id_zona = rs.getInt("ID_ZONA");
			String es_abierto = rs.getString("ES_ABIERTO");
			Integer capacidad = rs.getInt ("CAPACIDAD");
			String es_apto = rs.getString("ES_APTO");
			espacios = new Espacios(nombre,id_zona,es_abierto,capacidad,es_apto);
		}

		return espacios;
	}


	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addEspacios(Espacios espacios) throws SQLException, Exception {

		String sql = "INSERT INTO ESPACIO (NOMBRE,ID_ZONA,ES_ABIERTO,CAPACIDAD, ES_APTO ) VALUES ('";
		sql += espacios.getNombre() + "',";
		sql += espacios.getId_zona() +",'";
		sql += espacios.isEs_abierto()+ "',";
		sql += espacios.getCapacidad() + ",'";
		sql += espacios.getEs_apto() + "')";

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
	public void updateEspacio(Espacios espacios) throws SQLException, Exception {

		String sql = "UPDATE ESPACIO SET ";
		sql += "ES_APTO = '" + espacios.getEs_apto()+ "',";
		sql += "ID_ZONA = "+ espacios.getId_zona() +",";
		sql += "ES_ABIERTO = '" + espacios.isEs_abierto()+ "',";
		sql += "CAPACIDAD = " + espacios.getCapacidad();
		sql += " WHERE NOMBRE = '" + espacios.getNombre()+ "'";


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
	public void deleteEspacio(Espacios Espacios) throws SQLException, Exception {

		String sql = "DELETE FROM ESPACIO";
		sql += " WHERE NOMBRE = '" + Espacios.getNombre()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
