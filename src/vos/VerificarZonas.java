package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VerificarZonas {
	
	@JsonProperty(value= "zonas")
	private Zonas zonas;
	@JsonProperty(value ="administrador")
	private Usuario administrador;
	public Zonas getZonas() {
		return zonas;
	}
	public void setZonas(Zonas zonas) {
		this.zonas = zonas;
	}
	public Usuario getAdministrador() {
		return administrador;
	}
	public void setAdministrador(Usuario administrador) {
		this.administrador = administrador;
	}
	
	public VerificarZonas(@JsonProperty(value= "zonas")Zonas zonas,@JsonProperty(value ="administrador") Usuario administrador) {
		super();
		this.zonas = zonas;
		this.administrador = administrador;
	}

}
