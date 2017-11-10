package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class RegistrarEqIngrediente {
	@JsonProperty(value= "ingrediente")
	private String ingrediente;
	@JsonProperty(value= "equivalente")
	private String equivalente;
	@JsonProperty(value= "restaurante")
	private String restaurante;
	@JsonProperty(value ="usuarioRestaurante")
	private Usuario usuarioRestaurante;
	
	public RegistrarEqIngrediente(	@JsonProperty(value= "ingrediente")String ingrediente, @JsonProperty(value= "equivalente")String equivalente,@JsonProperty(value= "restaurante")String restaurante,@JsonProperty(value ="usuarioRestaurante") Usuario usuarioRestaurante) {
		super();
		this.ingrediente = ingrediente;
		this.equivalente = equivalente;
		this.usuarioRestaurante = usuarioRestaurante;
		this.restaurante = restaurante;
	}

	
	public String getRestaurante() {
		return restaurante;
	}


	public void setRestaurante(String restaurante) {
		this.restaurante = restaurante;
	}


	public String getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(String ingrediente) {
		this.ingrediente = ingrediente;
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
