package entities.venda;

import java.util.List;

public class CarrinhoCompra {
	
	private List<Item> itens;

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
}
