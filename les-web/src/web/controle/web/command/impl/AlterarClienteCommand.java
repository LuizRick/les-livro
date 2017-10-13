package web.controle.web.command.impl;

import core.dfs.aplicacao.Resultado;
import dominio.EntidadeDominio;

public class AlterarClienteCommand extends AbstractCommand {

	@Override
	public Resultado execute(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return clienteFachada.alterar(entidade);
	}

}
