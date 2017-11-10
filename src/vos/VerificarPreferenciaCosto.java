package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VerificarPreferenciaCosto {
	@JsonProperty(value= "usuario")
	private Usuario usuario;
	@JsonProperty(value ="costo")
	private Integer costo;
	public VerificarPreferenciaCosto(@JsonProperty(value= "usuario")Usuario usuario, @JsonProperty(value ="costo")Integer costo) {
		super();
		this.usuario = usuario;
		this.costo = costo;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Integer getCosto() {
		return costo;
	}
	public void setCosto(Integer costo) {
		this.costo = costo;
	}
	
}
