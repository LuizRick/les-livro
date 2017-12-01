package entities.venda;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoCompra {
	
	private List<Item> itens;

	
	public CarrinhoCompra() {
		// TODO Auto-generated constructor stub
		itens = new ArrayList<Item>();
	}
	
	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
	
	public boolean hasItem(Integer id) {
		for(Item i : itens) {
			if(i.getId() == id)
				return true;
		}
		return false;
	}
}
