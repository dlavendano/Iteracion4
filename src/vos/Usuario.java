package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Usuario {
	
public static final Integer Cliente = 1 ;
public static final Integer UsuarioRestaurante = 2 ;
public static final Integer Administrador = 3 ;

@JsonProperty(value = "id")
private Integer id;
@JsonProperty(value = "nombre")
private String nombre;
@JsonProperty(value = "identificacion")
private Integer identificacion;
@JsonProperty(value = "correo")
private String correo;
@JsonProperty(value = "rol")
private Integer rol;

@JsonProperty(value = "contrase�a")
private String contrase�a;

public Usuario(@JsonProperty(value = "id") Integer id,@JsonProperty(value = "nombre") String nombre,
		@JsonProperty(value = "identificacion") Integer identificacion,@JsonProperty(value = "correo") String correo,
		@JsonProperty(value = "rol")Integer rol,@JsonProperty(value = "contrase�a")
String contrase�a ){
	super();
	this.id = id;
	this.nombre = nombre;
	this.identificacion = identificacion;
	this.correo = correo;
	this.rol = rol;
	this.contrase�a = contrase�a;
	
}


public String getContrase�a() {
	return contrase�a;
}

public void setContrase�a(String contrase�a) {
	this.contrase�a = contrase�a;
}

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

public Integer getIdentificacion() {
	return identificacion;
}

public void setIdentificacion(Integer identificacion) {
	this.identificacion = identificacion;
}

public String getCorreo() {
	return correo;
}

public void setCorreo(String correo) {
	this.correo = correo;
}

public Integer getRol() {
	return rol;
}

public void setRol(Integer rol) {
	this.rol = rol;
}

}
