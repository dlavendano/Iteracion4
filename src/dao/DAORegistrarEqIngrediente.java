package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Producto;
import vos.RegistrarEqProducto;

public class DAORegistrarEqIngrediente {
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
	public DAORegistrarEqIngrediente() {
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

	public void addEquivalencia(String ingrediente,String equivalente,String restaurante) throws Exception{

			String sql = "INSERT INTO EQUIVALENCIA_INGREDIENTE(INGREDIENTE,EQUIVALENTE,RESTAURANTE) VALUES ('";

			sql += ingrediente+"','";
			sql += equivalente +"','";
			sql += restaurante +"')";

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		
	}
	public List<String> buscarEquivalenciasIngrediente(String nombreIngrediente,String nombreRestaurante) throws SQLException{
		ArrayList<String> equiv= new ArrayList<String>();
		
		String sql = "SELECT EQUIVALENTE FROM EQUIVALENCIA_INGREDIENTE WHERE  = '"+ nombreIngrediente + "' AND RESTAURANTE ='" + nombreRestaurante +"'";
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
