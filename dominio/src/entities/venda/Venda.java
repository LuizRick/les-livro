package entities.venda;

import dominio.EntidadeDominio;
import entities.produto.IProduto;
import entities.produto.StatusVenda;

public class Venda extends EntidadeDominio {
	private IProduto produto;
	private Double valor;
	private StatusVenda statusVenda;
	private Integer Idproduto;
	
	public Integer getIdproduto() {
		return Idproduto;
	}

	public void setIdproduto(Integer idproduto) {
		Idproduto = idproduto;
	}

	public Venda() {
		this.statusVenda = new StatusVenda();
	}
	
	public Venda(StatusVenda statusVenda) {
		this.statusVenda = statusVenda;
	}
	
	public IProduto getProduto() {
		return produto;
	}
	
	public void setProduto(IProduto produto) {
		this.produto = produto;
	}
	
	public Double getValor() {
		return valor;
	}
	
	public void setValor(Double valor) {
		this.valor = valor;
	}

	public StatusVenda getStatusVenda() {
		return statusVenda;
	}

	public void setStatusVenda(StatusVenda statusVenda) {
		this.statusVenda = statusVenda;
	}
}
