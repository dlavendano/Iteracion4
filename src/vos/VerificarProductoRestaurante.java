package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VerificarProductoRestaurante {
	@JsonProperty(value= "productoRestaurante")
	private ProductoRestaurante productoRestaurante;
	@JsonProperty(value ="usuarioRestaurante")
	private Usuario usuarioRestaurante;

	public VerificarProductoRestaurante(	@JsonProperty(value= "productoRestaurante")ProductoRestaurante productoRestaurante,@JsonProperty(value ="usuarioRestaurante") Usuario usuarioRestaurante) {
		super();
		this.productoRestaurante = productoRestaurante;
		this.usuarioRestaurante = usuarioRestaurante;
	}

	public ProductoRestaurante getProductoRestaurante() {
		return productoRestaurante;
	}

	public void setProductoRestaurante(ProductoRestaurante productoRestaurante) {
		this.productoRestaurante = productoRestaurante;
	}

	public Usuario getUsuarioRestaurante() {
		return usuarioRestaurante;
	}

	public void setUsuarioRestaurante(Usuario usuarioRestaurante) {
		this.usuarioRestaurante = usuarioRestaurante;
	}
	
}
