package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ProductoIngrediente {
	
	@JsonProperty(value = "nombre_producto")
 private  String nombreProducto;
	@JsonProperty(value = "nombre_ingrediente")
 private String nombreIngrediente;
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public String getNombreIngrediente() {
		return nombreIngrediente;
	}
	public void setNombreIngrediente(String nombreIngrediente) {
		this.nombreIngrediente = nombreIngrediente;
	}
	public ProductoIngrediente(	@JsonProperty(value = "nombre_producto")String nombreProducto,	@JsonProperty(value = "nombre_ingrediente") String nombreIngrediente) {
		super();
		this.nombreProducto = nombreProducto;
		this.nombreIngrediente = nombreIngrediente;
	}
 
}
