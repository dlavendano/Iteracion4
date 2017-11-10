package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class MenuProducto {

	@JsonProperty(value = "nombreMenu")
 private  String nombreMenu;
	@JsonProperty(value = "nombreProducto")
 private String nombreProducto;
	public String getNombreMenu() {
		return nombreMenu;
	}
	public void setNombreMenu(String nombreMenu) {
		this.nombreMenu = nombreMenu;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public MenuProducto(@JsonProperty(value = "nombreMenu")String nombreMenu, @JsonProperty(value = "nombreProducto")String nombreProducto) {
		super();
		this.nombreMenu = nombreMenu;
		this.nombreProducto = nombreProducto;
	}
	
}
