package web.controle.web;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import utils.*;

@ManagedBean(name="usuario")
@ApplicationScoped
public class UsuarioBean{

	private String nome;
	
	
	public String getNome(){
		return this.nome;
	}
	
	public void setNome(String nome){
		this.nome = nome;
	}
	
	public void loginUser() throws IOException{
		HttpSession session = SessionUtils.getSession();
		if(this.nome == null || this.nome.length() <= 0){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(
					FacesMessage.SEVERITY_WARN,"Nome Invalido","Por favor adicionar nome usuario"));
			FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
		}else{
			session.setAttribute("username", this.nome);
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		}
	}
	
	public void logout() throws IOException{
		HttpSession session = SessionUtils.getSession();
		session.removeAttribute("username");
		FacesContext.getCurrentInstance().getExternalContext().redirect("/les-web/login.xhtml");
	}
}
