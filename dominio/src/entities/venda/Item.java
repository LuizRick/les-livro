package entities.venda;

import entities.produto.IProduto;

public class Item {
	private Integer id;
	private IProduto produto;
	private Integer quantidade;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
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
