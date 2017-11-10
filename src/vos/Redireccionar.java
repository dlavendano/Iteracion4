package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Redireccionar {

	public static final String ClienteURL = "http://localhost:8080/RotondAndes/rest/usuarios/1";
	public static final String UsuarioRestauranteURL = "http://localhost:8080/RotondAndes/rest/usuarios/2";
	public static final String AdministradorURL = "http://localhost:8080/RotondAndes/rest/usuarios/3";
	/**
	 * URL del registro
	 */
	@JsonProperty(value = "urls")
	private List<String> urls;
	@JsonProperty(value = "id_rol")
	private Integer id_rol;
	
	public Redireccionar(@JsonProperty(value = "urls") List<String> urls,@JsonProperty(value = "id_rol")Integer id_rol){
		super();
		this.urls = urls;
		this.id_rol = id_rol;
	}
	public List<String> getUrls() {
		return urls;
	}
	public void setUrls(List<String> urls) {
		this.urls = urls;
	}
	public Integer getId_rol() {
		return id_rol;
	}
	public void setId_rol(Integer id_rol) {
		this.id_rol = id_rol;
	}
	

	
}
