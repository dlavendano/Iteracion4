package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Restaurante {
	/**
	 * Nombre del restaurante (PK)
	 */
	@JsonProperty(value= "nombre")
	private String nombre;
	/**
	 * Tipo de restaurante
	 */
	@JsonProperty(value= "tipo")
	private String tipo;
	/**
	 * Administrador del restaurante
	 */
	@JsonProperty(value= "id_administrador")
	private Integer id_administrador;
	/**
	 * id de la zona en la que se encuentra el restaurante
	 */
	@JsonProperty(value = "id_zona")
	private Integer id_zona;
	
	public Restaurante(@JsonProperty(value= "nombre") String nombre,@JsonProperty(value= "tipo") String tipo, @JsonProperty(value= "id_administrador") Integer id_administrador, @JsonProperty(value="id_zona")Integer id_zona){
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.id_administrador = id_administrador;
		this.id_zona = id_zona;
	}

	public Integer getId_zona() {
		return id_zona;
	}

	public void setId_zona(Integer id_zona) {
		this.id_zona = id_zona;
	}

	public void setId_administrador(Integer id_administrador) {
		this.id_administrador = id_administrador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getId_administrador() {
		return id_administrador;
	}

	public void setId_adminsitrador(Integer id_administrador) {
		this.id_administrador = id_administrador;
	}

}
