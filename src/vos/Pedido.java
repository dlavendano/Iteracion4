package vos;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;



public class Pedido{


	@JsonProperty(value = "numero_pedido")
	private Integer numero_pedido;
	private Integer precio;
	private Date fecha;
	@JsonProperty(value = "nombreProducto")
	private String nombreProducto;
	@JsonProperty(value ="equivalencia")
	private String equivalencia;
	@JsonProperty(value = "nombreRestaurante")
	private String nombreRestaurante;
	boolean servido;

	public Pedido( @JsonProperty(value = "numero_pedido")Integer numero_pedido,
			@JsonProperty(value = "nombreProducto")String nombreProducto,
			@JsonProperty(value = "nombreRestaurante")String nombreRestaurante)
	{
		this.numero_pedido = numero_pedido;
		this.precio = 0;
		this.fecha = new Date();
		this.nombreRestaurante=nombreRestaurante;
		this.nombreProducto=nombreProducto;
		this.servido=false;
		this.equivalencia= null;
	}

	public String getEquivalencia() {
		return equivalencia;
	}

	public void setEquivalencia(String equivalencia) {
		this.equivalencia = equivalencia;
	}

	public boolean isServido() {
		return servido;
	}


	public void setServido(boolean servido) {
		this.servido = servido;
	}


	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getNombreRestaurante() {
		return nombreRestaurante;
	}

	public void setNombreRestaurante(String nombreRestaurante) {
		this.nombreRestaurante = nombreRestaurante;
	}

	public Integer getNumero_pedido() {
		return numero_pedido;
	}

	public void setNumero_pedido(Integer numero_pedido) {
		this.numero_pedido = numero_pedido;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
