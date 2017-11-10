package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VerificarMenu {
	@JsonProperty(value= "menu")
	private Menu menu;
	@JsonProperty(value ="usuarioRestaurante")
	private Usuario usuarioRestaurante;
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public Usuario getUsuarioRestaurante() {
		return usuarioRestaurante;
	}
	public void setUsuarioRestaurante(Usuario usuarioRestaurante) {
		this.usuarioRestaurante = usuarioRestaurante;
	}
	public VerificarMenu(@JsonProperty(value= "menu")Menu menu,@JsonProperty(value ="usuarioRestaurante") Usuario usuarioRestaurante) {
		super();
		this.menu = menu;
		this.usuarioRestaurante = usuarioRestaurante;
	}
	
}
