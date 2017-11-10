package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class SurtirRestaurante {
	@JsonProperty(value = "usuarioRestaurante")
	private Usuario usuarioRestaurante;
	@JsonProperty(value = "restaurante")
	private String restaurante;

	public SurtirRestaurante(@JsonProperty(value = "usuarioRestaurante") Usuario usuarioRestaurante,
	@JsonProperty(value = "restaurante")String restaurante) {
	super();
	this.usuarioRestaurante = usuarioRestaurante;
	this.restaurante = restaurante;
	}


	public Usuario getUsuarioRestaurante() {
	return usuarioRestaurante;
	}


	public void setUsuarioRestaurante(Usuario usuarioRestaurante) {
	this.usuarioRestaurante = usuarioRestaurante;
	}


	public String getRestaurante() {
	return restaurante;
	}


	public void setRestaurante(String restaurante) {
	this.restaurante = restaurante;
	}

}
