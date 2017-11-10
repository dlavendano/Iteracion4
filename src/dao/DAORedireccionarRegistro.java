package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Redireccionar;
import vos.Usuario;

public class DAORedireccionarRegistro {
	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAORedireccionarRegistro() {
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
	
	public ArrayList<Redireccionar> darRedireccion() throws SQLException, Exception {
		ArrayList<Redireccionar> registro = new ArrayList<Redireccionar>();
		ArrayList<String> urls = new ArrayList<String>();
		String sql = "SELECT * FROM URL_REGISTRO WHERE ID_ROL = 1"; 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while (rs.next()) {
			
			String url = rs.getString("URL");
			urls.add(url);
		}
		registro.add(new Redireccionar(urls,1));
		ArrayList<String> urls2 = new ArrayList<String>();
		String sql2 = "SELECT * FROM URL_REGISTRO WHERE ID_ROL = 2"; 

		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs2 = prepStmt2.executeQuery();
		
		while (rs2.next()) {
			
			String url = rs2.getString("URL");
			urls2.add(url);
		}
		registro.add(new Redireccionar(urls2,2));
		ArrayList<String> urls3 = new ArrayList<String>();
		String sql3 = "SELECT * FROM URL_REGISTRO WHERE ID_ROL = 3"; 

		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		recursos.add(prepStmt3);
		ResultSet rs3 = prepStmt3.executeQuery();
		
		while (rs3.next()) {
			
			String url = rs3.getString("URL");
			urls3.add(url);
		}
		registro.add(new Redireccionar(urls3,3));
		
		return registro;
	}
	
}
