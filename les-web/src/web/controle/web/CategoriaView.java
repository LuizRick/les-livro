package web.controle.web;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;


import core.impl.dao.CategoriaDAO;
import core.impl.dao.GrupoPrecificacaoDAO;
import dominio.EntidadeDominio;
import entities.produto.Categoria;
import entities.produto.GrupoPrecificacao;

@ManagedBean(name="categoriaView")
public class CategoriaView {

	private List<Categoria> categorias = new ArrayList<Categoria>();
	private List<GrupoPrecificacao> gruposPrecificacao = new ArrayList<GrupoPrecificacao>();
	
	
	public List<GrupoPrecificacao> getGruposPrecificacao() {
		return gruposPrecificacao;
	}

	public void setGruposPrecificacao(List<GrupoPrecificacao> gruposPrecificacao) {
		this.gruposPrecificacao = gruposPrecificacao;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	
	@PostConstruct
	public void init(){
		try{
			CategoriaDAO categoriaDAO = new CategoriaDAO();
			GrupoPrecificacaoDAO grupoPrecificacaoDAO = new GrupoPrecificacaoDAO();
			List<EntidadeDominio> entities = categoriaDAO.consultar(null);
			for(EntidadeDominio entidade : entities){
				Categoria c = (Categoria) entidade;
				categorias.add(c);
			}
			entities = grupoPrecificacaoDAO.consultar(null);
			for(EntidadeDominio entidade: entities){
				GrupoPrecificacao g = (GrupoPrecificacao) entidade;
				gruposPrecificacao.add(g);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
