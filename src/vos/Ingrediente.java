package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Ingrediente {
	/**
	 * Nombre del restaurante (PK)
	 */
	@JsonProperty(value= "nombre")
	private String nombre;
	/**
	 * Tipo de restaurante
	 */
	@JsonProperty(value= "descripcion")
	private String descripcion;
	
	public Ingrediente( @JsonProperty(value= "nombre") String nombre, @JsonProperty(value= "descripcion") String descripcion){
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
