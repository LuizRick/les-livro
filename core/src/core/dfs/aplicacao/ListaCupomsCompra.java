package core.dfs.aplicacao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import core.util.ConvertDate;
import entities.cadastros.CupomCompra;
import entities.cadastros.TipoCupom;

public class ListaCupomsCompra {
	List<CupomCompra> cupons;
	
	public ListaCupomsCompra() {
		// TODO Auto-generated constructor stub
		cupons = new ArrayList<>();
		cupons.add(new CupomCompra("010", "vale-10", 10.0, TipoCupom.Promocional));
		cupons.add(new CupomCompra("025", "vale-25", 25.0,TipoCupom.Promocional));
		cupons.add(new CupomCompra("050", "vale-50", 50.0,TipoCupom.Promocional));
		cupons.add(new CupomCompra("075", "vale-75", 75.0,TipoCupom.Promocional));
		cupons.add(new CupomCompra("100", "vale-100", 100.0,TipoCupom.Promocional));
		for(CupomCompra c : cupons){
			if(c.getCodigoCupom() == "010" || c.getCodigoCupom() == "025")
				c.setValidade(ConvertDate.converteStringDate("25/12/2017"));
			if(c.getCodigoCupom() == "050" || c.getCodigoCupom() == "075")
				c.setValidade(ConvertDate.converteStringDate("04/12/2017"));
			else
				c.setValidade(ConvertDate.converteStringDate("20/12/2017"));
		}
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
