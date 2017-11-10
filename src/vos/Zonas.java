package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Zonas {

	@JsonProperty(value= "id")	
	private Integer id;
	@JsonProperty(value = "nombre")
	private String nombre;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Zonas(@JsonProperty(value= "id")	Integer id,@JsonProperty(value = "nombre") String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
}
