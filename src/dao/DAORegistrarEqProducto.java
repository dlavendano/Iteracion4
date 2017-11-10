package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Producto;
import vos.RegistrarEqProducto;

public class DAORegistrarEqProducto {
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
	public DAORegistrarEqProducto() {
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

	public void addEquivalencia(String producto,String equivalente) throws Exception{

		String nombreR = "";
		String nombreRE = "";
		
		String sql2 = "SELECT * FROM PRODUCTO_RESTAURANTE WHERE NOMBRE_PRODUCTO = '"+ producto +"'";
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs = prepStmt2.executeQuery();
		if(rs.next()){
			nombreR = rs.getString("NOMBRE_RESTAURANTE");
		}
		String sql3 = "SELECT * FROM PRODUCTO_RESTAURANTE WHERE NOMBRE_PRODUCTO = '"+ equivalente +"'";
		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		recursos.add(prepStmt3);
		ResultSet rs2 = prepStmt3.executeQuery();
		if(rs2.next()){
			nombreRE = rs2.getString("NOMBRE_RESTAURANTE");
		}
		if(nombreR.equals(nombreRE)){
			String sql = "INSERT INTO EQUIVALENCIA_PRODUCTOS(PRODUCTO,EQUIVALENTE,RESTAURANTE) VALUES ('";

			sql += producto+"','";
			sql += equivalente +"','";
			sql += nombreR +"')";

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
		else{
			throw new Exception("El producto equivalente es de un restaurante diferente");
		}
	}
	public List<String> buscarEquivalenciasProducto(String nombreProducto,String nombreRestaurante) throws SQLException{
		ArrayList<String> equiv= new ArrayList<String>();
		
		String sql = "SELECT EQUIVALENTE FROM EQUIVALENCIA_PRODUCTOS WHERE PRODUCTO = '"+ nombreProducto + "' AND RESTAURANTE ='" + nombreRestaurante +"'";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next()) {
			String equivalencia = rs.getString("EQUIVALENTE");
			equiv.add(equivalencia);
		}
		return equiv;
	}
}
