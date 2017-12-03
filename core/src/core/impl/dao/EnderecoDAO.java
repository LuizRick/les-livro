package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.dfs.aplicacao.Resultado;
import core.util.ValidateUtils;
import dominio.EntidadeDominio;
import entities.cadastros.Cidade;
import entities.cadastros.Endereco;
import entities.cadastros.Estado;
import entities.cadastros.Pais;

public class EnderecoDAO extends AbstractJdbcDAO {

	public EnderecoDAO() {
		// TODO Auto-generated constructor stub
		super("endereco", "id");
	}
	
	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		openConnection();
		Endereco endereco = (Endereco) entidade;
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO public.endereco(\r\n" + 
				"            logradouro, bairro, cep, numero, complemento, nome, tipo_residencia, \r\n" + 
				"            tipo_logradouro, cidade, estado, pais, id_cliente)\r\n" + 
				"    VALUES (?, ?, ?, ?, ?, ?, ?, \r\n" + 
				"            ?, ?, ?, ?, ?);");
		try {
			pst = connection.prepareStatement(sql.toString());
			connection.setAutoCommit(false);
			pst.setString(1, endereco.getLogradouro());
			pst.setString(2, endereco.getBairro());
			pst.setString(3, endereco.getCep());
			pst.setString(4, endereco.getNumero());
			pst.setString(5, endereco.getComplemento());
			pst.setString(6, endereco.getNome());
			pst.setString(7, endereco.getTipoResidencia());
			pst.setString(8, endereco.getTipoLogradouro());
			pst.setString(9, endereco.getCidade().getNome());
			pst.setString(10, endereco.getCidade().getEstado().getNome());
			pst.setString(11, endereco.getCidade().getEstado().getPais().getNome());
			pst.setInt(12, Integer.parseInt(endereco.getId_cliente()));
			pst.executeUpdate();
			connection.commit();
		}catch(SQLException ex) {
			connection.rollback();
			ex.printStackTrace();
		}finally {
			connection.close();
		}
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		openConnection();
		Endereco endereco = (Endereco) entidade;
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE public.endereco\r\n" + 
				"   logradouro=?, bairro=?, cep=?, numero=?, complemento=?, \r\n" + 
				"       nome=?, tipo_residencia=?, tipo_logradouro=?, cidade=?, estado=?, \r\n" + 
				"       pais=?, id_cliente=?\r\n" + 
				" WHERE id = ?;");
		try {
			connection.setAutoCommit(false);
			pst = connection.prepareStatement(sql.toString());
			pst.setString(1, endereco.getLogradouro());
			pst.setString(2, endereco.getBairro());
			pst.setString(3, endereco.getCep());
			pst.setString(4, endereco.getNumero());
			pst.setString(5, endereco.getComplemento());
			pst.setString(6, endereco.getNome());
			pst.setString(7, endereco.getTipoResidencia());
			pst.setString(8, endereco.getTipoLogradouro());
			pst.setString(9, endereco.getCidade().getNome());
			pst.setString(10, endereco.getCidade().getEstado().getNome());
			pst.setString(11, endereco.getCidade().getEstado().getPais().getNome());
			pst.setString(12, endereco.getId_cliente());
			pst.setInt(13, endereco.getId());
			pst.executeQuery();
			connection.commit();
		}catch(SQLException ex) {
			ex.printStackTrace();
			connection.rollback();
		}finally {
			connection.close();
		}
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		openConnection();
		StringBuilder sql = new StringBuilder();
		ValidateUtils vUtil = new ValidateUtils();
		List<EntidadeDominio> enderecos = new ArrayList<>();
		sql.append("SELECT id, logradouro, bairro, cep, numero, complemento, nome, tipo_residencia, \r\n" + 
				"       tipo_logradouro, cidade, estado, pais, id_cliente\r\n" + 
				"  FROM public.endereco a;");
		if(entidade != null) {
			Endereco endereco = (Endereco) entidade;
			sql.append(" WHERE 1=1");
			if(!vUtil.isNullOrEmpty(endereco.getId()))
				sql.append(" and a.id = " + endereco.getId());
			if(!vUtil.isNullOrEmpty(endereco.getLogradouro()))
				sql.append(" and a.logradouro = '" + endereco.getLogradouro() + "'");
			if(!vUtil.isNullOrEmpty(endereco.getBairro()))
				sql.append(" and a.bairro = '" + endereco.getBairro() + "'");
			if(!vUtil.isNullOrEmpty(endereco.getCep()))
				sql.append(" and a.cep = '" + endereco.getCep()  + "'");
			if(!vUtil.isNullOrEmpty(endereco.getNumero()))
				sql.append(" and a.numero = '" + endereco.getNumero() + "'");
			if(!vUtil.isNullOrEmpty(endereco.getComplemento()))
				sql.append(" and a.complemento = '" + endereco.getComplemento() + "'");
			if(!vUtil.isNullOrEmpty(endereco.getNome()))
				sql.append(" and a.nome = '" + endereco.getNome() + "'");
			if(!vUtil.isNullOrEmpty(endereco.getTipoResidencia()))
				sql.append(" and a.tipo_residencia = '" + endereco.getTipoResidencia()  + "'");
			if(!vUtil.isNullOrEmpty(endereco.getTipoLogradouro()))
				sql.append(" and a.tipo_logradouro = '" + endereco.getTipoLogradouro()  + "'");
			if(!vUtil.isNullOrEmpty(endereco.getCidade()) && !vUtil.isNullOrEmpty(endereco.getCidade().getNome()))
				sql.append(" and a.cidade = '" + endereco.getCidade().getNome()  + "'");
			if(!vUtil.isNullOrEmpty(endereco.getCidade()) && !vUtil.isNullOrEmpty(endereco.getCidade().getEstado())
					&& !vUtil.isNullOrEmpty(endereco.getCidade().getEstado().getNome()))
				sql.append(" and a.estado = '" + endereco.getCidade().getEstado().getNome() + "'");
			if(!vUtil.isNullOrEmpty(endereco.getCidade()) && !vUtil.isNullOrEmpty(endereco.getCidade().getEstado())
					&& !vUtil.isNullOrEmpty(endereco.getCidade().getEstado().getPais())
					&& !vUtil.isNullOrEmpty(endereco.getCidade().getEstado().getPais().getNome()))
				sql.append(" and a.pais = '" + endereco.getCidade().getEstado().getPais().getNome() + "'");
		}
		try {
			pst = connection.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Endereco e = new Endereco();
				e.setId(rs.getInt("id"));
				e.setLogradouro(rs.getString("logradouro"));
				e.setBairro(rs.getString("bairro"));
				e.setCep(rs.getString("cep"));
				e.setNumero(rs.getString("numero"));
				e.setComplemento(rs.getString("complemento"));
				e.setNome(rs.getString("nome"));
				e.setTipoResidencia(rs.getString("tipo_residencia"));
				e.setTipoLogradouro(rs.getString("tipo_logradouro"));
				Cidade cidade = new Cidade();
				cidade.setNome(rs.getString("cidade"));
				Estado estado = new Estado();
				estado.setNome(rs.getString("estado"));
				Pais pais = new Pais();
				pais.setNome(rs.getString("pais"));
				estado.setPais(pais);
				cidade.setEstado(estado);
				e.setCidade(cidade);
				enderecos.add(e);
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			connection.close();
		}
		return null;
	}

}
