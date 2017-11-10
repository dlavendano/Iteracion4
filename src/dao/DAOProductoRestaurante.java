package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Producto;
import vos.ProductoRestaurante;

public class DAOProductoRestaurante {

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
	public DAOProductoRestaurante() {
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
	 * Metodo que ag rega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addProductoRestaurante(ProductoRestaurante agregar) throws SQLException, Exception {


		String sql = "INSERT INTO Producto_Restaurante(nombre_restaurante,nombre_producto,costo_preparacion, precio_venta, disponibilidad, maximo) VALUES ('";
		sql += agregar.getNombreRestaurante()+"','";
		sql += agregar.getNombreProducto() + "',";
		sql += agregar.getCosto() + ",";
		sql += agregar.getValor() + ",";
		sql += agregar.getDisponibilidad() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	public int darCosto(String nombreProducto, String nombreRestaurante) throws SQLException, Exception{



		String sql = "SELECT * FROM PRODUCTO_RESTAURANTE WHERE NOMBRE_RESTAURANTE = '"+ nombreRestaurante+"' AND NOMBRE_PRODUCTO = '"+nombreProducto+"';";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		ResultSet rs = prepStmt.executeQuery();

		int costo=0;

		while (rs.next()) {
			costo = rs.getInt("COSTO_PREPARACION");
		}
		return costo;
	}
	public Integer darPrecio(String nombreProducto, String nombreRestaurante) throws SQLException, Exception{
		String sql = "SELECT * FROM PRODUCTO_RESTAURANTE WHERE NOMBRE_RESTAURANTE = '"+nombreRestaurante+"' AND NOMBRE_PRODUCTO = '"+nombreProducto+"'";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		ResultSet rs = prepStmt.executeQuery();
		Integer precio=0;
		
		if (rs.next())
			precio = rs.getInt("PRECIO_VENTA");
		System.out.println(precio);
		return precio;
	}
	public void surtir(String restaurante)throws SQLException, Exception {
		String sql = "UPDATE PRODUCTO_RESTAURANTE SET DISPONIBILIDAD = MAXIMO WHERE NOMBRE_RESTAURANTE = '"+restaurante+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
