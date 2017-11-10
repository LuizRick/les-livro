package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dominio.EntidadeDominio;
import entities.produto.Livro;
import entities.venda.Venda;

public class ConfiguracaoVendaDAO extends AbstractJdbcDAO {
	
	public ConfiguracaoVendaDAO() {
		// TODO Auto-generated constructor stub
		super("livro","id");
	}
	
	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		Venda venda = (Venda) entidade;
		openConnection();
		try {
			connection.setAutoCommit(false);
			openConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE livro SET valor= ? WHERE id = ?");
			pst = connection.prepareStatement(sql.toString());
			pst.setDouble(1, venda.getValor());
			pst.setInt(2,venda.getId());
			pst.executeUpdate();
			connection.commit();
		}catch(SQLException ex) {
			connection.rollback();
		}
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		Venda venda  = (Venda) entidade;
		List<EntidadeDominio> lst = new ArrayList<>();
		openConnection();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT liv.id,lv_ina.id id_inativo,liv.valor,lv_ina.categoria, lv_ina.motivo, lv_ina.status FROM livro liv");
			sql.append(" LEFT JOIN livros_inativos lv_ina on lv_ina.livro = liv.id");
			if(venda != null) {
				sql.append(" WHERE 1=1 ");
				if(venda.getId() > 0) {
					sql.append(" and liv.id = " + venda.getId());
				}
			}
			pst = connection.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Venda v = new Venda();
				v.setId(rs.getInt("id"));
				v.setValor(rs.getDouble("valor"));
				v.getStatusVenda().setStatus(rs.getBoolean("status"));
				v.getStatusVenda().setCategoria(rs.getShort("categoria"));
				v.getStatusVenda().setMotivo(rs.getString("motivo"));
				v.getStatusVenda().setId(rs.getInt("id_inativo"));
				lst.add(v);
			}
			return lst;
		}catch(SQLException ex) {
			ex.printStackTrace();
		}finally {
			connection.close();
		}
		return null;
	}

}
