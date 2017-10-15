package core.impl.negocio;

import core.dfs.IStrategy;
import dominio.EntidadeDominio;
import entities.produto.StatusVenda;

public class ValidarCamposConfiguracao implements IStrategy {
	
	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		StatusVenda s = (StatusVenda) entidade;
		
		if(s.getMotivo() == null || s.getMotivo().length()  <=0)
			return "E necessario um motivo de inativação";
		return null;
	}
}
