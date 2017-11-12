package core.impl.negocio;

import core.dfs.IStrategy;
import dominio.EntidadeDominio;
import entities.cadastros.CartaoCredito;

public class ValidarCamposCartaoCredito implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		CartaoCredito c = (CartaoCredito) entidade;
		if(c.getTitular() == null || c.getNumero() == null || c.getBandeira() == null || c.getCodigo() == null)
			return "Titular,Numero,Bandeira e codigo são dados obrigatorios";
		return null;
	}

}
