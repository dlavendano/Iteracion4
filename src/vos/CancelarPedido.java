package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class CancelarPedido {
	@JsonProperty(value = "cliente")
	private Usuario cliente;
	@JsonProperty(value = "pedido")
	private Integer pedido;

	
	public CancelarPedido(@JsonProperty(value = "cliente") Usuario cliente, @JsonProperty(value = "pedido")Integer pedido) {
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


	public Integer getPedido() {
		return pedido;
	}


	public void setPedido(Integer pedido) {
		this.pedido = pedido;
	}
	
}
