package core.impl.negocio;

import core.dfs.IStrategy;
import core.util.CCUtils;
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
			return "Titular,Numero,Bandeira e codigo s�o dados obrigatorios";
		if(c.getNumero().length() < 19 || !CCUtils.validCCNumber(c.getNumero().replaceAll("-","")))
			return "Numero de cart�o invalido";
		return null;
	}

}
