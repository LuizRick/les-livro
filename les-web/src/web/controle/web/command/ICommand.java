package web.controle.web.command;

import core.dfs.aplicacao.Resultado;
import dominio.EntidadeDominio;

public interface ICommand {
	public Resultado execute(EntidadeDominio entidade);
}
