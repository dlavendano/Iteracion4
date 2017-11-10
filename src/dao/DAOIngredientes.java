package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Ingrediente;

public class DAOIngredientes {
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
		public DAOIngredientes() {
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
		public ArrayList<Ingrediente> darEspacios() throws SQLException, Exception {
			ArrayList<Ingrediente> Ingrediente = new ArrayList<Ingrediente>();

			String sql = "SELECT * FROM INGREDIENTE";

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				String nombre = rs.getString("NOMBRE");
				String descripcion = rs.getString("DESCRIPCION");
				Ingrediente.add(new Ingrediente(nombre, descripcion));
			}
			return Ingrediente;
		}


		/**
		 * Metodo que busca el/los videos con el nombre que entra como parametro.
		 * @param name - Nombre de el/los videos a buscar
		 * @return ArrayList con los videos encontrados
		 * @throws SQLException - Cualquier error que la base de datos arroje.
		 * @throws Exception - Cualquier error que no corresponda a la base de datos
		 */
		public Ingrediente buscarIngredientePorNombre(String name) throws SQLException, Exception {
			Ingrediente Ingrediente = new Ingrediente("","");

			String sql = "SELECT * FROM INGREDIENTE WHERE NOMBRE ='" + name + "'";

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			if(rs.next()) {
				String nombre = rs.getString("NOMBRE");
				String descripcion = rs.getString("DESCRIPCION");
				Ingrediente = new Ingrediente(nombre, descripcion);
			}

			return Ingrediente;
		}


		/**
		 * Metodo que agrega el video que entra como parametro a la base de datos.
		 * @param video - el video a agregar. video !=  null
		 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
		 * haga commit para que el video baje  a la base de datos.
		 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
		 * @throws Exception - Cualquier error que no corresponda a la base de datos
		 */
		public void addIngredientes(Ingrediente Ingrediente) throws SQLException, Exception {

			String sql = "INSERT INTO INGREDIENTE(NOMBRE, DESCRIPCION ) VALUES ('";
			sql += Ingrediente.getNombre() + "','";
			sql += Ingrediente.getDescripcion() + "')";

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
		public void updateIngredientes(Ingrediente Ingrediente) throws SQLException, Exception {

			String sql = "UPDATE INGREDIENTE SET ";
			sql += "DESCRIPCION = '" + Ingrediente.getDescripcion()+ "'";
			sql += " WHERE NOMBRE = '" + Ingrediente.getNombre() + "'";

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
		public void deleteIngrediente(Ingrediente Ingrediente) throws SQLException, Exception {

			String sql = "DELETE FROM INGREDIENTE";
			sql += " WHERE NOMBRE = '" + Ingrediente.getNombre()+ "'";

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
}
