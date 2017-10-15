package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dominio.EntidadeDominio;
import entities.produto.StatusVenda;

public class StatusVendaDAO extends AbstractJdbcDAO {

	public StatusVendaDAO() {
		// TODO Auto-generated constructor stub
		super("livros_inativos", "id");
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		openConnection();
		StatusVenda stV = (StatusVenda) entidade;
		try {
			connection.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO livros_inativos(justificativa, categoria, id_livro,status) VALUES (?, ?, ?,?)");
			pst = connection.prepareStatement(sql.toString());
			pst.setString(1, stV.getMotivo());
			pst.setShort(2, stV.getCategoria());
			pst.setInt(3, stV.getIdLivro());
			pst.setBoolean(4, stV.getStatus());
			pst.executeUpdate();
			connection.commit();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		openConnection();
		StatusVenda stV = (StatusVenda) entidade;
		try {
			connection.setAutoCommit(false);
			pst = connection.prepareStatement(
					"UPDATE livros_inativos SET justificativa=?, categoria=?, id_livro=?, status = ? WHERE id_livro = ?;");
			pst.setString(1, stV.getMotivo());
			pst.setShort(2, stV.getCategoria());
			pst.setInt(3, stV.getIdLivro());
			pst.setBoolean(4, stV.getStatus());
			pst.setInt(5, stV.getIdLivro());
			
			pst.executeUpdate();
			connection.commit();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		StatusVenda stV = (StatusVenda) entidade;
		try {
			openConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT id, justificativa, categoria, id_livro,status FROM livros_inativos WHERE 1=1");
			if(stV.getIdLivro() != null && stV.getIdLivro() > 0)
				sql.append(" AND id_livro = " + stV.getIdLivro());
			pst = connection.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			List<EntidadeDominio> lst = new ArrayList<>();
			while(rs.next()){
				StatusVenda s = new StatusVenda();
				s.setId(rs.getInt("id"));
				s.setCategoria(rs.getShort("categoria"));
				s.setMotivo(rs.getString("justificativa"));
				s.setIdLivro(rs.getInt("id_livro"));
				s.setStatus(rs.getBoolean("status"));
				lst.add(s);
			}
			return lst;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
