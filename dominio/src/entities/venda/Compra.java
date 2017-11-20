package entities.venda;

import java.util.List;

import dominio.EntidadeDominio;
import entities.cadastros.Cartao;
import entities.cadastros.CartaoCredito;
import entities.cadastros.Cliente;
import entities.cadastros.CupomCompra;

public class Compra extends EntidadeDominio {
	private Cliente cliente;
	private CarrinhoCompra produtos;
	private Frete frete;
	private List<CartaoCredito> formasPagamento;
	private List<CupomCompra> cuponCompra;
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
	public List<CartaoCredito> getFormasPagamento() {
		return formasPagamento;
	}
	public void setFormasPagamento(List<CartaoCredito> formasPagamento) {
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
	
	public List<CupomCompra> getCuponCompra() {
		return cuponCompra;
	}
	
	public void setCuponCompra(List<CupomCompra> cuponCompra) {
		this.cuponCompra = cuponCompra;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
