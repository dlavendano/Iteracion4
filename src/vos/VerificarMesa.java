package vos;
import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

public class VerificarMesa {
	@JsonProperty(value = "cliente")
	private Usuario cliente;
	@JsonProperty(value = "numeroPedidoMesa")
	private Integer numeroPedidoMesa;
	@JsonProperty(value = "pedido")
	private List<String> pedido;

	public VerificarMesa(@JsonProperty(value = "cliente") Usuario cliente, @JsonProperty(value = "pedido")List<String> pedido,
			@JsonProperty(value = "numeroPedidoMesa")Integer numeroPedidoMesa) {
		super();
		this.cliente = cliente;
		this.pedido = pedido;
		this.numeroPedidoMesa=numeroPedidoMesa;
	}

	public Integer getNumeroMesa() {
		return numeroPedidoMesa;
	}

	public void setNumeroMesa(Integer numeroMesa) {
		this.numeroPedidoMesa = numeroMesa;
	}

	public Usuario getCliente() {
		return cliente;
	}


	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}


	public List<String> getPedido() {
		return pedido;
	}


	public void setPedido(List<String> pedido) {
		this.pedido = pedido;
	}
}
