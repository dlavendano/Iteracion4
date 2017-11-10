package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VerificacionProducto {
	
	@JsonProperty(value= "producto")
	private Producto producto;
	@JsonProperty(value ="usuarioRestaurante")
	private Usuario usuarioRestaurante;

	

	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Usuario getAdministrador() {
		return usuarioRestaurante;
	}
	public void setAdministrador(Usuario usuarioRestaurante) {
		this.usuarioRestaurante = usuarioRestaurante;
	}
	public VerificacionProducto(@JsonProperty(value= "producto")Producto producto,	@JsonProperty(value ="usuarioRestaurante") Usuario usuarioRestaurante) {
		super();
		this.producto = producto;
		this.usuarioRestaurante = usuarioRestaurante;

	}
	
}
