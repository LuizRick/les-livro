package entities.venda;

import entities.produto.IProduto;

public class Item {
	private IProduto produto;
	private Integer quantidade;
	
	public IProduto getProduto() {
		return this.produto;
	}
	
	public void setProduto(IProduto produto) {
		this.produto = produto;
	}
	
	public Integer getQuantidade() {
		return this.quantidade;
	}
	
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
}
