package tm;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.omg.CORBA.Any;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.DynamicAny.DynAny;
import org.omg.DynamicAny.DynArrayOperations;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;

import dao.DAOCategorias;
import dao.DAOEspacios;
import dao.DAOIngredientes;
import dao.DAOMenuProducto;
import dao.DAOMenus;
import dao.DAOPedidoMesa;
import dao.DAOPedidos;
import dao.DAOPreferenciasCosto;
import dao.DAOPreferenciasZona;
import dao.DAOProductoIngrediente;
import dao.DAOProductoRestaurante;
import dao.DAOProductoVenta;
import dao.DAOProductos;
import dao.DAORedireccionarRegistro;
import dao.DAORegistrarEqIngrediente;
import dao.DAORegistrarEqProducto;
import dao.DAORestaurantes;
import dao.DAOZonas;
import dao.DAOUsuario;
import dao.DAOZonas;
import vos.Categoria;
import vos.Espacios;
import vos.Ingrediente;
import vos.Menu;
import vos.MenuProducto;
import vos.Pedido;
import vos.Producto;
import vos.ProductoIngrediente;
import vos.ProductoRestaurante;
import vos.Redireccionar;
import vos.RegistrarEqProducto;
import vos.Restaurante;
import vos.ServirProducto;
import vos.Usuario;
import vos.VerificacionProducto;
import vos.VerificarPreferenciaCosto;
import vos.VerificarPreferenciaZona;
import vos.Zonas;
public class RotondAndesTM {
	/**
	 * Atributo estatico que contiene el path relativo del archivo que tiene los datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los datos de la conexion
	 */
	private  String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;

	/**
	 * conexion a la base de datos
	 */
	private Connection conn;


	/**
	 * Metodo constructor de la clase ZonasAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto ZonasAndesTM, se inicializa el path absoluto del archivo de conexion y se
	 * inicializa los atributos que se usan par la conexion a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public  RotondAndesTM (String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	/**
	 * Metodo que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexion a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que  retorna la conexion a la base de datos
	 * @return Connection - la conexion a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexion a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	////////////////////////////////////////
	///////Transacciones////////////////////
	////////////////////////////////////////


	/**
	 * Metodo que modela la transaccion que retorna todos los Zonas de la base de datos.
	 * @return ListaZonas - objeto que modela  un arreglo de Zonas. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Restaurante> darRestaurantes() throws Exception {
		List<Restaurante> restaurantes;
		DAORestaurantes daoResta = new DAORestaurantes();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoResta.setConn(conn);
			restaurantes = daoResta.darRestaurantes();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoResta.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return restaurantes;
	}

	public List<Categoria> darCategorias() throws Exception {
		List<Categoria> categorias;
		DAOCategorias daoCategoria = new DAOCategorias();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoCategoria.setConn(conn);
			categorias = daoCategoria.darCategorias();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCategoria.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return categorias;
	}

	public List<Espacios> darEspacios() throws Exception {
		List<Espacios> espacios;
		DAOEspacios daoEspacios = new DAOEspacios();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoEspacios.setConn(conn);
			espacios = daoEspacios.darEspacios();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEspacios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return espacios;
	}

	public List<Ingrediente> darIngredientes() throws Exception {
		List<Ingrediente> ingredientes;
		DAOIngredientes daoIngredientes = new DAOIngredientes();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			ingredientes = daoIngredientes.darEspacios();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ingredientes;
	}

	//	public List<Menu> darMenus() throws Exception {
	//		List<Menu> Menu;
	//		DAOMenus daoMenus = new DAOMenus();
	//		try 
	//		{
	//			//////transaccion
	//			this.conn = darConexion();
	//			daoMenus.setConn(conn);
	//			Menu = daoMenus.darEspacios();
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoMenus.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//		return Menu;
	//	}

	public List<Producto> darProductos() throws Exception {
		List<Producto> Producto;
		DAOProductos daoProductos = new DAOProductos();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			Producto = daoProductos.darProducto();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Producto;
	}

	public List<Pedido> darPedidos() throws Exception {
		List<Pedido> Producto;
		DAOPedidos daoPedidos = new DAOPedidos();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			Producto = daoPedidos.darPedidos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Producto;
	}

	//	public List<ProductoRestaurante> darProductoRestaurante() throws Exception {
	//		List<ProductoRestaurante> ProductoRestaurante;
	//		DAOProductosRestaurante daoProductosRestaurantes = new DAOProductosRestaurante();
	//		try 
	//		{
	//			//////transaccion
	//			this.conn = darConexion();
	//			daoProductosRestaurantes.setConn(conn);
	//			ProductoRestaurante = daoProductosRestaurantes.darProductoRestaurante();
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoProductosRestaurantes.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//		return ProductoRestaurante;
	//	}

	/**
	 * Metodo que modela la transaccion que busca el/los Zonas en la base de datos con el nombre entra como parametro.
	 * @param name - Nombre del Zonas a buscar. name != null
	 * @return ListaZonas - objeto que modela  un arreglo de Zonas. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Restaurante buscarRestaurantesPorNombre(String name) throws Exception {
		Restaurante restaurante;
		DAORestaurantes daoResta = new DAORestaurantes();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoResta.setConn(conn);
			restaurante = daoResta.buscarRestaurantePorNombre(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoResta.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return restaurante;
	}
	/**
	 * Metodo que modela la transaccion que busca el/los Zonas en la base de datos con el nombre entra como parametro.
	 * @param name - Nombre del Zonas a buscar. name != null
	 * @return ListaZonas - objeto que modela  un arreglo de Zonas. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Categoria buscarCategoriasPorId(Integer id) throws Exception {
		Categoria categoria;
		DAOCategorias daoCategorias = new DAOCategorias();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoCategorias.setConn(conn);
			categoria = daoCategorias.buscarCategoriaPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCategorias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return categoria;
	}
	/**
	 * Metodo que modela la transaccion que busca el/los Zonas en la base de datos con el nombre entra como parametro.
	 * @param name - Nombre del Zonas a buscar. name != null
	 * @return ListaZonas - objeto que modela  un arreglo de Zonas. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Espacios buscarEspaciosPorNombre(String name) throws Exception {
		Espacios espacio;
		DAOEspacios daoEspacios = new DAOEspacios();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoEspacios.setConn(conn);
			espacio = daoEspacios.buscarEspacioPorNombre(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEspacios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return espacio;
	}

	/**
	 * Metodo que modela la transaccion que busca el/los Zonas en la base de datos con el nombre entra como parametro.
	 * @param name - Nombre del Zonas a buscar. name != null
	 * @return ListaZonas - objeto que modela  un arreglo de Zonas. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Ingrediente buscarIngredientesPorNombre(String name) throws Exception {
		Ingrediente Ingrediente;
		DAOIngredientes daoIngredientes = new DAOIngredientes();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			Ingrediente = daoIngredientes.buscarIngredientePorNombre(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Ingrediente;
	}

	/**
	 * Metodo que modela la transaccion que busca el/los Zonas en la base de datos con el nombre entra como parametro.
	 * @param name - Nombre del Zonas a buscar. name != null
	 * @return ListaZonas - objeto que modela  un arreglo de Zonas. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	//	public Menu buscarMenuPorNombre(String name) throws Exception {
	//		Menu Menu;
	//		DAOMenus daoMenus = new DAOMenus();
	//		try 
	//		{
	//			//////transaccion
	//			this.conn = darConexion();
	//			daoMenus.setConn(conn);
	//			Menu = daoMenus.buscarMenuPorNombre(name);
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoMenus.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//		return Menu;
	//	}
	/**
	 * Metodo que modela la transaccion que busca el/los Zonas en la base de datos con el nombre entra como parametro.
	 * @param name - Nombre del Zonas a buscar. name != null
	 * @return ListaZonas - objeto que modela  un arreglo de Zonas. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Producto buscarProductoPorNombre(String name) throws Exception {
		Producto Producto;
		DAOProductos daoProductos = new DAOProductos();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			Producto = daoProductos.buscarProductoPorNombre(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Producto;
	}

	/**
	 * Metodo que modela la transaccion que busca el/los Zonas en la base de datos con el nombre entra como parametro.
	 * @param name - Nombre del Zonas a buscar. name != null
	 * @return ListaZonas - objeto que modela  un arreglo de Zonas. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Pedido buscarPedidoPorNumero(Integer numero_pedido) throws Exception {
		Pedido Pedido;
		DAOPedidos daoPedidos = new DAOPedidos();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			Pedido = daoPedidos.buscarPedidoPorNumero(numero_pedido);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Pedido;
	}
	/**
	 * Metodo que modela la transaccion que busca el/los Zonas en la base de datos con el nombre entra como parametro.
	 * @param name - Nombre del Zonas a buscar. name != null
	 * @return ListaZonas - objeto que modela  un arreglo de Zonas. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	//	public ProductoRestaurante buscarProductoRestaurante(String name) throws Exception {
	//		Producto Producto;
	//		DAOProductos daoProductos = new DAOProductos();
	//		try 
	//		{
	//			//////transaccion
	//			this.conn = darConexion();
	//			daoProductos.setConn(conn);
	//			Producto = daoProductos.buscarProductoPorNombre(name);
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoProductos.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//		return Producto;
	//	}

	/**
	 * Metodo que modela la transaccion que agrega un solo Zonas a la base de datos.
	 * <b> post: </b> se ha agregado el Zonas que entra como parametro
	 * @param Zonas - el Zonas a agregar. Zonas != null
	 * @throws Exception - cualquier error que se genere agregando el Zonas
	 */
	public void addRestaurante(Restaurante restaurante) throws Exception {
		DAORestaurantes daoResta = new DAORestaurantes();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoResta.setConn(conn);
			daoResta.addRestaurante(restaurante);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoResta.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que agrega un solo Zonas a la base de datos.
	 * <b> post: </b> se ha agregado el Zonas que entra como parametro
	 * @param Zonas - el Zonas a agregar. Zonas != null
	 * @throws Exception - cualquier error que se genere agregando el Zonas
	 */
	public void addCategoria(Categoria categoria) throws Exception {
		DAOCategorias daoCategoria = new DAOCategorias();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoCategoria.setConn(conn);
			daoCategoria.addCategoria(categoria);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCategoria.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que agrega un solo Zonas a la base de datos.
	 * <b> post: </b> se ha agregado el Zonas que entra como parametro
	 * @param Zonas - el Zonas a agregar. Zonas != null
	 * @throws Exception - cualquier error que se genere agregando el Zonas
	 */
	public void addEspacio(Espacios espacios) throws Exception {
		DAOEspacios daoEspacio = new DAOEspacios();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoEspacio.setConn(conn);
			daoEspacio.addEspacios(espacios);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEspacio.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que agrega un solo Zonas a la base de datos.
	 * <b> post: </b> se ha agregado el Zonas que entra como parametro
	 * @param Zonas - el Zonas a agregar. Zonas != null
	 * @throws Exception - cualquier error que se genere agregando el Zonas
	 */
	public void addIngrediente(Ingrediente Ingrediente) throws Exception {
		DAOIngredientes daoIngrediente = new DAOIngredientes();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoIngrediente.setConn(conn);
			daoIngrediente.addIngredientes(Ingrediente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngrediente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que agrega un solo Zonas a la base de datos.
	 * <b> post: </b> se ha agregado el Zonas que entra como parametro
	 * @param Zonas - el Zonas a agregar. Zonas != null
	 * @throws Exception - cualquier error que se genere agregando el Zonas
	 */
	//	public void addMenu(Menu Menu) throws Exception {
	//		DAOMenus daoMenu = new DAOMenus();
	//		try 
	//		{
	//			//////transaccion
	//			this.conn = darConexion();
	//			daoMenu.setConn(conn);
	//			daoMenu.addMenu(Menu);
	//			conn.commit();
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoMenu.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//	}
	/**
	 * Metodo que modela la transaccion que agrega un solo Zonas a la base de datos.
	 * <b> post: </b> se ha agregado el Zonas que entra como parametro
	 * @param Zonas - el Zonas a agregar. Zonas != null
	 * @throws Exception - cualquier error que se genere agregando el Zonas
	 */
	public void addProducto(Producto Producto) throws Exception {
		DAOProductos daoProductos = new DAOProductos();
		DAOProductoRestaurante daoProductoRestaurante = new DAOProductoRestaurante();
		try 
		{
			//////transaccion
			Producto prod= null;
			this.conn = darConexion();
			daoProductos.setConn(conn);
			daoProductos.addProductos(Producto);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que agrega un solo Zonas a la base de datos.
	 * <b> post: </b> se ha agregado el Zonas que entra como parametro
	 * @param idCliente 
	 * @param cliente 
	 * @param Zonas - el Zonas a agregar. Zonas != null
	 * @throws Exception - cualquier error que se genere agregando el Zonas
	 */

	/**
	 * Metodo que modela la transaccion que agrega un solo Zonas a la base de datos.
	 * <b> post: </b> se ha agregado el Zonas que entra como parametro
	 * @param idCliente 
	 * @param cliente 
	 * @param Zonas - el Zonas a agregar. Zonas != null
	 * @throws Exception - cualquier error que se genere agregando el Zonas
	 */
	@SuppressWarnings("unused")
	public void addPedidoEquivalencias(Pedido Pedido, Integer idCliente) throws Exception {
		DAOPedidos daoPedidos = new DAOPedidos();
		try 
		{
			//////transaccion
			List<String> equiv = buscarEquivalenciasProducto(Pedido.getNombreProducto(),Pedido.getNombreRestaurante());
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			for( int i = 0; i < equiv.size();i++) {
				if(Pedido.getEquivalencia() == null|| Pedido.getEquivalencia() == ""||Pedido.getEquivalencia().equals(equiv.get(i))) {
					daoPedidos.addPedido(Pedido,idCliente);
					conn.commit();
					break;
				}				
				else {
					throw new Exception("La equivalencia ingresada no esta registrada en ese restaurante");
				}
			}


		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void buscarEquivalenciaProducto(String producto, String restaurante) throws Exception {
		DAORegistrarEqProducto daoRegistrarEqProducto = new DAORegistrarEqProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRegistrarEqProducto.setConn(conn);
			daoRegistrarEqProducto.buscarEquivalenciasProducto(producto,restaurante);


		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRegistrarEqProducto.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	//	public void addIngredienteProducto(VerificacionProducto ingProducto) throws Exception {
	//		DAOIngredienteProducto daoIngredienteProducto = new DAOIngredienteProducto();
	//		try 
	//		{
	//			//////transaccion
	//			this.conn = darConexion();
	//			daoIngredienteProducto.setConn(conn);
	//			daoIngredienteProducto.addIngredienteProducto(ingProducto);
	//			conn.commit();
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoIngredienteProducto.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//	}
	/**
	 * Metodo que modela la transaccion que agrega un solo Zonas a la base de datos.
	 * <b> post: </b> se ha agregado el Zonas que entra como parametro
	 * @param Zonas - el Zonas a agregar. Zonas != null
	 * @throws Exception - cualquier error que se genere agregando el Zonas
	 */
	//	public void addProductoRestaurante(ProductoRestaurante ProductoRestaurante) throws Exception {
	//		DAOProductosRestaurantes daoProductosRestaurantes = new DAOProductosRestaurantes();
	//		try 
	//		{
	//			//////transaccion
	//			this.conn = darConexion();
	//			daoProductosRestaurantes.setConn(conn);
	//			daoProductosRestaurantes.addProductos(ProductoRestaurante);
	//			conn.commit();
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoProductosRestaurantes.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//	}
	/**
	 * Metodo que modela la transaccion que actualiza el Zonas que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el Zonas que entra como parametro
	 * @param Zonas - Zonas a actualizar. Zonas != null
	 * @throws Exception - cualquier error que se genera actualizando los Zonas
	 */
	public void updateRestaurante(Restaurante restaurante) throws Exception {
		DAORestaurantes daoRestaurante = new DAORestaurantes();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurante.setConn(conn);
			daoRestaurante.updateRestaurante(restaurante);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurante.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que actualiza el Zonas que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el Zonas que entra como parametro
	 * @param Zonas - Zonas a actualizar. Zonas != null
	 * @throws Exception - cualquier error que se genera actualizando los Zonas
	 */
	public void updateCategoria(Categoria categoria) throws Exception {
		DAOCategorias daoCategoria = new DAOCategorias();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoCategoria.setConn(conn);
			daoCategoria.updateCategoria(categoria);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCategoria.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que actualiza el Zonas que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el Zonas que entra como parametro
	 * @param Zonas - Zonas a actualizar. Zonas != null
	 * @throws Exception - cualquier error que se genera actualizando los Zonas
	 */
	public void updateEspacios(Espacios espacios) throws Exception {
		DAOEspacios daoEspacios = new DAOEspacios();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoEspacios.setConn(conn);
			daoEspacios.updateEspacio(espacios);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEspacios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que actualiza el Zonas que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el Zonas que entra como parametro
	 * @param Zonas - Zonas a actualizar. Zonas != null
	 * @throws Exception - cualquier error que se genera actualizando los Zonas
	 */
	public void updateIngrediente(Ingrediente Ingrediente) throws Exception {
		DAOIngredientes daoIngredientes = new DAOIngredientes();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			daoIngredientes.updateIngredientes(Ingrediente);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que actualiza el Zonas que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el Zonas que entra como parametro
	 * @param Zonas - Zonas a actualizar. Zonas != null
	 * @throws Exception - cualquier error que se genera actualizando los Zonas
	 */
	//	public void updateMenu(Menu Menu) throws Exception {
	//		DAOMenus daoMenus = new DAOMenus();
	//		try 
	//		{
	//			//////transaccion
	//			this.conn = darConexion();
	//			daoMenus.setConn(conn);
	//			daoMenus.updateMenu(Menu);
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoMenus.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//	}
	//	
	/**
	 * Metodo que modela la transaccion que actualiza el Zonas que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el Zonas que entra como parametro
	 * @param Zonas - Zonas a actualizar. Zonas != null
	 * @throws Exception - cualquier error que se genera actualizando los Zonas
	 */
	public void updateProducto(Producto Producto) throws Exception {
		DAOProductos daoProductos = new DAOProductos();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			daoProductos.updateProducto(Producto);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que actualiza el Zonas que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el Zonas que entra como parametro
	 * @param Zonas - Zonas a actualizar. Zonas != null
	 * @throws Exception - cualquier error que se genera actualizando los Zonas
	 */
	public void updatePedido(Integer numeroPedido, Boolean servido) throws Exception {
		DAOPedidos daoPedidos = new DAOPedidos();	
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			daoPedidos.updatePedido(numeroPedido,servido);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que actualiza el Zonas que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el Zonas que entra como parametro
	 * @param Zonas - Zonas a actualizar. Zonas != null
	 * @throws Exception - cualquier error que se genera actualizando los Zonas
	 */
	//	public void updateProductoRestaurante(ProductoRestaurante ProductoRestaurante) throws Exception {
	//		DAOProductosRestaurantes daoProductosRestaurantes = new DAOProductosRestaurantes();
	//		try 
	//		{
	//			//////transaccion
	//			this.conn = darConexion();
	//			daoProductosRestaurantes.setConn(conn);
	//			daoProductosRestaurantes.updateProducto(ProductoRestaurante);
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoProductosRestaurantes.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//	}
	/**
	 * Metodo que modela la transaccion que elimina el Zonas que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el Zonas que entra como parametro
	 * @param Zonas - Zonas a eliminar. Zonas != null
	 * @throws Exception - cualquier error que se genera actualizando los Zonas
	 */
	public void deleteRestaurante(Restaurante restaurante) throws Exception {
		DAORestaurantes daoRestaurantes = new DAORestaurantes();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			daoRestaurantes.deleteRestaurante(restaurante);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que elimina el Zonas que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el Zonas que entra como parametro
	 * @param Zonas - Zonas a eliminar. Zonas != null
	 * @throws Exception - cualquier error que se genera actualizando los Zonas
	 */
	public void deleteCategoria(Categoria categoria) throws Exception {
		DAOCategorias daoCategorias = new DAOCategorias();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoCategorias.setConn(conn);
			daoCategorias.deleteCategoria(categoria);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCategorias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que elimina el Zonas que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el Zonas que entra como parametro
	 * @param Zonas - Zonas a eliminar. Zonas != null
	 * @throws Exception - cualquier error que se genera actualizando los Zonas
	 */
	public void deleteEspacio(Espacios espacio) throws Exception {
		DAOEspacios daoEspacio = new DAOEspacios();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoEspacio.setConn(conn);
			daoEspacio.deleteEspacio(espacio);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEspacio.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que elimina el Zonas que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el Zonas que entra como parametro
	 * @param Zonas - Zonas a eliminar. Zonas != null
	 * @throws Exception - cualquier error que se genera actualizando los Zonas
	 */
	public void deleteIngrediente(Ingrediente Ingrediente) throws Exception {
		DAOIngredientes daoIngredientes = new DAOIngredientes();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			daoIngredientes.deleteIngrediente(Ingrediente);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que elimina el Zonas que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el Zonas que entra como parametro
	 * @param Zonas - Zonas a eliminar. Zonas != null
	 * @throws Exception - cualquier error que se genera actualizando los Zonas
	 */
	//	public void deleteMenu(Menu Menu) throws Exception {
	//		DAOMenus daoMenus = new DAOMenus();
	//		try 
	//		{
	//			//////transaccion
	//			this.conn = darConexion();
	//			daoMenus.setConn(conn);
	//			daoMenus.deleteMenu(Menu);
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoMenus.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//	}

	/**
	 * Metodo que modela la transaccion que elimina el Zonas que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el Zonas que entra como parametro
	 * @param Zonas - Zonas a eliminar. Zonas != null
	 * @throws Exception - cualquier error que se genera actualizando los Zonas
	 */
	public void deleteProducto(Producto Producto) throws Exception {
		DAOProductos daoProductos = new DAOProductos();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			daoProductos.deleteProducto(Producto);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deletePedido(Pedido Pedido) throws Exception {
		DAOPedidos daoPedidos = new DAOPedidos();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			daoPedidos.deletePedido(Pedido);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que elimina el Zonas que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el Zonas que entra como parametro
	 * @param Zonas - Zonas a eliminar. Zonas != null
	 * @throws Exception - cualquier error que se genera actualizando los Zonas
	 */
	//	public void deleteProductoRestaurante(ProductoRestaurante ProductoRestaurante) throws Exception {
	//		DAOProductosRestaurantes daoProductosRestaurantes = new DAOProductosRestaurantes();
	//		try 
	//		{
	//			//////transaccion
	//			this.conn = darConexion();
	//			daoProductosRestaurantes.setConn(conn);
	//			daoProductosRestaurantes.deleteProducto(ProductoRestaurante);
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoProductosRestaurantes.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//	}

	public List<Usuario> darUsuarios() throws Exception {
		List<Usuario> Usuarios;
		DAOUsuario daoUsuarios = new DAOUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			Usuarios = daoUsuarios.darUsuarios();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Usuarios;
	}

	/**
	 * Metodo que modela la transaccion que busca el/los Usuarios en la base de datos con el nombre entra como parametro.
	 * @param name - Nombre del Usuario a buscar. name != null
	 * @return ListaUsuarios - objeto que modela  un arreglo de Usuarios. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Usuario> buscarUsuariosPorNombre(String name) throws Exception {
		List<Usuario> Usuarios;
		DAOUsuario daoUsuarios = new DAOUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			Usuarios = daoUsuarios.buscarUsuariosPorNombre(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Usuarios;
	}

	/**
	 * Metodo que modela la transaccion que busca el Usuario en la base de datos con el id que entra como parametro.
	 * @param name - Id del Usuario a buscar. name != null
	 * @return Usuario - Resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Usuario buscarUsuarioPorId(Integer id) throws Exception {
		Usuario Usuario;
		DAOUsuario daoUsuarios = new DAOUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			Usuario = daoUsuarios.buscarUsuarioPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Usuario;
	}

	public List<Usuario> buscarUsuariosPorRol(Integer rol) throws Exception {
		List<Usuario> Usuarios;
		DAOUsuario daoUsuarios = new DAOUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			Usuarios = daoUsuarios.buscarUsuariosPorRol(rol);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Usuarios;
	}
	/**
	 * Metodo que modela la transaccion que agrega un solo Usuario a la base de datos.
	 * <b> post: </b> se ha agregado el Usuario que entra como parametro
	 * @param Usuario - el Usuario a agregar. Usuario != null
	 * @throws Exception - cualquier error que se genere agregando el Usuario
	 */
	public void addUsuario(Usuario Usuario) throws Exception {
		DAOUsuario daoUsuarios = new DAOUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			daoUsuarios.addUsuario(Usuario);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	/**
	 * Metodo que modela la transaccion que actualiza el Usuario que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el Usuario que entra como parametro
	 * @param Usuario - Usuario a actualizar. Usuario != null
	 * @throws Exception - cualquier error que se genera actualizando los Usuarios
	 */
	public void updateUsuario(Usuario Usuario) throws Exception {
		DAOUsuario daoUsuarios = new DAOUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			daoUsuarios.updateUsuario(Usuario);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que elimina el Usuario que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el Usuario que entra como parametro
	 * @param Usuario - Usuario a eliminar. Usuario != null
	 * @throws Exception - cualquier error que se genera actualizando los Usuarios
	 */
	public void deleteUsuario(Usuario Usuario) throws Exception {
		DAOUsuario daoUsuarios = new DAOUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			daoUsuarios.deleteUsuario(Usuario);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public List<Redireccionar> darRedireccion() throws Exception {
		List<Redireccionar> registros;
		DAORedireccionarRegistro daoRegistro = new DAORedireccionarRegistro();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRegistro.setConn(conn);
			registros = daoRegistro.darRedireccion();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRegistro.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return registros;
	}

	public void addMenu(Menu Menu) throws Exception {
		DAOMenus daoMenu = new DAOMenus();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMenu.setConn(conn);
			daoMenu.addMenus(Menu);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenu.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public void deleteMenu(Menu Menu) throws Exception {
		DAOMenus daoMenus = new DAOMenus();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMenus.setConn(conn);
			daoMenus.deleteMenu(Menu);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	public Menu buscarMenuPorNombre(String name) throws Exception {
		Menu Menu;
		DAOMenus daoMenus = new DAOMenus();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMenus.setConn(conn);
			Menu = daoMenus.buscarMenuPorNombre(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Menu;
	}
	public List<Menu> darMenus() throws Exception {
		List<Menu> Menu;
		DAOMenus daoMenus = new DAOMenus();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMenus.setConn(conn);
			Menu = daoMenus.darMenus();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Menu;
	}

	/**
	 * Metodo que modela la transaccion que retorna todos los Zonas de la base de datos.
	 * @return ListaZonas - objeto que modela  un arreglo de Zonas. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Zonas> darZonas() throws Exception {
		List<Zonas> Zonas;
		DAOZonas daoZonas = new DAOZonas();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoZonas.setConn(conn);
			Zonas = daoZonas.darZonas();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Zonas;
	}

	/**
	 * Metodo que modela la transaccion que busca el Zonas en la base de datos con el id que entra como parametro.
	 * @param name - Id del Zonas a buscar. name != null
	 * @return Zonas - Resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Zonas buscarZonasPorId(Integer id) throws Exception {
		Zonas Zonas;
		DAOZonas daoZonas = new DAOZonas();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoZonas.setConn(conn);
			Zonas = daoZonas.buscarZonasPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Zonas;
	}

	/**
	 * Metodo que modela la transaccion que agrega un solo Zonas a la base de datos.
	 * <b> post: </b> se ha agregado el Zonas que entra como parametro
	 * @param Zonas - el Zonas a agregar. Zonas != null
	 * @throws Exception - cualquier error que se genere agregando el Zonas
	 */
	public void addZonas(Zonas Zonas) throws Exception {
		DAOZonas daoZonas = new DAOZonas();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoZonas.setConn(conn);
			daoZonas.addZonas(Zonas);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que actualiza el Zonas que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el Zonas que entra como parametro
	 * @param Zonas - Zonas a actualizar. Zonas != null
	 * @throws Exception - cualquier error que se genera actualizando los Zonas
	 */
	public void updateZonas(Zonas Zonas) throws Exception {
		DAOZonas daoZonas = new DAOZonas();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoZonas.setConn(conn);
			daoZonas.updateZonas(Zonas);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que elimina el Zonas que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el Zonas que entra como parametro
	 * @param Zonas - Zonas a eliminar. Zonas != null
	 * @throws Exception - cualquier error que se genera actualizando los Zonas
	 */
	public void deleteZonas(Zonas Zonas) throws Exception {
		DAOZonas daoZonas = new DAOZonas();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoZonas.setConn(conn);
			daoZonas.deleteZonas(Zonas);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que agrega un solo Zonas a la base de datos.
	 * <b> post: </b> se ha agregado el Zonas que entra como parametro
	 * @param Zonas - el Zonas a agregar. Zonas != null
	 * @throws Exception - cualquier error que se genere agregando el Zonas
	 */
	public void addProductoIngrediente(ProductoIngrediente productoIngrediente) throws Exception {
		DAOProductoIngrediente daoProductoIngrediente = new DAOProductoIngrediente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductoIngrediente.setConn(conn);
			daoProductoIngrediente.addProductoIngredientes(productoIngrediente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductoIngrediente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que agrega un solo Zonas a la base de datos.
	 * <b> post: </b> se ha agregado el Zonas que entra como parametro
	 * @param Zonas - el Zonas a agregar. Zonas != null
	 * @throws Exception - cualquier error que se genere agregando el Zonas
	 */
	public void addMenuProducto(MenuProducto MenuProducto) throws Exception {
		DAOMenuProducto daoMenuProducto = new DAOMenuProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMenuProducto.setConn(conn);
			daoMenuProducto.addMenuProducto(MenuProducto);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenuProducto.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	public void addPreferenciaCosto(VerificarPreferenciaCosto verif) throws Exception {
		DAOPreferenciasCosto daoPreferenciasCosto = new DAOPreferenciasCosto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPreferenciasCosto.setConn(conn);
			daoPreferenciasCosto.addPreferencia(verif);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferenciasCosto.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	public void addPreferenciaZona(VerificarPreferenciaZona verif) throws Exception {
		DAOPreferenciasZona daoPreferenciasZona = new DAOPreferenciasZona();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPreferenciasZona.setConn(conn);
			daoPreferenciasZona.addPreferencia(verif);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferenciasZona.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public Integer darPrecioProducto(String nombreProducto, String nombreRestaurante) throws Exception{
		DAOProductoRestaurante daoProductoRestaurante = new DAOProductoRestaurante();

		Integer precioVenta=0;
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductoRestaurante.setConn(conn);
			precioVenta = daoProductoRestaurante.darPrecio(nombreProducto, nombreRestaurante);

			conn.commit();
		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductoRestaurante.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return precioVenta;
	}

	public void addProductoVenta(String nombre) throws Exception{
		DAOProductoVenta daoProductoVenta = new DAOProductoVenta();

		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductoVenta.setConn(conn);
			daoProductoVenta.addProductoVenta(nombre);
			conn.commit();
		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductoVenta.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	public void asignarProductoRestaurante(ProductoRestaurante agrega) throws Exception{
		DAOProductoRestaurante daoProductoRestaurante = new DAOProductoRestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductoRestaurante.setConn(conn);
			daoProductoRestaurante.addProductoRestaurante(agrega);
			conn.commit();
		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductoRestaurante.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void registrarEquivalenciaProducto(String producto,String equivalente) throws Exception{
		DAORegistrarEqProducto daoRegistrarEqProducto = new DAORegistrarEqProducto();
		try
		{
			//////transaccion

			Integer cate1 = buscarProductoPorNombre(producto).getCategoria();
			Integer cate2 =buscarProductoPorNombre(equivalente).getCategoria();
			this.conn = darConexion();
			daoRegistrarEqProducto.setConn(conn);
			if(cate1 == cate2){

				daoRegistrarEqProducto.addEquivalencia(producto,equivalente);
				conn.commit();
			}else{
				throw new Exception("Las categorias deben ser las mismas");
			}
		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRegistrarEqProducto.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}
	public void registrarEquivalenciaIngrediente(String ingrediente,String equivalente, String restaurante) throws Exception{
		DAORegistrarEqIngrediente daoRegistrarEqIngrediente = new DAORegistrarEqIngrediente();
		try
		{
			//////transaccion
			this.conn = darConexion();
			daoRegistrarEqIngrediente.setConn(conn);
			daoRegistrarEqIngrediente.addEquivalencia(ingrediente,equivalente,restaurante);
			conn.commit();
		}
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRegistrarEqIngrediente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	public void surtirRestaurante(String restaurante)throws Exception {
		DAOProductoRestaurante daoProductoRestaurante = new DAOProductoRestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductoRestaurante.setConn(conn);
			daoProductoRestaurante.surtir(restaurante);
			conn.commit();
		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductoRestaurante.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que busca el Zonas en la base de datos con el id que entra como parametro.
	 * @param name - Id del Zonas a buscar. name != null
	 * @return Zonas - Resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<String> buscarEquivalenciasProducto(String nombreProducto,String nombreRestaurante) throws Exception {
		List<String> equivalencias;
		DAORegistrarEqProducto daoRegistrarEqProducto = new DAORegistrarEqProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRegistrarEqProducto.setConn(conn);
			equivalencias = daoRegistrarEqProducto.buscarEquivalenciasProducto(nombreProducto,nombreRestaurante);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRegistrarEqProducto.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return equivalencias;
	}
	
	public List<String> buscarEquivalenciasIngrediente(String nombreIngrediente,String nombreRestaurante) throws Exception {
		List<String> equivalencias;
		DAORegistrarEqIngrediente daoRegistrarEqIngrediente = new DAORegistrarEqIngrediente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRegistrarEqIngrediente.setConn(conn);
			equivalencias = daoRegistrarEqIngrediente.buscarEquivalenciasIngrediente(nombreIngrediente,nombreRestaurante);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRegistrarEqIngrediente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return equivalencias;
	}
	public void addPedidoMesa(Integer numeroMesa, Integer numeroPedido)throws Exception {
		DAOPedidoMesa daoPedidoMesa = new DAOPedidoMesa();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPedidoMesa.setConn(conn);
			daoPedidoMesa.addPedidoMesa(numeroMesa,numeroPedido);
			conn.commit();
		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidoMesa.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updatePedidoMesa(Integer numero_pedido_mesa, Boolean servido)throws Exception {
		DAOPedidoMesa daoPedidoMesa = new DAOPedidoMesa();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPedidoMesa.setConn(conn);
			ArrayList<Integer>pedidos =daoPedidoMesa.darPedidosMesa(numero_pedido_mesa);
			conn.commit();
			for(int x=0; x<pedidos.size();x++)
			{
				System.out.println(pedidos.get(x)+"");
				this.updatePedido(pedidos.get(x), servido);
			}
		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidoMesa.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	public List<String> buscarProductosMenu(String nombreMenu) throws SQLException{
		List<String> productos;
		DAOMenuProducto daoMenuProducto= new DAOMenuProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMenuProducto.setConn(conn);
			productos = daoMenuProducto.buscarProductosMenu(nombreMenu);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenuProducto.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return productos;
	}
	public List<String> buscarIngredientesProducto(String nombreProducto) throws SQLException{
		List<String> ingredientes;
		DAOProductoIngrediente daoProductoIngrediente= new DAOProductoIngrediente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductoIngrediente.setConn(conn);
			ingredientes = daoProductoIngrediente.buscarIngredientesProducto(nombreProducto);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductoIngrediente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ingredientes;
	}
	public void cancelarPedido(Integer pedido, Integer idCliente)throws Exception {
		DAOPedidos daoPedidos = new DAOPedidos();	
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			daoPedidos.cancelarPedido(pedido,idCliente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
	}
	public void cancelarPedidoMesa(Integer idPedido, Integer idCliente) throws Exception{
		DAOPedidoMesa daoPedidoMesa = new DAOPedidoMesa();	
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPedidoMesa.setConn(conn);
			ArrayList<Integer> lista = daoPedidoMesa.darPedidosMesa(idPedido);
			conn.commit();
			for(int x=0; x<lista.size();x++)
			{
				this.cancelarPedido(lista.get(x), idCliente);
			}

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidoMesa.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
}
