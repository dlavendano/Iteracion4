package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VerificacionUsuario {

	@JsonProperty(value = "administrador")
	private Usuario administrador;
	@JsonProperty(value = "usuario")
	private Usuario user;
	public Usuario getAdministrador() {
		return administrador;
	}
	public void setAdministrador(Usuario administrador) {
		this.administrador = administrador;
	}
	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}
	
	public VerificacionUsuario(@JsonProperty(value = "administrador") Usuario administrador, @JsonProperty(value = "usuario")Usuario user) {
		super();
		this.administrador = administrador;
		this.user = user;
	}
	
}
