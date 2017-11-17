package entities.venda;

import java.util.List;

import dominio.EntidadeDominio;

public class Compra extends EntidadeDominio {
	private CarrinhoCompra produtos;
	private Frete frete;
	private List<IFormaPagamento> formasPagamento;
	private StatusCompra statusCompra;
	private Double total;
	
	public CarrinhoCompra getProdutos() {
		return produtos;
	}
	public void setProdutos(CarrinhoCompra produtos) {
		this.produtos = produtos;
	}
	public Frete getFrete() {
		return frete;
	}
	public void setFrete(Frete frete) {
		this.frete = frete;
	}
	public List<IFormaPagamento> getFormasPagamento() {
		return formasPagamento;
	}
	public void setFormasPagamento(List<IFormaPagamento> formasPagamento) {
		this.formasPagamento = formasPagamento;
	}
	public StatusCompra getStatusCompra() {
		return statusCompra;
	}
	public void setStatusCompra(StatusCompra statusCompra) {
		this.statusCompra = statusCompra;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
}
