package vos;

	import org.codehaus.jackson.annotate.JsonProperty;

	public class Menu {
	/**
	* Nombre del restaurante (PK)
	*/
	@JsonProperty(value= "nombre")
	private String nombre;

	public Menu( @JsonProperty(value= "nombre")String nombre){
	super();
	this.nombre = nombre;

	}

	public String getNombre() {
	return nombre;
	}

	public void setNombre(String nombre) {
	this.nombre = nombre;
	}

	


	}
