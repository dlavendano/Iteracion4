package rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.annotate.JsonProperty;

import tm.RotondAndesTM;
import vos.CancelarPedido;
import vos.Pedido;
import vos.Producto;
import vos.ProductoIngrediente;
import vos.ProductoRestaurante;
import vos.RegistrarEqIngrediente;
import vos.RegistrarEqProducto;
import vos.ServirMesa;
import vos.ServirProducto;
import vos.SurtirRestaurante;
import vos.Usuario;
import vos.ValidarMenuProducto;
import vos.ValidarProductoIngrediente;
import vos.VerificacionProducto;
import vos.VerificacionRestaurante;
import vos.VerificacionUsuario;
import vos.VerificarIngrediente;
import vos.VerificarMenu;
import vos.VerificarMesa;
import vos.VerificarPedido;
import vos.VerificarPreferenciaCosto;
import vos.VerificarPreferenciaZona;
import vos.VerificarProductoRestaurante;
import vos.VerificarZonas;
@Path("usuarios")
public class UsuarioServices {
	/**
	 * Atributo que usa la anotacion @Context para tener el ServletContext de la conexion actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * Metodo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}


	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}

	Date date = new Date();
	DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
	//Date a =hourdateFormat.format(date);
	/**
	 * Metodo que expone servicio REST usando GET que da todos los Usuarios de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/UsuarioAndes/rest/Usuarios
	 * @return Json con todos los Usuarios de la base de datos o json con 
	 * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUsuarios() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Usuario> Usuarios;
		try {
			Usuarios = tm.darUsuarios();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Usuarios).build();
	}

	/**
	 * Metodo que expone servicio REST usando GET que busca el Usuario con el id que entra como parametro
	 * <b>URL: </b> http://"ip o nombre de host":8080/UsuarioAndes/rest/Usuarios/<<id>>" para la busqueda"
	 * @param name - Nombre del Usuario a buscar que entra en la URL como parametro 
	 * @return Json con el/los Usuarios encontrados con el nombre que entra como parametro o json con 
	 * el error que se produjo
	 */
	@GET
	@Path( "{rol: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getUsuario( @PathParam( "rol" ) Integer rol )
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		List<Usuario> Usuarios;
		try
		{
			Usuarios = tm.buscarUsuariosPorRol( rol );
			return Response.status( 200 ).entity( Usuarios ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

	/**
	 * Metodo que expone servicio REST usando GET que busca el Usuario con el nombre que entra como parametro
	 * <b>URL: </b> http://"ip o nombre de host":8080/UsuarioAndes/rest/Usuarios/nombre/nombre?nombre=<<nombre>>" para la busqueda"
	 * @param name - Nombre del Usuario a buscar que entra en la URL como parametro 
	 * @return Json con el/los Usuarios encontrados con el nombre que entra como parametro o json con 
	 * el error que se produjo
	 */
	@GET
	@Path( "{nombre}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getUsuarioName( @QueryParam("nombre") String name) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Usuario> Usuarios;
		try {
			if (name == null || name.length() == 0)
				throw new Exception("Nombre del Usuario no valido");
			Usuarios = tm.buscarUsuariosPorNombre(name);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Usuarios).build();
	}


	/**
	 * Metodo que expone servicio REST usando POST que registra el Cliente o el Usuario Restaurante validando que sea un Usuario Administrador el que lo agregeque. Recibe un Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/UsuarioAndes/rest/Usuarios/Usuario
	 * @param Usuario - Usuario a agregar
	 * @return Json con el Usuario que agrego o Json con el error que se produjo
	 * @throws Exception 
	 */
	@POST
	@Path("{rol: \\d+}/registrarUsuario")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(@PathParam("rol") Integer rol,VerificacionUsuario verif) throws Exception {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if(rol ==3){
				Usuario admon = tm.buscarUsuarioPorId(verif.getAdministrador().getId());
				if(admon != null && admon.getContraseña().equals(verif.getAdministrador().getContraseña()) && admon.getRol() == 3)
					tm.addUsuario(verif.getUser());
				else{
					throw new Exception("Id o contraseña invalido");
				}
			}else{
				throw new Exception("No tiene los permisos necesarios");
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(verif.getUser()).build();
	}

	/**
	 * Metodo que expone servicio REST usando POST que registra el Cliente o el Usuario Restaurante validando que sea un Usuario Administrador el que lo agregeque. Recibe un Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/UsuarioAndes/rest/Usuarios/Usuario
	 * @param Usuario - Usuario a agregar
	 * @return Json con el Usuario que agrego o Json con el error que se produjo
	 * @throws Exception 
	 */
	@POST
	@Path("{rol: \\d+}/asignarProducto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMenuProducto(@PathParam("rol") Integer rol,ValidarMenuProducto verif) throws Exception {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if(rol ==2){
				Usuario admon = tm.buscarUsuarioPorId(verif.getUsuarioRestaurante().getId());
				if(admon != null && admon.getContraseña().equals(verif.getUsuarioRestaurante().getContraseña()) && admon.getRol() == 2)
					tm.addMenuProducto(verif.getMenuProducto());
				else{
					throw new Exception("Id o contraseña invalido");
				}
			}else{
				throw new Exception("No tiene los permisos necesarios");
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(verif.getMenuProducto()).build();
	}
	/**
	 * Metodo que expone servicio REST usando POST que registra el Cliente o el Usuario Restaurante validando que sea un Usuario Administrador el que lo agregeque. Recibe un Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/UsuarioAndes/rest/Usuarios/Usuario
	 * @param Usuario - Usuario a agregar
	 * @return Json con el Usuario que agrego o Json con el error que se produjo
	 * @throws Exception 
	 */
	@POST
	@Path("{rol: \\d+}/asignarIngrediente")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProductoIngrediente(@PathParam("rol") Integer rol,ValidarProductoIngrediente prod) throws Exception {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if(rol ==2){
				Usuario admon = tm.buscarUsuarioPorId(prod.getUsuarioRestaurante().getId());
				if(admon != null && admon.getContraseña().equals(prod.getUsuarioRestaurante().getContraseña()) && admon.getRol() == 2)
					tm.addProductoIngrediente(prod.getProductoIngrediente());
				else{
					throw new Exception("Id o contraseña invalido");
				}
			}else{
				throw new Exception("No tiene los permisos necesarios");
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(prod.getProductoIngrediente()).build();
	}
	/**
	 * Metodo que expone servicio REST usando POST que registra un restaurante validando que un Usuario Administrador sea el que agrege dicho restaurante.Recibe un Json
	 * @param rol
	 * @param verif
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("{rol: \\d+}/registrarRestaurante")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRestaurante(@PathParam("rol") Integer rol,VerificacionRestaurante verif) throws Exception {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if(rol ==3){
				Usuario admon = tm.buscarUsuarioPorId(verif.getAdministrador().getId());
				if(admon != null && admon.getContraseña().equals(verif.getAdministrador().getContraseña()) && admon.getRol() == 3)
					tm.addRestaurante(verif.getRestaurante());
				else{
					throw new Exception("Id o contraseña invalido");
				}
			}else{
				throw new Exception("No tiene los permisos necesarios");
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(verif.getRestaurante()).build();
	}

	/**
	 * Metodo que expone servicio REST usando POST que registra un restaurante validando que un Usuario Restaurante sea el que agrege dicho restaurante.Recibe un Json
	 * @param rol
	 * @param verif
	 * @return
	 * @throws Exception
	 */

	//	///{
	//	int x=0;
	//	while(x<verif.getIngredientes().size())
	//	{
	//		int y=0;
	//		boolean bandera=false;
	//		while(y<tm.darIngredientes().size())
	//		{
	//			
	//			if(verif.getIngredientes().get(x).getNombre().equals(tm.darIngredientes().get(y).getNombre()))
	//				{
	//				tm.addIngredienteProducto(verif);
	//				bandera=true;
	//				}
	//			y++;
	//		}
	//		if(bandera==false)
	//			throw new Exception("Ingrediente no registrado");
	//		else
	//	X++;
	@POST
	@Path("{rol: \\d+}/registrarProducto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProducto(@PathParam("rol") Integer rol,VerificacionProducto verif) throws Exception {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if(rol ==2){
				Usuario admon = tm.buscarUsuarioPorId(verif.getAdministrador().getId());
				if(admon != null && admon.getContraseña().equals(verif.getAdministrador().getContraseña()) && admon.getRol() == 2){
					tm.addProductoVenta(verif.getProducto().getNombre());
					tm.addProducto(verif.getProducto());
				}
				else{
					throw new Exception("Id o contraseña invalido");
				}
			}else{
				throw new Exception("No tiene los permisos necesarios");
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(verif.getProducto()).build();
	}

	/**
	 * Metodo que expone servicio REST usando POST que registra un restaurante validando que un Usuario Restaurante sea el que agrege dicho restaurante.Recibe un Json
	 * @param rol
	 * @param verif
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("{rol: \\d+}/registrarIngrediente")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addIngrediente (@PathParam("rol") Integer rol,VerificarIngrediente verif) throws Exception {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if(rol ==2){
				Usuario admon = tm.buscarUsuarioPorId(verif.getUsuarioRestaurante().getId());
				if(admon != null && admon.getContraseña().equals(verif.getUsuarioRestaurante().getContraseña()) && admon.getRol() == 2)
					tm.addIngrediente(verif.getIngrediente());
				else{
					throw new Exception("Id o contraseña invalido");
				}
			}else{
				throw new Exception("No tiene los permisos necesarios");
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(verif.getIngrediente()).build();
	}


	/**
	 * Metodo que expone servicio REST usando POST que registra un restaurante validando que un Usuario Restaurante sea el que agrege dicho restaurante.Recibe un Json
	 * @param rol
	 * @param verif
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("{rol: \\d+}/registrarMenu")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMenu (@PathParam("rol") Integer rol,VerificarMenu verif) throws Exception {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if(rol ==2){
				Usuario admon = tm.buscarUsuarioPorId(verif.getUsuarioRestaurante().getId());
				if(admon != null && admon.getContraseña().equals(verif.getUsuarioRestaurante().getContraseña()) && admon.getRol() == 2)
				{
					tm.addProductoVenta(verif.getMenu().getNombre());
					tm.addMenu(verif.getMenu());
				}
				else{
					throw new Exception("Id o contraseña invalido");
				}
			}else{
				throw new Exception("No tiene los permisos necesarios");
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(verif.getMenu()).build();
	}


	/**
	 * Metodo que expone servicio REST usando POST que registra un restaurante validando que un Usuario Restaurante sea el que agrege dicho restaurante.Recibe un Json
	 * @param rol
	 * @param verif
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("{rol: \\d+}/registrarZona")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addZonas(@PathParam("rol") Integer rol,VerificarZonas verif) throws Exception {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if(rol ==2){
				Usuario admon = tm.buscarUsuarioPorId(verif.getAdministrador().getId());
				if(admon != null && admon.getContraseña().equals(verif.getAdministrador().getContraseña()) && admon.getRol() == 2)
					tm.addZonas(verif.getZonas());
				else{
					throw new Exception("Id o contraseña invalido");
				}
			}else{
				throw new Exception("No tiene los permisos necesarios");
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(verif.getZonas()).build();
	}
	/**
	 * Metodo que expone servicio REST usando PUT que actualiza el Usuario que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/UsuarioAndes/rest/Usuarios
	 * @param Usuario - Usuario a actualizar. 
	 * @return Json con el Usuario que actualizo o Json con el error que se produjo
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUsuario(Usuario Usuario) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.updateUsuario(Usuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Usuario).build();
	}

	/**
	 * Metodo que expone servicio REST usando DELETE que elimina el Usuario que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/UsuarioAndes/rest/Usuarios
	 * @param Usuario - Usuario a aliminar. 
	 * @return Json con el Usuario que elimino o Json con el error que se produjo
	 */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUsuario(Usuario Usuario) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteUsuario(Usuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Usuario).build();
	}


	@POST
	@Path("{rol: \\d+}/agregarPreferencia")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPreferenciaCosto(@PathParam("rol") Integer rol,VerificarPreferenciaCosto verif) throws Exception {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if(rol ==1){
				Usuario cliente = tm.buscarUsuarioPorId(verif.getUsuario().getId());
				if(cliente != null && cliente.getContraseña().equals(verif.getUsuario().getContraseña()) && cliente.getRol() == 1)
					tm.addPreferenciaCosto(verif);
				else{
					throw new Exception("Id o contraseña invalido");
				}
			}else{
				throw new Exception("No tiene los permisos necesarios");
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(verif).build();
	}
	@POST
	@Path("{rol: \\d+}/agregarPreferenciaZona")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPreferenciaZona(@PathParam("rol") Integer rol,VerificarPreferenciaZona verif) throws Exception {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if(rol ==1){
				Usuario cliente = tm.buscarUsuarioPorId(verif.getUsuario().getId());
				if(cliente != null && cliente.getContraseña().equals(verif.getUsuario().getContraseña()) && cliente.getRol() == 1)
					tm.addPreferenciaZona(verif);
				else{
					throw new Exception("Id o contraseña invalido");
				}
			}else{
				throw new Exception("No tiene los permisos necesarios");
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(verif).build();
	}

	/**
	 * Metodo que expone servicio REST usando PUT que actualiza el Usuario que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/UsuarioAndes/rest/Usuarios
	 * @param Usuario - Usuario a actualizar. 
	 * @return Json con el Usuario que actualizo o Json con el error que se produjo
	 * @throws Exception 
	 */
	@PUT
	@Path("{rol: \\d+}/servirPedido")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePedido(@PathParam("rol") Integer rol,@JsonProperty(value = "servir")ServirProducto servir) throws Exception {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if(rol ==2){
				Usuario rest = tm.buscarUsuarioPorId(servir.getUsuarioRestaurante().getId());

				if(rest != null && rest.getContraseña().equals(servir.getUsuarioRestaurante().getContraseña()) && rest.getRol() == 2)
				{
					tm.updatePedido(servir.getNumero_pedido(),servir.getServido());
				}
				else{
					throw new Exception("Id o contraseña invalido");
				}
			}else{
				throw new Exception("No tiene los permisos necesarios");
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(servir).build();
	}


	@POST
	@Path("{rol: \\d+}/registrarPedido")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPedido(@PathParam("rol") Integer rol,
			@JsonProperty(value = "pedido")VerificarPedido verif ) throws Exception {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if(rol ==1){
				Usuario cliente1 = tm.buscarUsuarioPorId(verif.getCliente().getId());

				if(cliente1 != null && cliente1.getContraseña().equals(verif.getCliente().getContraseña()) && cliente1.getRol() == 1)
				{
					tm.addPedidoEquivalencias(verif.getPedido(),verif.getCliente().getId());
				}
				else{
					throw new Exception("Id o contraseña invalido");
				}
			}else{
				throw new Exception("No tiene los permisos necesarios");
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(verif).build();
	}
	@POST
	@Path("{rol: \\d+}/asignarProductoRestaurante")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response asignarProducto(@PathParam("rol") Integer rol,
			VerificarProductoRestaurante agrega) throws Exception {

		/*	Date ahora = new Date();
	    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
	   formateador.format(ahora);*/

		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if(rol ==2){
				Usuario cliente1 = tm.buscarUsuarioPorId(agrega.getUsuarioRestaurante().getId());

				if(cliente1 != null && cliente1.getContraseña().equals(agrega.getUsuarioRestaurante().getContraseña()) && cliente1.getRol() == 2)
				{
					tm.asignarProductoRestaurante( agrega.getProductoRestaurante());
				}
				else{
					throw new Exception("Id o contraseña invalido");
				}
			}else{
				throw new Exception("No tiene los permisos necesarios");
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(agrega).build();
	}
	@POST
	@Path("{rol: \\d+}/registrarEquivalenciaProducto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarEquivalenciaProducto(@PathParam("rol") Integer rol,
			RegistrarEqProducto reg) throws Exception {

		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if(rol ==2){
				Usuario rest = tm.buscarUsuarioPorId(reg.getUsuarioRestaurante().getId());

				if(rest != null && rest.getContraseña().equals(reg.getUsuarioRestaurante().getContraseña()) && rest.getRol() == 2)
				{
					tm.registrarEquivalenciaProducto(reg.getProducto(),reg.getEquivalente());
				}
				else{
					throw new Exception("Id o contraseña invalido");
				}
			}else{
				throw new Exception("No tiene los permisos necesarios");
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reg).build();
	}
	@POST
	@Path("{rol: \\d+}/registrarEquivalenciaIngrediente")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarEquivalenciaIngrediente(@PathParam("rol") Integer rol,
			RegistrarEqIngrediente reg) throws Exception {

		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if(rol ==2){
				Usuario rest = tm.buscarUsuarioPorId(reg.getUsuarioRestaurante().getId());

				if(rest != null && rest.getContraseña().equals(reg.getUsuarioRestaurante().getContraseña()) && rest.getRol() == 2)
				{
					tm.registrarEquivalenciaIngrediente(reg.getIngrediente(),reg.getEquivalente(),reg.getRestaurante());
				}
				else{
					throw new Exception("Id o contraseña invalido");
				}
			}else{
				throw new Exception("No tiene los permisos necesarios");
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reg).build();
	}
	@POST
	@Path("{rol: \\d+}/surtirRestaurante")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response surtirRestaurante(@PathParam("rol") Integer rol,
			SurtirRestaurante modifica ) throws Exception {


		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if(rol ==2){
				Usuario cliente1 = tm.buscarUsuarioPorId(modifica.getUsuarioRestaurante().getId());

				if(cliente1 != null && cliente1.getContraseña().equals(modifica.getUsuarioRestaurante().getContraseña()) && cliente1.getRol() == 2)
				{
					tm.surtirRestaurante(modifica.getRestaurante());
				}
				else{
					throw new Exception("Id o contraseña invalido");
				}
			}else{
				throw new Exception("No tiene los permisos necesarios");
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(modifica).build();
	}
	@SuppressWarnings("unused")
	@POST
	@Path("{rol: \\d+}/registrarPedidoMesa")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPedidoMesa(@PathParam("rol") Integer rol,
			VerificarMesa verif ) throws Exception {


		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if(rol ==1){
				Usuario cliente1 = tm.buscarUsuarioPorId(verif.getCliente().getId());

				if(cliente1 != null && cliente1.getContraseña().equals(verif.getCliente().getContraseña()) && cliente1.getRol() == 1)
				{
					Integer precioCompleto = 0;
					Integer numeroPedido= null;
					Integer idCliente = verif.getCliente().getId();
					String nombreRestaurante=null;
					String nombreProducto=null;
					for(int x=0;x<verif.getPedido().size();x++)
					{
						String string = verif.getPedido().get(x);
						String[] parte = string.split(",");
						numeroPedido= Integer.parseInt(parte[0]);
						nombreRestaurante = parte[1];
						nombreProducto = parte[2];
						Pedido pedido= new Pedido(numeroPedido,nombreProducto,nombreRestaurante);
						tm.addPedidoEquivalencias(pedido, idCliente);
						precioCompleto += tm.darPrecioProducto(nombreProducto, nombreRestaurante);
						tm.addPedidoMesa(verif.getNumeroMesa(),numeroPedido);
					}
				}
				else{
					throw new Exception("Id o contraseña invalido");
				}
			}else{
				throw new Exception("No tiene los permisos necesarios");
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(verif).build();
	}
	@PUT
	@Path("{rol: \\d+}/servirPedidoMesa")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePedidoMesa(@PathParam("rol")Integer rol,
			ServirMesa verif ) throws Exception {


		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if(rol ==2){
				Usuario cliente1 = tm.buscarUsuarioPorId(verif.getUsuarioRestaurante().getId());

				if(cliente1 != null && cliente1.getContraseña().equals(verif.getUsuarioRestaurante().getContraseña()) && cliente1.getRol() == 2)
				{
					tm.updatePedidoMesa(verif.getNumero_pedido_mesa(),verif.getServido());
				}
				else{
					throw new Exception("Id o contraseña invalido");
				}
			}else{
				throw new Exception("No tiene los permisos necesarios");
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(verif).build();
	}
	
	@PUT
	@Path("{rol: \\d+}/cancelarPedido")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancelarPedido(@PathParam("rol")Integer rol,
			CancelarPedido verif ) throws Exception {


		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if(rol ==1){
				Usuario cliente1 = tm.buscarUsuarioPorId(verif.getCliente().getId());

				if(cliente1 != null && cliente1.getContraseña().equals(verif.getCliente().getContraseña()) && cliente1.getRol() == 1)
				{
					tm.cancelarPedido(verif.getPedido(),verif.getCliente().getId());
				}
				else{
					throw new Exception("Id o contraseña invalido");
				}
			}else{
				throw new Exception("No tiene los permisos necesarios");
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(verif).build();
	}
	
	@PUT
	@Path("{rol: \\d+}/cancelarPedidoMesa")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancelarPedidoMesa(@PathParam("rol")Integer rol,
			CancelarPedido verif ) throws Exception {


		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if(rol ==1){
				Usuario cliente1 = tm.buscarUsuarioPorId(verif.getCliente().getId());

				if(cliente1 != null && cliente1.getContraseña().equals(verif.getCliente().getContraseña()) && cliente1.getRol() == 1)
				{
					tm.cancelarPedidoMesa(verif.getPedido(),verif.getCliente().getId());
				}
				else{
					throw new Exception("Id o contraseña invalido");
				}
			}else{
				throw new Exception("No tiene los permisos necesarios");
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(verif).build();
	}
}

