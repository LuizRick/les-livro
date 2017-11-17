package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dominio.EntidadeDominio;
import entities.cadastros.CartaoCredito;
import entities.produto.Categoria;

public class CategoriaDAO extends AbstractJdbcDAO {

	public CategoriaDAO() {
		// TODO Auto-generated constructor stub
		super("categoria","id");
	}
	
	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		CartaoCredito card = (CartaoCredito) entidade;
		try {
			pst = connection.prepareStatement("INSERT INTO cartao_credito("
					+ "titular, numero, bandeira, codigo_seguranca, validade, id_cliente)"
					+ "VALUES (?, ?, ?, ?, ?, ?);");
			pst.setString(1, card.getTitular());
			pst.setString(2, card.getNumero());
			pst.setString(3, card.getBandeira());
			pst.setString(4, card.getCodigo());
			pst.setDate(5, new java.sql.Date(card.getValidade().getTime()));
			pst.setInt(6, card.getId_cliente());
			pst.executeUpdate();
		}catch(SQLException ex) {
			ex.printStackTrace();
		}finally {
			connection.close();
		}
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT id,nome FROM categoria \n");
		if(entidade != null){
			Categoria categoria = (Categoria) entidade;
			sb.append("WHERE 1=1\n");
			if(categoria.getId() != null){
				sb.append(" and id = " + categoria.getId() + "\n");
			}
			if(categoria.getNome() != null && categoria.getNome().length() > 0){
				sb.append(" and nome ilike '%" + categoria.getNome() + "%'\n");
			}
		}
		try{
			openConnection();
			pst = connection.prepareStatement(sb.toString());
			ResultSet rs = pst.executeQuery();
			List<EntidadeDominio> categorias = new ArrayList<>();
			while(rs.next()){
				Categoria c  = new Categoria();
				c.setId(rs.getInt("id"));
				c.setNome(rs.getString("nome"));
				categorias.add(c);
			}
			pst.close();
			return categorias;
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally {
			connection.close();
		}
		return null;
	}

}
