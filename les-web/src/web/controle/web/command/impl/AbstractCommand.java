package web.controle.web.command.impl;

import core.dfs.IFachada;
import core.impl.controle.FachadaCliente;
import core.impl.controle.FachadaLivro;
import web.controle.web.command.ICommand;

public abstract class AbstractCommand implements ICommand {
	
	protected IFachada livroFachada = new FachadaLivro();
	protected IFachada clienteFachada = new FachadaCliente();

}
