package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import vos.Pedido;
import vos.ServirProducto;
import vos.Usuario;


public class DAOPedidos {
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
	public DAOPedidos() {
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
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param id_cliente 
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addPedido(Pedido pedido, Integer idCliente) throws SQLException, Exception {

		Integer disponibilidad=0;	
		Integer precio = 0;
		String sql = "SELECT * FROM PRODUCTO_RESTAURANTE WHERE NOMBRE_RESTAURANTE = '"+ pedido.getNombreRestaurante()+"' AND NOMBRE_PRODUCTO = '"+pedido.getNombreProducto()+"'";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if (rs.next()) {
			disponibilidad = rs.getInt("DISPONIBILIDAD");
			precio = rs.getInt("PRECIO_VENTA");
		}
		if(disponibilidad > 0)
		{
			String sql2 = "INSERT INTO PEDIDO (NUMERO_PEDIDO,ID_USUARIO,PRECIO,FECHA,NOMBRE_PRODUCTO, NOMBRE_RESTAURANTE,SERVIDO,ESTADO) VALUES (";
			sql2 += pedido.getNumero_pedido() + ",";
			sql2 += idCliente + ",";
			sql2 += precio + ",";
			sql2 += "SYSDATE,'";
			sql2 += pedido.getNombreProducto()+"','";
			sql2 += pedido.getNombreRestaurante()+"','F','";
			sql2 += "normal')";

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			prepStmt2.executeQuery();
		}else {
			throw new Exception("No hay productos disponibles");
		}

	}
	public void addPedidoCompProducto(Pedido pedido, Integer idCliente) throws SQLException, Exception {

		Integer disponibilidad=0;	
		Integer precio = 0;
		String sql = "SELECT * FROM PRODUCTO_RESTAURANTE WHERE NOMBRE_RESTAURANTE = '"+ pedido.getNombreRestaurante()+"' AND NOMBRE_PRODUCTO = '"+pedido.getNombreProducto()+"'";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if (rs.next()) {
			disponibilidad = rs.getInt("DISPONIBILIDAD");
			precio = rs.getInt("PRECIO_VENTA");
		}
		if(disponibilidad > 0)
		{

			String sql2 = "INSERT INTO PEDIDO (NUMERO_PEDIDO,ID_USUARIO,PRECIO,FECHA,NOMBRE_PRODUCTO, NOMBRE_RESTAURANTE,SERVIDO) VALUES (";
			sql2 += pedido.getNumero_pedido() + ",";
			sql2 += idCliente + ",";
			sql2 += precio + ",";
			sql2 += "SYSDATE,'";
			sql2 += pedido.getNombreProducto()+"','";
			sql2 += pedido.getNombreRestaurante()+"','F')";

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			prepStmt2.executeQuery();
			
			//Agrego datos de las equivalencias
			for(int i = 0; i< pedido.getComponentes().size(); i++) {
				String[] parte = pedido.getComponentes().get(i).split(","); 

				String sql3 = "INSERT INTO PEDIDO_EQUIV_PRODUCTO(NUM_PEDIDO,PRODUCTO,INGREDIENTE,EQUIVALENCIA) VALUES(";
				sql3 += pedido.getNumero_pedido() + ",'";
				sql3 += pedido.getNombreProducto() +"','";
				sql3 += parte[0] + "','";
				sql3 += parte[1] + "')";

				PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
				recursos.add(prepStmt3);
				prepStmt3.executeQuery();
			}
		}else {
			throw new Exception("No hay productos disponibles");
		}


	}
	public void addPedidoCompMenu(Pedido pedido, Integer idCliente) throws SQLException, Exception {

		Integer disponibilidad=0;	
		Integer precio = 0;
		String sql = "SELECT * FROM PRODUCTO_RESTAURANTE WHERE NOMBRE_RESTAURANTE = '"+ pedido.getNombreRestaurante()+"' AND NOMBRE_PRODUCTO = '"+pedido.getNombreProducto()+"'";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if (rs.next()) {
			disponibilidad = rs.getInt("DISPONIBILIDAD");
			precio = rs.getInt("PRECIO_VENTA");
		}
		if(disponibilidad > 0)
		{

			String sql2 = "INSERT INTO PEDIDO (NUMERO_PEDIDO,ID_USUARIO,PRECIO,FECHA,NOMBRE_PRODUCTO, NOMBRE_RESTAURANTE,SERVIDO) VALUES (";
			sql2 += pedido.getNumero_pedido() + ",";
			sql2 += idCliente + ",";
			sql2 += precio + ",";
			sql2 += "SYSDATE,'";
			sql2 += pedido.getNombreProducto()+"','";
			sql2 += pedido.getNombreRestaurante()+"','F')";

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			prepStmt2.executeQuery();
			
			//Agrego datos de las equivalencias
			for(int i = 0; i< pedido.getComponentes().size(); i++) {
				String[] parte = pedido.getComponentes().get(i).split(","); 

				String sql3 = "INSERT INTO PEDIDO_EQUIV_MENU(NUM_PEDIDO,MENU,PRODUCTO,EQUIVALENCIA) VALUES(";
				sql3 += pedido.getNumero_pedido() + ",'";
				sql3 += pedido.getNombreProducto() +"','";
				sql3 += parte[0] + "','";
				sql3 += parte[1] + "')";

				PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
				recursos.add(prepStmt3);
				prepStmt3.executeQuery();
			}
		}else {
			throw new Exception("No hay menus disponibles");
		}


	}

	/**
	 * Metodo que actualiza el video que entra como parametro en la base de datos.
	 * @param video - el video a actualizar. video !=  null
	 * <b> post: </b> se ha actualizado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updatePedido(Integer numero_pedido,Boolean servido) throws SQLException, Exception {
		String bServido = "F";
		if(servido == true){
			bServido = "T";
			String nombreR = "";
			String nombreP= "";
			Integer dis = 0;

			String sql2 = "SELECT * FROM PRODUCTO_RESTAURANTE R JOIN (SELECT * FROM PEDIDO WHERE NUMERO_PEDIDO =" +numero_pedido +") P ON R.NOMBRE_PRODUCTO = P.NOMBRE_PRODUCTO AND R.NOMBRE_RESTAURANTE = P.NOMBRE_RESTAURANTE";
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs1 = prepStmt2.executeQuery();
			if (rs1.next()) {
				nombreR = rs1.getString("NOMBRE_RESTAURANTE");
				nombreP = rs1.getString("NOMBRE_PRODUCTO");
				dis = rs1.getInt("DISPONIBILIDAD");
			}
			if(dis > 0){
				String sql3 = "UPDATE PRODUCTO_RESTAURANTE SET DISPONIBILIDAD = DISPONIBILIDAD-1 WHERE NOMBRE_RESTAURANTE = '" + nombreR + "' AND NOMBRE_PRODUCTO = '"+ nombreP +"'";
				PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
				recursos.add(prepStmt3);
				prepStmt3.executeQuery();
			}else{
				throw new Exception("No hay productos disponibles");
			}
		}
		String sql = "UPDATE PEDIDO SET ";
		sql += "SERVIDO = '"+ bServido + "'";
		sql += " WHERE NUMERO_PEDIDO = " + numero_pedido + "AND ESTADO = 'normal'";
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
	public void deletePedido(Pedido pedido) throws SQLException, Exception {

		String sql = "DELETE FROM PEDIDO";
		sql += " WHERE NUMERO_PEDIDO = " + pedido.getNumero_pedido()+"";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	public void cancelarPedido(Integer pedido, Integer idCliente)throws SQLException, Exception  {
		String sql2 = "SELECT * FROM PEDIDO WHERE NUMERO_PEDIDO = " + pedido+" AND ID_USUARIO = "+idCliente; 
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs1 =prepStmt2.executeQuery();
		
		if(rs1.next()) {
			if(rs1.getString("SERVIDO").equals("F")){
				String sql = "UPDATE PEDIDO SET ESTADO = 'CANCELADO'";
				sql += " WHERE NUMERO_PEDIDO = " + pedido+"AND ID_USUARIO = "+idCliente;

				PreparedStatement prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);
				prepStmt.executeQuery();
			}
			else{
				throw new Exception("El pedido ya fue servido");
			}
		}
		
	}
}
