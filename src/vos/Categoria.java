package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Categoria {
	/**
	 * Nombre del restaurante (PK)
	 */
	@JsonProperty(value= "nombre")
	private String nombre;
	/**
	 * Tipo de restaurante
	 */
	@JsonProperty(value= "id")
	private Integer id;
	
	public Categoria( @JsonProperty(value= "id") Integer id, @JsonProperty(value= "nombre") String nombre){
		super();
		this.nombre = nombre;
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	

	
}
