package entities.venda;

import java.util.List;

import dominio.EntidadeDominio;

public class Compra extends EntidadeDominio {
	private CarrinhoCompra produtos;
	private Frete frete;
	private List<IFormaPagamento> formasPagamento;
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
}
