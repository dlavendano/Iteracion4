package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VerificarIngrediente {
	
	@JsonProperty(value= "ingrediente")
	private Ingrediente ingrediente;
	@JsonProperty(value ="usuarioRestaurante")
	private Usuario usuarioRestaurante;
	public Ingrediente getIngrediente() {
		return ingrediente;
	}
	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}
	public Usuario getUsuarioRestaurante() {
		return usuarioRestaurante;
	}
	public void setUsuarioRestaurante(Usuario usuarioRestaurante) {
		this.usuarioRestaurante = usuarioRestaurante;
	}
	public VerificarIngrediente(@JsonProperty(value= "ingrediente")Ingrediente ingrediente,@JsonProperty(value ="usuarioRestaurante") Usuario usuarioRestaurante) {
		super();
		this.ingrediente = ingrediente;
		this.usuarioRestaurante = usuarioRestaurante;
	}
	
	
}
