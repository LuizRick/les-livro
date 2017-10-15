package core.impl.controle;

import core.dfs.IFachada;

import java.sql.SQLException;
import java.util.*;
import core.dfs.aplicacao.*;
import core.impl.dao.LivroDAO;
import core.impl.dao.StatusVendaDAO;
import core.impl.negocio.ValidarCamposConfiguracao;
import core.impl.negocio.ValidarCamposObrigadoriosLivro;
import dominio.EntidadeDominio;
import entities.produto.Livro;
import entities.produto.StatusVenda;
import core.dfs.*;

public class FachadaLivro implements IFachada {
	private Map<String, IDAO> daos;
	private Map<String, Map<String, List<IStrategy>>> rns;
	private Resultado resultado;
	
	public FachadaLivro(){
	
		daos = new HashMap<String, IDAO>();
		
		rns = new HashMap<String, Map<String, List<IStrategy>>>();
		
		LivroDAO livroDAO = new LivroDAO();
		StatusVendaDAO statusVendaDAO = new StatusVendaDAO();
		daos.put(Livro.class.getName(), livroDAO);
		daos.put(StatusVenda.class.getName(),statusVendaDAO);
		
		List<IStrategy> rnsSalvarLivro = new ArrayList<IStrategy>(),
				rnsSalvarConf = new ArrayList<>();
		rnsSalvarLivro.add(new ValidarCamposObrigadoriosLivro());
		rnsSalvarConf.add(new ValidarCamposConfiguracao());
		Map<String, List<IStrategy>> rnsLivro = new HashMap<String, List<IStrategy>>(),
				rnsConfLivro = new HashMap<>();
		
		rnsLivro.put("SALVAR", rnsSalvarLivro);	
		rnsConfLivro.put("SALVAR", rnsSalvarConf);
		
		rns.put(Livro.class.getName(), rnsLivro);
		rns.put(StatusVenda.class.getName(), rnsConfLivro);
	}
	
	
	@Override
	public Resultado salvar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		resultado = new Resultado();
		String nmClass = entidade.getClass().getName();
		String msg = executaRegras(entidade, "SALVAR");

		if (msg == null) {
			IDAO dao = daos.get(nmClass);
			try {
				dao.salvar(entidade);
				List<EntidadeDominio> entidades = new ArrayList<>();
				entidades.add(entidade);
				resultado.setEntidades(entidades);
			} catch (SQLException e) {
				e.printStackTrace();
				resultado.setMsg("Nao foi possivel realizar o registro!");
			}
		} else {
			resultado.setMsg(msg);
		}
		return resultado;
	}

	@Override
	public Resultado alterar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		resultado = new Resultado();
		String nmClass = entidade.getClass().getName();
		String msg = executaRegras(entidade, "ALTERAR");

		if (msg == null) {
			IDAO dao = daos.get(nmClass);
			try {
				dao.alterar(entidade);
				List<EntidadeDominio> entidades = new ArrayList<>();
				entidades.add(entidade);
				resultado.setEntidades(entidades);
			} catch (SQLException e) {
				e.printStackTrace();
				resultado.setMsg("Nao foi possivel realizar o registro!");
			}
		} else {
			resultado.setMsg(msg);
		}
		return resultado;
	}

	@Override
	public Resultado excluir(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		resultado = new Resultado();
		String nmClass = entidade.getClass().getName();
		String msg = executaRegras(entidade, "EXCLUIR");

		if (msg == null) {
			IDAO dao = daos.get(nmClass);
			try {
				dao.salvar(entidade);
				List<EntidadeDominio> entidades = new ArrayList<>();
				entidades.add(entidade);
				resultado.setEntidades(entidades);
			} catch (SQLException e) {
				e.printStackTrace();
				resultado.setMsg("Nao foi possivel realizar o registro!");
			}
		} else {
			resultado.setMsg(msg);
		}
		return resultado;
	}

	@Override
	public Resultado consultar(EntidadeDominio entidade) {
		resultado = new Resultado();
		String nmClasse = entidade.getClass().getName();	
		
		String msg = executaRegras(entidade, "CONSULTAR");
		
		
		if(msg == null){
			IDAO dao = daos.get(nmClasse);
			try {
				
				resultado.setEntidades(dao.consultar(entidade));
			} catch (SQLException e) {
				e.printStackTrace();
				resultado.setMsg("Nao foi possivel realizar a consulta!");
				
			}
		}else{
			resultado.setMsg(msg);
		}
		
		return resultado;
	}

	@Override
	public Resultado visualizar(EntidadeDominio entidade) {
		resultado = new Resultado();
		resultado.setEntidades(new ArrayList<EntidadeDominio>(1));
		resultado.getEntidades().add(entidade);		
		return resultado;
	}

	private String executaRegras(EntidadeDominio entidade, String operacao) {
		String nmClass = entidade.getClass().getName();
		StringBuilder msg = new StringBuilder();
		Map<String, List<IStrategy>> regrasOperacao = rns.get(nmClass);
		if (regrasOperacao != null) {
			List<IStrategy> regras = regrasOperacao.get(operacao);
			if (regras != null) {
				for (IStrategy s : regras) {
					String m = s.processar(entidade);
					if (m != null) {
						msg.append(m);
						msg.append("\n");
					}
				}
			}
		}

		if (msg.length() > 0)
			return msg.toString();
		else
			return null;
	}
}
