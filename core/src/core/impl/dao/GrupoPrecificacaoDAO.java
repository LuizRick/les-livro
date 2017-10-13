package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import core.util.Conexao;
import dominio.EntidadeDominio;
import entities.produto.GrupoPrecificacao;

public class GrupoPrecificacaoDAO extends  AbstractJdbcDAO {
	
	public GrupoPrecificacaoDAO() {
		// TODO Auto-generated constructor stub
		super("grupo_precificacao", "id");
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		GrupoPrecificacao g = (GrupoPrecificacao) entidade;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT id, nome, margem FROM grupo_precificacao");
		if(g != null){
			sb.append(" where 1=1");
			if(g.getId() > 0){
				sb.append(" and id = " + g.getId());
			}
			if(!g.getDescricao().equals("")){
				sb.append(" and nome ilike '" + g.getDescricao() + "%'");
			}
		}
		try{
			openConnection();
			pst = connection.prepareStatement(sb.toString());
			ResultSet rs = pst.executeQuery();
			List<EntidadeDominio> grupos = new ArrayList<>();
			while(rs.next()){
				GrupoPrecificacao grupo = new GrupoPrecificacao();
				grupo.setId(rs.getInt("id"));
				grupo.setDescricao(rs.getString("nome"));
				grupo.setMargem(rs.getDouble("margem"));
				grupos.add(grupo);
			}
			return grupos;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
}
