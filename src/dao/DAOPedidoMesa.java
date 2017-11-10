package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOPedidoMesa {
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAORestaurantes
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOPedidoMesa() {
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
	public void addPedidoMesa(Integer mesa, Integer numeroPedido) throws SQLException, Exception {

		String sql = "INSERT INTO PEDIDO_MESA(PEDIDO_MESA, NUMERO_PEDIDO ) VALUES ('";
		sql += mesa+"','";
		sql += numeroPedido+"')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public ArrayList<Integer> darPedidosMesa(Integer numero_pedido_mesa)throws SQLException, Exception  {
		String sql = "SELECT * FROM PEDIDO_MESA WHERE PEDIDO_MESA = "+numero_pedido_mesa;
		ArrayList<Integer>  pedidos = new ArrayList<Integer>();
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			pedidos.add(rs.getInt("NUMERO_PEDIDO"));
		}
		return pedidos;
	}
}

