package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VerificarPedido {
	@JsonProperty(value = "cliente")
	private Usuario cliente;
	@JsonProperty(value = "pedido")
	private Pedido pedido;

	
	public VerificarPedido(@JsonProperty(value = "cliente") Usuario cliente, @JsonProperty(value = "pedido")Pedido pedido) {
		super();
		this.cliente = cliente;
		this.pedido = pedido;
	}


	public Usuario getCliente() {
		return cliente;
	}


	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}


	public Pedido getPedido() {
		return pedido;
	}


	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
}
