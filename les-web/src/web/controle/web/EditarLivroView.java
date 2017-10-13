package web.controle.web;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import core.dfs.aplicacao.Resultado;
import core.impl.dao.CategoriaDAO;
import core.impl.dao.GrupoPrecificacaoDAO;
import dominio.EntidadeDominio;
import entities.produto.Categoria;
import entities.produto.Dimensao;
import entities.produto.GrupoPrecificacao;
import entities.produto.Livro;
import entities.usuarios.Usuario;
import utils.SessionUtils;
import web.controle.web.command.ICommand;
import web.controle.web.command.impl.AlterarCommand;


@ManagedBean(name="editarView")
@ViewScoped
public class EditarLivroView implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Livro livro;
	
	private static Map<String, ICommand> commands;
	private List<GrupoPrecificacao> gruposPrecificacao = new ArrayList<GrupoPrecificacao>();
	private Resultado resultado;
	private List<Livro> livros;
	
	private List<Categoria> categorias = new ArrayList<Categoria>();
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
		return livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	
	public Resultado getResultado(){
		return this.resultado;
	}
	
	public EditarLivroView() {
		// TODO Auto-generated constructor stub
		livro = new Livro();
		livro.setCategoria(categorias);
		livro.setDimensao(new Dimensao());
		livro.setGrupoPrecificacao(new GrupoPrecificacao());
		livros = new ArrayList<Livro>();
		commands = new HashMap<String, ICommand>();
		commands.put("ALTERAR", new AlterarCommand());
	}
	
	@PostConstruct
	public void init(){
		try {
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
			if(SessionUtils.getSession().getAttribute("livro") != null){
				this.livro = (Livro) SessionUtils.getSession().getAttribute("livro");
			}
			Usuario usuario = new Usuario();
			usuario.setNomeusuario(SessionUtils.getSession().getAttribute("username").toString());
			this.livro.setUsuario(usuario);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void processar(String operacao){
		ICommand command = commands.get(operacao);
		Resultado resultado = command.execute(this.livro);
		if(resultado != null){
			if(resultado.getMsg() == null){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,"Informação","Livro alterado com sucesso"));
				return;
			}else{
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro",resultado.getMsg()));
			}
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL,"Erro","Erro no precessamento da operacao"));
	}
																																																																																																	
	public List<Livro> getLivros() {
		return livros;
	}
	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}
	
	public void reset() throws IOException{
		FacesContext.getCurrentInstance().getExternalContext().redirect("cadastrar.xhtml");
	}
}
