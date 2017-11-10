package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ServirProducto {
	
	@JsonProperty(value ="usuarioRestaurante")
	private Usuario usuarioRestaurante;
	@JsonProperty(value ="numero_pedido")
	private Integer numero_pedido;
	@JsonProperty(value ="servido")
	private Boolean servido;
	public ServirProducto(@JsonProperty(value ="usuarioRestaurante")Usuario usuarioRestaurante,@JsonProperty(value ="numero_pedido") Integer numero_pedido, @JsonProperty(value ="servido")Boolean servido) {
		super();
		this.usuarioRestaurante = usuarioRestaurante;
		this.numero_pedido = numero_pedido;
		this.servido = servido;
	}
	public Usuario getUsuarioRestaurante() {
		return usuarioRestaurante;
	}
	public void setUsuarioRestaurante(Usuario usuarioRestaurante) {
		this.usuarioRestaurante = usuarioRestaurante;
	}
	public Integer getNumero_pedido() {
		return numero_pedido;
	}
	public void setNumero_pedido(Integer numero_pedido) {
		this.numero_pedido = numero_pedido;
	}
	public Boolean getServido() {
		return servido;
	}
	public void setServido(Boolean servido) {
		this.servido = servido;
	}
	
	
}
