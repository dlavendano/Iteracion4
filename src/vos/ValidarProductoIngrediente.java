package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ValidarProductoIngrediente {

	@JsonProperty(value= "productoIngrediente")
	private ProductoIngrediente productoIngrediente;
	@JsonProperty(value ="usuarioRestaurante")
	private Usuario usuarioRestaurante;
	public ProductoIngrediente getProductoIngrediente() {
		return productoIngrediente;
	}
	public void setProductoIngrediente(ProductoIngrediente productoIngrediente) {
		this.productoIngrediente = productoIngrediente;
	}
	public Usuario getUsuarioRestaurante() {
		return usuarioRestaurante;
	}
	public void setUsuarioRestaurante(Usuario usuarioRestaurante) {
		this.usuarioRestaurante = usuarioRestaurante;
	}
	public ValidarProductoIngrediente(	@JsonProperty(value= "productoIngrediente")ProductoIngrediente productoIngrediente,@JsonProperty(value ="usuarioRestaurante") Usuario usuarioRestaurante) {
		super();
		this.productoIngrediente = productoIngrediente;
		this.usuarioRestaurante = usuarioRestaurante;
	}
	
	
}
