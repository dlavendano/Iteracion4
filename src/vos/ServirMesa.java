package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ServirMesa {
	@JsonProperty(value ="usuarioRestaurante")
	private Usuario usuarioRestaurante;
	@JsonProperty(value ="numero_pedido_mesa")
	private Integer numero_pedido_mesa;
	@JsonProperty(value ="servido")
	private Boolean servido;
	public ServirMesa(@JsonProperty(value ="usuarioRestaurante")Usuario usuarioRestaurante,@JsonProperty(value ="numero_pedido_mesa") Integer numero_pedido_mesa, @JsonProperty(value ="servido")Boolean servido) {
		super();
		this.usuarioRestaurante = usuarioRestaurante;
		this.numero_pedido_mesa = numero_pedido_mesa;
		this.servido = servido;
	}
	public Usuario getUsuarioRestaurante() {
		return usuarioRestaurante;
	}
	public void setUsuarioRestaurante(Usuario usuarioRestaurante) {
		this.usuarioRestaurante = usuarioRestaurante;
	}
	public Integer getNumero_pedido_mesa() {
		return numero_pedido_mesa;
	}
	public void setNumero_pedido_mesa(Integer numero_pedido_mesa) {
		this.numero_pedido_mesa = numero_pedido_mesa;
	}
	public Boolean getServido() {
		return servido;
	}
	public void setServido(Boolean servido) {
		this.servido = servido;
	}
}



