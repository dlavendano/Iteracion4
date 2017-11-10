package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ProductoRestaurante {

		@JsonProperty(value = "nombre_restaurante")
		private String nombre_restaurante;
		@JsonProperty(value = "nombre_producto")
		private String nombre_producto;
		@JsonProperty(value = "costo")
		private int costo;
		@JsonProperty(value = "valor")
		private int valor;
		@JsonProperty(value = "disponibilidad")
		private int disponibilidad;
		
		public ProductoRestaurante (
				@JsonProperty(value = "nombre_restaurante")String nombre_restaurante, 
				@JsonProperty(value = "nombre_producto") String nombre_producto,
				@JsonProperty(value = "costo") int costo,
				@JsonProperty(value = "valor") int valor,
				@JsonProperty(value = "disponibilidad")int disponibilidad)
		{
		this.nombre_restaurante=nombre_restaurante;
		this.nombre_producto= nombre_producto;
		this.costo=costo;
		this.valor=valor;
		this.disponibilidad=disponibilidad;
		}



		public String getNombreProducto() {
			return nombre_producto;
		}



		public void setNombreProducto(String nombreProducto) {
			this.nombre_producto = nombreProducto;
		}



		public String getNombreRestaurante() {
			return nombre_restaurante;
		}

		public void setNombreRestaurante(String nombreRestaurante) {
			this.nombre_restaurante = nombreRestaurante;
		}

		public int getCosto() {
			return costo;
		}

		public void setCosto(int costo) {
			this.costo = costo;
		}

		public int getValor() {
			return valor;
		}

		public void setValor(int valor) {
			this.valor = valor;
		}

		public int getDisponibilidad() {
			return disponibilidad;
		}

		public void setDisponibilidad(int disponibilidad) {
			this.disponibilidad = disponibilidad;
		}
		
}
