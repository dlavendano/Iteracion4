package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Espacios {
	/**
	 * Nombre del restaurante (PK)
	 */
	@JsonProperty(value= "nombre")
	private String nombre;
	@JsonProperty(value= "id_zona")
	private Integer id_zona;
	@JsonProperty(value = "es_abierto")
	private boolean es_abierto;
	@JsonProperty(value ="capacidad")
	private Integer capacidad;
	/**
	 * Tipo de restaurante
	 */
	@JsonProperty(value= "es_apto")
	private boolean es_apto;


	public Espacios( @JsonProperty(value= "nombre") String nombre,	@JsonProperty(value= "id_zona") Integer id_zona,
			@JsonProperty(value = "es_abierto") String es_abierto,	@JsonProperty(value ="capacidad") Integer capacidad, @JsonProperty(value= "es_apto") String es_apto) throws Exception{
		super();
		this.nombre = nombre;
		this.id_zona = id_zona;
		this.capacidad =capacidad;
		if(es_abierto.equals("true"))
			this.es_abierto = true;
		else if(es_abierto.equals("false"))
			this.es_abierto =false;
		else{
			throw new Exception("Error al introducir un boolean");
		}
		if(es_apto.equals("true"))
			this.es_apto=true;
		else if(es_apto.equals("false"))
			this.es_apto=false;
		else{
			throw new Exception("Error al introducir un boolean");
		}
	}
	public Integer getId_zona() {
		return id_zona;
	}

	public void setId_zona(Integer id_zona) {
		this.id_zona = id_zona;
	}

	public boolean isEs_abierto() {
		return es_abierto;
	}

	public void setEs_abierto(boolean es_abierto) {
		this.es_abierto = es_abierto;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean getEs_apto() {
		return es_apto;
	}

	public void setEs_apto(boolean esApto) {
		this.es_apto = esApto;
	}
}
