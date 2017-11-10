package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ValidarMenuProducto {
	
	@JsonProperty(value= "menuProducto")
	private MenuProducto menuProducto;
	@JsonProperty(value ="usuarioRestaurante")
	private Usuario usuarioRestaurante;
	
	
	public MenuProducto getMenuProducto() {
		return menuProducto;
	}
	public void setMenuProducto(MenuProducto menuProducto) {
		this.menuProducto = menuProducto;
	}
	public Usuario getUsuarioRestaurante() {
		return usuarioRestaurante;
	}
	public void setUsuarioRestaurante(Usuario usuarioRestaurante) {
		this.usuarioRestaurante = usuarioRestaurante;
	}
	public ValidarMenuProducto(@JsonProperty(value= "menuProducto")MenuProducto menuProducto, @JsonProperty(value ="usuarioRestaurante") Usuario usuarioRestaurante) {
		super();
		this.menuProducto = menuProducto;
		this.usuarioRestaurante = usuarioRestaurante;
	}
	
}
