package web.controle.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import core.dfs.aplicacao.Resultado;
import core.impl.dao.ConfiguracaoVendaDAO;
import dominio.EntidadeDominio;
import entities.venda.Venda;
import web.controle.web.command.ICommand;
import web.controle.web.command.impl.AlterarCommand;
import web.controle.web.command.impl.SalvarCommand;

@ManagedBean
@SessionScoped
public class ConfigurarView {
	
	private ConfiguracaoVendaDAO dao;
	private String operacao = new String();
	private Venda venda;
	private static Map<String, ICommand> commands;
	
	public ConfigurarView() throws SQLException{
		// TODO Auto-generated constructor stub
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		commands = new HashMap<String, ICommand>();
		commands.put("SALVAR", new SalvarCommand());
		commands.put("ALTERAR", new AlterarCommand());
		
		if(venda == null)
			venda = new Venda();
		
		if(this.venda.getId() == null)
			this.venda.setId(Integer.parseInt(params.get("id")));
		
		if(this.venda.getId() != null){
			dao = new ConfiguracaoVendaDAO();
			this.venda.setId(this.venda.getId());
			List<EntidadeDominio> lst = dao.consultar(this.venda);
			if(lst != null && lst.size() > 0) {
				venda = (Venda) lst.get(0);
				if(this.venda.getStatusVenda().getId()!= null && this.venda.getStatusVenda().getId() > 0)
					this.setOperacao("ALTERAR");
				else
					this.setOperacao("SALVAR");
			}
			else
			{
				this.venda = new Venda();
				this.setOperacao("SALVAR");
			}
			this.venda.setId(Integer.parseInt(params.get("id")));
		}
	}
	
	
	public Venda getVenda(){
		return this.venda;
	}
	
	public void setVenda(Venda venda){
		this.venda = venda;
	}
	
	public String processar() throws IOException{
		operacao = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("operacao");
		if(operacao.equals("ALTERARVALOR"))
			operacao = "ALTERAR";
		ICommand command = commands.get(operacao);
		Resultado resultado = command.execute(this.venda);
		if(resultado != null){
			if(resultado.getMsg() == null){
				if(operacao.equals("ALTERAR")){
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,"Informação","Produto alterado com sucesso"));
					//FacesContext.getCurrentInstance().getExternalContext().redirect("configurar.xhtml");
					return "configurar";
				}
			}
		}
		return  "consultar?faces-redirect=true";
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}
}
