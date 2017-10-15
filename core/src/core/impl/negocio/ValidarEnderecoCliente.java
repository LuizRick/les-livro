package core.impl.negocio;

import core.dfs.IStrategy;
import core.util.ValidateUtils;
import dominio.EntidadeDominio;
import entities.cadastros.Cliente;

public class ValidarEnderecoCliente implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		Cliente cli = (Cliente) entidade;
		if(cli.getEndereco().size() <=0)
			return "E necessario ao menos um endereco de entrega";
		return null;
	}

}
