package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VerificarPreferenciaZona {
	@JsonProperty(value= "usuario")
	private Usuario usuario;
	@JsonProperty(value ="zona")
	private Zonas zona;
	public VerificarPreferenciaZona(@JsonProperty(value= "usuario")Usuario usuario, @JsonProperty(value ="zona")Zonas zona) {
		super();
		this.usuario = usuario;
		this.zona = zona;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Zonas getZona() {
		return zona;
	}
	public void setZona(Zonas zona) {
		this.zona = zona;
	}

}
