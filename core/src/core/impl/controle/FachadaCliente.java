package core.impl.controle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.dfs.IDAO;
import core.dfs.IFachada;
import core.dfs.IStrategy;
import core.dfs.aplicacao.Resultado;
import core.impl.dao.CartaoCreditoDAO;
import core.impl.dao.ClienteDAO;
import core.impl.negocio.ValidarCamposCartaoCredito;
import core.impl.negocio.ValidarCamposObrigatoriosCliente;
import dominio.EntidadeDominio;
import entities.cadastros.Cartao;
import entities.cadastros.CartaoCredito;
import entities.cadastros.Cliente;

public class FachadaCliente implements IFachada {

	private Map<String, IDAO> daos;
	private Map<String, Map<String, List<IStrategy>>> rns;
	private Resultado resultado;
	
	public FachadaCliente(){
	
		daos = new HashMap<String, IDAO>();
		
		rns = new HashMap<String, Map<String, List<IStrategy>>>();
		
		ClienteDAO clienteDAO = new ClienteDAO();
		CartaoCreditoDAO cartaoCreditoDAO = new CartaoCreditoDAO();
		
		daos.put(Cliente.class.getName(), clienteDAO);
		daos.put(CartaoCredito.class.getName(), cartaoCreditoDAO);
		List<IStrategy> rnsSalvarCliente = new ArrayList<IStrategy>();
		List<IStrategy> rnsSalvarCartao = new ArrayList<>();
		rnsSalvarCartao.add(new ValidarCamposCartaoCredito());
		rnsSalvarCliente.add(new ValidarCamposObrigatoriosCliente());
		
		Map<String, List<IStrategy>> rnsCliente = new HashMap<String, List<IStrategy>>();
		Map<String,List<IStrategy>> rnsCartaoCredito = new HashMap<>();
		rnsCliente.put("SALVAR", rnsSalvarCliente);
		rnsCartaoCredito.put("SALVAR", rnsSalvarCartao);
		
		
		rns.put(Cliente.class.getName(), rnsCliente);
		rns.put(CartaoCredito.class.getName(), rnsCartaoCredito);
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
