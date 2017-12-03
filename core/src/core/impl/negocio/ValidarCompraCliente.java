package core.impl.negocio;

import core.dfs.IStrategy;
import core.util.ValidateUtils;
import dominio.EntidadeDominio;
import entities.cadastros.CartaoCredito;
import entities.cadastros.CupomCompra;
import entities.venda.Compra;

public class ValidarCompraCliente implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		ValidateUtils utils = new ValidateUtils();
		Compra compra  = (Compra) entidade;
		Double totalCupom = 0.0;
		for(CupomCompra c : compra.getCuponCompra()) {
			totalCupom += c.getValor(); 
		}
		if(compra.getCuponCompra().size() > 1)
			return "So e possivel utilizar um cupom de compra para cada compra";
		for(int i = 0; i < compra.getFormasPagamento().size();i++) {
			CartaoCredito cartao = compra.getFormasPagamento().get(i);
			if(cartao.getValor() < 10.0D && (compra.getTotal() - totalCupom) > 10)
				return "Cada cartão deve ter o pagamento minimo de R$10 por cartão(so é permitindo apenas se a soma dos totais forem menores que R$10";
		}
		if(totalCupom > compra.getTotal() && compra.getTotal() - totalCupom > 25) {
			return "O cupom de compras ultrapassa muito o valor total da compra use menos cupons";
		}
		
		return null;
	}

}
