package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Producto {
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
	
	@JsonProperty(value= "tiempoP")
	private Integer tiempoP;
	
	@JsonProperty(value= "categoria")
	private Integer categoria;
	
	public Producto( @JsonProperty(value= "nombre") String nombre, @JsonProperty(value= "descripcion") String descripcion, @JsonProperty(value= "tiempoP") Integer tiempoP, @JsonProperty(value= "categoria") Integer categoria){
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.tiempoP = tiempoP;
		this.categoria=categoria;
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

	public Integer getTiempoP() {
		return tiempoP;
	}

	public void setTiempoP(Integer tiempoP) {
		this.tiempoP = tiempoP;
	}

	public Integer getCategoria() {
		return categoria;
	}

	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}

}
