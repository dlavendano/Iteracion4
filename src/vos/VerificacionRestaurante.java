package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VerificacionRestaurante {
	
	@JsonProperty(value= "restaurante")
	private Restaurante restaurante;
	@JsonProperty(value ="administrador")
	private Usuario admon;
	public Restaurante getRestaurante() {
		return restaurante;
	}
	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}
	public Usuario getAdministrador() {
		return admon;
	}
	public void setAdministrador(Usuario usuario) {
		this.admon = usuario;
	}
	public VerificacionRestaurante(@JsonProperty(value= "restaurante") Restaurante restaurante, @JsonProperty(value ="administrador") Usuario admon) {
		super();
		this.restaurante = restaurante;
		this.admon = admon;
	}
}
