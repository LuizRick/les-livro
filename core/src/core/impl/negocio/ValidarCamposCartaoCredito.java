package core.impl.negocio;

import core.dfs.IStrategy;
import core.util.ValidateUtils;
import dominio.EntidadeDominio;
import entities.cadastros.CartaoCredito;

public class ValidarCamposCartaoCredito implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		CartaoCredito c = (CartaoCredito) entidade;
		ValidateUtils utils = new ValidateUtils();
		if (utils.isNullOrEmpty(c.getTitular()) || utils.isNullOrEmpty(c.getNumero())
				|| utils.isNullOrEmpty(c.getBandeira()) || utils.isNullOrEmpty(c.getCodigo()))
			return "Titular,Numero,Bandeira e codigo são dados obrigatorios";
		return null;
	}

}
