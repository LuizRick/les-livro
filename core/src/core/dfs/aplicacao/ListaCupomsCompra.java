package core.dfs.aplicacao;

import java.util.ArrayList;
import java.util.List;

import entities.cadastros.CupomCompra;

public class ListaCupomsCompra {
	List<CupomCompra> cupons;
	
	public ListaCupomsCompra() {
		// TODO Auto-generated constructor stub
		cupons = new ArrayList<>();
		cupons.add(new CupomCompra("010", "vale-10", 10.0));
		cupons.add(new CupomCompra("025", "vale-25", 25.0));
		cupons.add(new CupomCompra("050", "vale-50", 50.0));
		cupons.add(new CupomCompra("075", "vale-75", 75.0));
		cupons.add(new CupomCompra("100", "vale-100", 100.0));
	}
	
	public void addCupom(CupomCompra cupom) {
		this.cupons.add(cupom);
	}
	
	public CupomCompra getCupom(String codigo) {
		for(CupomCompra cupom : cupons) {
			if(cupom.getCodigoCupom().equals(codigo))
				return cupom;
		}
		return null;
	}
}
