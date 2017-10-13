package web.controle.web;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import utils.*;

import core.dfs.aplicacao.Resultado;
import core.impl.dao.CategoriaDAO;
import core.impl.dao.GrupoPrecificacaoDAO;
import dominio.EntidadeDominio;
import entities.produto.Categoria;
import entities.produto.Dimensao;
import entities.produto.GrupoPrecificacao;
import entities.produto.Livro;
import entities.usuarios.Usuario;
import web.controle.web.command.ICommand;
import web.controle.web.command.impl.ConsultarCommand;
import web.controle.web.command.impl.SalvarCommand;

import java.util.Map;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ManagedBean
@ViewScoped
public class LivroView implements Serializable {

	private static final long serialVersionUID = 1L;
	private Livro livro;
	private Resultado resultado;
	private List<Livro> livros;
	private static Map<String, ICommand> commands;
	private List<Categoria> categorias = new ArrayList<Categoria>();
	private List<GrupoPrecificacao> gruposPrecificacao = new ArrayList<GrupoPrecificacao>();
	
	public LivroView() {
		// TODO Auto-generated constructor stub
		this.livro = new Livro();
		commands = new HashMap<String, ICommand>();
		commands.put("SALVAR", new SalvarCommand());
		commands.put("CONSULTAR", new ConsultarCommand());
		if(SessionUtils.getSession().getAttribute("resultado") != null){
			resultado = (Resultado) SessionUtils.getSession().getAttribute("resultado");
		}else{
			resultado = new Resultado();
		}
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		try {
			livro = new Livro();
			Usuario usuario = new Usuario();
			usuario.setNomeusuario(SessionUtils.getSession().getAttribute("username").toString());
			this.livro.setUsuario(usuario);
			livro.setCategoria(categorias);
			livro.setDimensao(new Dimensao());
			livro.setGrupoPrecificacao(new GrupoPrecificacao());
			CategoriaDAO categoriaDAO = new CategoriaDAO();
			GrupoPrecificacaoDAO grupoPrecificacaoDAO = new GrupoPrecificacaoDAO();
			List<EntidadeDominio> entities = categoriaDAO.consultar(null);
			for (EntidadeDominio entidade : entities) {
				Categoria c = (Categoria) entidade;
				categorias.add(c);
			}
			entities = grupoPrecificacaoDAO.consultar(null);
			for (EntidadeDominio entidade : entities) {
				GrupoPrecificacao g = (GrupoPrecificacao) entidade;
				gruposPrecificacao.add(g);
			}
			if(SessionUtils.getSession().getAttribute("livros") != null){
				this.livros = (List<Livro>) SessionUtils.getSession().getAttribute("livros");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	public Resultado getResultado() {
		return resultado;
	}

	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public List<GrupoPrecificacao> getGruposPrecificacao() {
		return gruposPrecificacao;
	}

	public void setGruposPrecificacao(List<GrupoPrecificacao> gruposPrecificacao) {
		this.gruposPrecificacao = gruposPrecificacao;
	}

	public Livro getLivro() {
		return this.livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public void processar(String operacao) throws IOException {
		ICommand command = commands.get(operacao);
		Resultado resultado = command.execute(this.livro);
		List<Livro> lstLivros = new ArrayList<Livro>();
		if(resultado != null && resultado.getEntidades() != null){
			if (resultado.getMsg() == null) {
				if (operacao.equals("SALVAR")) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,"Informação","Livro salvo com sucesso"));
					return;
				}
				if(operacao.equals("CONSULTAR")){
					for(int i = 0; i < resultado.getEntidades().size();i++){
						 lstLivros.add((Livro) resultado.getEntidades().get(i));
					}
					SessionUtils.getSession().setAttribute("livros", lstLivros);
					FacesContext.getCurrentInstance().getExternalContext().redirect("listarlivros.xhtml");
				}
				if(operacao.equals("VISUALIZAR")){
					 FacesContext.getCurrentInstance().getExternalContext().redirect("editar.xhtml");
				}
				
				if(operacao.equals("ALTERAR")){
					
				}
			}else{
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro de preenchimento",resultado.getMsg()));
			}
		}
	}
	
	public void processar(String operacao,int livro) throws IOException{
		for(Livro l : livros){
			if(l.getId().equals(livro))
				this.livro = l;
		}
		processar(operacao);
	}
	
	public void editar(Integer livro) throws IOException{
		Livro objLivro = null;
		for(Livro l : livros){
			if(l.getId() == livro)
				objLivro = l;
		}
		if(objLivro != null){
			SessionUtils.getSession().setAttribute("livro", objLivro);
		}
		FacesContext.getCurrentInstance().getExternalContext().redirect("editar.xhtml");
	}
	
	public void navConsultar() throws IOException{
		SessionUtils.getSession().removeAttribute("resultado");
		SessionUtils.getSession().setAttribute("livro", this.livro);
		FacesContext.getCurrentInstance().getExternalContext().redirect("consultar.xhtml");
	}
	
	public void reset() throws IOException{
		FacesContext.getCurrentInstance().getExternalContext().redirect("cadastrar.xhtml");
	}
	
	public void inativar() throws IOException{
		FacesContext.getCurrentInstance().getExternalContext().redirect("inativar.xhtml");
	}
}
