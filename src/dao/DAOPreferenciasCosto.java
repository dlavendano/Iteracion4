package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import vos.VerificarPreferenciaCosto;;

public class DAOPreferenciasCosto {
	Date fecha = new Date();
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
	public DAOPreferenciasCosto() {
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


//	/**
//	 * Metodo que, usando la conexión a la base de datos, saca todos los videos de la base de datos
//	 * <b>SQL Statement:</b> SELECT * FROM VIDEOS;
//	 * @return Arraylist con los videos de la base de datos.
//	 * @throws SQLException - Cualquier error que la base de datos arroje.
//	 * @throws Exception - Cualquier error que no corresponda a la base de datos
//	 */
//	public ArrayList<VerificarPreferenciaCosto> darPedidos() throws SQLException, Exception {
//		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
//
//		String sql = "SELECT * FROM PEDIDO";
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		ResultSet rs = prepStmt.executeQuery();
//
//		while (rs.next()) {
//			Integer numero_pedido = rs.getInt("NUMERO_PEDIDO");
//			Integer id_usuario = rs.getInt("ID_USUARIO");
//			Double precio = rs.getDouble("PRECIO");
//			Date fecha = rs.getDate("FECHA");
//			pedidos.add(new Pedido(numero_pedido, id_usuario,precio,fecha));
//		}
//		return pedidos;
//	}
//
//
//	/**
//	 * Metodo que busca el/los videos con el nombre que entra como parametro.
//	 * @param name - Nombre de el/los videos a buscar
//	 * @return ArrayList con los videos encontrados
//	 * @throws SQLException - Cualquier error que la base de datos arroje.
//	 * @throws Exception - Cualquier error que no corresponda a la base de datos
//	 */
//	public Pedido buscarPedidoPorNumero(Integer num_pedido) throws SQLException, Exception {
//		Pedido pedido = new Pedido(0,0,0.0,fecha);
//
//		String sql = "SELECT * FROM PEDIDO WHERE NUMERO_PEDIDO =" + num_pedido + "";
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		ResultSet rs = prepStmt.executeQuery();
//
//		if(rs.next()) {
//			Integer numero_pedido = rs.getInt("NUMERO_PEDIDO");
//			Integer id_usuario = rs.getInt("ID_USUARIO");
//			Double precio = rs.getDouble("PRECIO");
//			Date fecha = rs.getDate("FECHA");
//			pedido = new Pedido(numero_pedido, id_usuario, precio,fecha);
//		}
//
//		return pedido;
//	}


	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addPreferencia(VerificarPreferenciaCosto preferencia) throws SQLException, Exception {

		String sql = "INSERT INTO PREFERENCIA_PRECIO (ID_USUARIO ,PRECIO ) VALUES (";
		sql += preferencia.getUsuario().getId() + ",";
		sql += preferencia.getCosto() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

//	/**
//	 * Metodo que actualiza el video que entra como parametro en la base de datos.
//	 * @param video - el video a actualizar. video !=  null
//	 * <b> post: </b> se ha actualizado el video en la base de datos en la transaction actual. pendiente que el video master
//	 * haga commit para que los cambios bajen a la base de datos.
//	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
//	 * @throws Exception - Cualquier error que no corresponda a la base de datos
//	 */
//	public void updatePedido(Pedido pedido) throws SQLException, Exception {
//
//		String sql = "UPDATE PEDIDO SET ";
//		sql += "ID_USUARIO= " + pedido.getId_usuario() + ",";
//		sql += "PRECIO = " + pedido.getPrecio() + ",";
//		sql += "FECHA = " + pedido.getFecha();
//		sql += " WHERE NUMERO_PEDIDO = " + pedido.getNumero_pedido() + "";
//
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		prepStmt.executeQuery();
//	}
//
//	/**
//	 * Metodo que elimina el video que entra como parametro en la base de datos.
//	 * @param video - el video a borrar. video !=  null
//	 * <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
//	 * haga commit para que los cambios bajen a la base de datos.
//	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
//	 * @throws Exception - Cualquier error que no corresponda a la base de datos
//	 */
//	public void deletePedido(Pedido pedido) throws SQLException, Exception {
//
//		String sql = "DELETE FROM PEDIDO";
//		sql += " WHERE NUMERO_PEDIDO = " + pedido.getNumero_pedido()+"";
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		prepStmt.executeQuery();
//	}
}
