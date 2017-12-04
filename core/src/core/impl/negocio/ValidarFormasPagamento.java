package core.impl.negocio;

import java.util.Date;

import core.dfs.IStrategy;
import dominio.EntidadeDominio;
import entities.cadastros.CartaoCredito;
import entities.cadastros.CupomCompra;
import entities.cadastros.TipoCupom;
import entities.venda.Compra;


public class ValidarFormasPagamento implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		Compra compra = (Compra) entidade;
		Date now = new Date();	
		int qtd_cuponsPromocionais = 0;
		Double total = 0.0D;
		for(CupomCompra c: compra.getCuponCompra()) {
			if(c.getTipo() ==  TipoCupom.Promocional)
				qtd_cuponsPromocionais++;
			
			if(!c.getValidade().after(now))
				return "Cupom promocional passou da data de validade";
			
			total += c.getValor();
		}
		
		if(qtd_cuponsPromocionais > 1)
			return "So e possivel usar um cupom promocional por compra";
		
		for(CartaoCredito c : compra.getFormasPagamento()) {
			if(c.getValidade().before(now)) {
				return "Cartão vencido por favor utilize outro cartão: " + c.getNumero() + ";";
			}
			total += c.getValor();
		}
		
		if(total < compra.getTotal() + compra.getFrete().getValor()) {
			return "valor do pagamento e menor do que o total da compra";
		}
		return null;
	}

}
