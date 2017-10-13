package core.impl.negocio;

import core.dfs.IStrategy;
import core.util.ValidateUtils;
import dominio.EntidadeDominio;
import entities.cadastros.Cliente;

public class ValidarCamposObrigatoriosCliente implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		Cliente c = (Cliente) entidade;
		ValidateUtils utils = new ValidateUtils();
		if(utils.isNullOrEmpty(c.getEmail()) || utils.isNullOrEmpty(c.getSenha()) || utils.isNullOrEmpty(c.getPessoa().getNome())
				|| utils.isNullOrEmpty(c.getPessoa().getGenero()+"")  || c.getPessoa().getNascimento() == null){
			return "Email,Senha,Nome,Genero, Nascimento são campos obrigatorios";
		}
		return null;
	}

}
