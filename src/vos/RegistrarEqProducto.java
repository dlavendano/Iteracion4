package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class RegistrarEqProducto {
	@JsonProperty(value= "producto")
	private String producto;
	@JsonProperty(value= "equivalente")
	private String equivalente;
	@JsonProperty(value ="usuarioRestaurante")
	private Usuario usuarioRestaurante;
	
	public RegistrarEqProducto(	@JsonProperty(value= "producto")String producto, @JsonProperty(value= "equivalente")String equivalente,@JsonProperty(value ="usuarioRestaurante") Usuario usuarioRestaurante) {
		super();
		this.producto = producto;
		this.equivalente = equivalente;
		this.usuarioRestaurante = usuarioRestaurante;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public String getEquivalente() {
		return equivalente;
	}
	public void setEquivalente(String equivalente) {
		this.equivalente = equivalente;
	}
	public Usuario getUsuarioRestaurante() {
		return usuarioRestaurante;
	}
	public void setUsuarioRestaurante(Usuario usuarioRestaurante) {
		this.usuarioRestaurante = usuarioRestaurante;
	}

	
}
