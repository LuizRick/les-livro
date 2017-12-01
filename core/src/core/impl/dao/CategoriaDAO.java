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
		Categoria cat = (Categoria) entidade;
		try {
			pst = connection.prepareStatement("public.categoria(\r\n" + 
					"	nome)\r\n" + 
					"	VALUES (?)");
			pst.setString(1, cat.getNome());
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
		PreparedStatement pst = null;
		Categoria cat = (Categoria) entidade;
		try {
			 pst = connection.prepareStatement("UPDATE public.categoria\r\n" + 
			 		"	SET nome=?" + 
			 		"	WHERE id = ?");
			 pst.setString(1, cat.getNome());
			 pst.setInt(2, cat.getId());
			 pst.executeUpdate();
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
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
