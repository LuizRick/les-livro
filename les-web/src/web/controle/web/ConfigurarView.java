package web.controle.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import core.dfs.aplicacao.Resultado;
import core.impl.dao.StatusVendaDAO;
import dominio.EntidadeDominio;
import entities.produto.StatusVenda;
import web.controle.web.command.ICommand;
import web.controle.web.command.impl.AlterarCommand;
import web.controle.web.command.impl.SalvarCommand;

@ManagedBean
@ViewScoped
public class ConfigurarView {
	
	private StatusVendaDAO dao;
	private StatusVenda venda = new StatusVenda();
	private String operacao = new String();
	private static Map<String, ICommand> commands;
	
	public ConfigurarView() throws SQLException{
		// TODO Auto-generated constructor stub
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		commands = new HashMap<String, ICommand>();
		commands.put("SALVAR", new SalvarCommand());
		commands.put("ALTERAR", new AlterarCommand());
		if(params.get("id") != null){
			this.venda.setIdLivro(Integer.parseInt(params.get("id")));
			dao = new StatusVendaDAO();
			List<EntidadeDominio> lst = dao.consultar(this.venda);
			
			if(lst.size() > 0){
				venda = (StatusVenda) lst.get(0);
				this.setOperacao("ALTERAR");
			}
			else
			{
				this.venda = new StatusVenda();
				this.setOperacao("SALVAR");
			}
		}
	}
	
	@PostConstruct
	public void init() throws SQLException{
		this.venda = new StatusVenda();
	}
	
	public StatusVenda getVenda(){
		return this.venda;
	}
	
	public void setVenda(StatusVenda venda){
		this.venda = venda;
	}
	
	public void processar() throws IOException{
		operacao = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("operacao");
		ICommand command = commands.get(operacao);
		Resultado resultado = command.execute(venda);
		if(resultado != null){
			if(resultado.getMsg() == null){
				if(operacao.equals("SALVAR")){
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,"Informação","Status do livro alterado com sucesso"));
					FacesContext.getCurrentInstance().getExternalContext().redirect("consultar.xhtml");
					return;
				}
			}
		}
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}
}
