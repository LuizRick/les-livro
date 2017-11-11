package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import dominio.EntidadeDominio;
import entities.produto.Categoria;
import entities.produto.Dimensao;
import entities.produto.GrupoPrecificacao;
import entities.produto.Livro;

public class LivroDAO extends AbstractJdbcDAO {
	
	public LivroDAO() {
		super("livros", "id_livro");
	}
	
	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		openConnection();
		PreparedStatement pst = null;
		Livro livro = (Livro) entidade;
		try{
			connection.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO livro(autor, ano, titulo, editora, edicao, isbn, "
					+ "npaginas, sinopse, status, altura, largura, peso, profundidade, "
					+ "id_grupo_precificacao,usuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?);");
			pst = connection.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, livro.getAutor());
			pst.setString(2, livro.getAno());
			pst.setString(3, livro.getTitulo());
			pst.setString(4, livro.getEditora());
			pst.setString(5, livro.getEdicao());
			pst.setString(6, livro.getIsbn());
			pst.setString(7, livro.getNpaginas());
			pst.setString(8, livro.getSinopse());
			pst.setInt(9, livro.getStatus());
			pst.setDouble(10, livro.getDimensao().getAltura());
			pst.setDouble(11, livro.getDimensao().getLargura());
			pst.setDouble(12, livro.getDimensao().getPeso());
			pst.setDouble(13, livro.getDimensao().getProfundidade());
			pst.setInt(14, livro.getGrupoPrecificacao().getId());
			pst.setString(15, livro.getUsuario().getNomeusuario());
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			int id = 0;
			if(rs.next())
				id = rs.getInt(1);
			
			livro.setId(id);
			for(int i = 0; i < livro.getCategoria().size();i++){
				String strSql = String.format("INSERT INTO livro_categorias(id_livro, id_categoria) VALUES (%s,%s)",
						livro.getId(),livro.getCategoria().get(i));
				pst = connection.prepareStatement(strSql);
				pst.executeUpdate();
			}
			connection.commit();
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			try{
				pst.close();
			}catch(SQLException e1){
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		openConnection();
		PreparedStatement pst = null;
		Livro livro = (Livro) entidade;
		try {
			connection.setAutoCommit(false);
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE livro SET autor=?, ano=?, titulo=?, editora=?, edicao=?, isbn=?,npaginas=?, sinopse=?, status=?, altura=?,"
					+ " largura=?, peso=?, profundidade=?, id_grupo_precificacao=? WHERE id=?");
			pst = connection.prepareStatement(sb.toString());
			pst.setString(1, livro.getAutor());
			pst.setString(2, livro.getAno());
			pst.setString(3, livro.getTitulo());
			pst.setString(4, livro.getEditora());
			pst.setString(5, livro.getEdicao());
			pst.setString(6, livro.getIsbn());
			pst.setString(7, livro.getNpaginas());
			pst.setString(8, livro.getSinopse());
			pst.setInt(9, livro.getStatus());
			pst.setDouble(10, livro.getDimensao().getAltura());
			pst.setDouble(11, livro.getDimensao().getLargura());
			pst.setDouble(12, livro.getDimensao().getPeso());
			pst.setDouble(13, livro.getDimensao().getProfundidade());
			pst.setInt(14, livro.getGrupoPrecificacao().getId());
			pst.setInt(15, livro.getId());
			pst.executeUpdate();
			connection.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		Livro livro = (Livro) entidade;
		List<Categoria> categorias = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT  a.id, a.autor, a.ano, a.titulo, a.editora, a.edicao, a.isbn, a.npaginas, a.sinopse, a.status, a.altura,");
	    sql.append(" a.largura, a.peso, a.profundidade, a.id_grupo_precificacao,a.valor,a.estoque FROM livro a ");
		if(livro != null) {
			sql.append(" WHERE 1=1 ");
			if(livro.getId() != null && livro.getId() > 0){
				sql.append(" AND a.id = " + livro.getId());
			}
			
			if(livro.getTitulo() != null && livro.getTitulo().length() > 0){
				sql.append(" AND a.titulo ilike '%" + livro.getTitulo() + "%'");
			}
			if(livro.getAutor() != null && livro.getAutor().length() > 0){
				sql.append(" AND a.autor ilike '%" + livro.getTitulo() + "%'");
			}
			if(livro.getEdicao() != null && livro.getEdicao().length() > 0){
				sql.append(" AND a.edicao ilike '%" + livro.getEdicao() + "%'");
			}
			if(livro.getIsbn() != null && livro.getIsbn().length() > 0){
				sql.append(" AND a.isbn ilike '%" + livro.getIsbn() + "%'");
			}
			if(livro.getAno() != null && livro.getAno().length() > 0){
				sql.append(" and a.ano ilike '%" + livro.getAno() + "%'");
			}
			if(livro.getNpaginas() != null && livro.getNpaginas().length() > 0){
				sql.append(" and a.npaginas = " + livro.getNpaginas());
			}
			if(livro.getStatus() > 0){
				sql.append(" and a.status = " + livro.getStatus());
			}
			if(livro.getEditora() != null && livro.getEditora().length() > 0){
				sql.append(" and a.editora ilike '%" + livro.getEditora() + "%'");
			}
			if(livro.getGrupoPrecificacao() != null && livro.getGrupoPrecificacao().getId() != null){
				sql.append(" and a.id_grupo_precificacao = " + livro.getGrupoPrecificacao().getId());
			}
		}
		try{
			openConnection();
			pst = connection.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			List<EntidadeDominio> livros = new ArrayList<>();
			while(rs.next()){
				Livro l = new Livro();
				l.setId(rs.getInt("id"));
				l.setTitulo(rs.getString("titulo"));
				l.setEdicao(rs.getString("edicao"));
				l.setAutor(rs.getString("autor"));
				l.setIsbn(rs.getString("isbn"));
				l.setAno(rs.getString("ano"));
				l.setNpaginas(rs.getString("npaginas"));
				l.setStatus(rs.getInt("status"));
				l.setDimensao(new Dimensao());
				l.getDimensao().setAltura(rs.getDouble("altura"));
				l.setEditora(rs.getString("editora"));
				l.getDimensao().setLargura(rs.getDouble("largura"));
				l.getDimensao().setPeso(rs.getDouble("peso"));
				l.setGrupoPrecificacao(new GrupoPrecificacao());
				l.getGrupoPrecificacao().setId(rs.getInt("id_grupo_precificacao"));
				l.getDimensao().setProfundidade(rs.getDouble("profundidade"));
				l.setSinopse(rs.getString("sinopse"));
				l.setCategoria(categorias);
				l.setValor(rs.getDouble("valor"));
				l.setEstoque(rs.getInt("estoque"));
				pst = connection.prepareStatement("SELECT b.id,b.nome FROM livro_categorias a "
						+ "JOIN categoria b on (a.id_livro = ? and b.id = a.id_categoria)");
				pst.setInt(1, l.getId());
				ResultSet rsCat = pst.executeQuery();
				while(rsCat.next())
					l.getCategoria().add(new Categoria(rsCat.getString("nome")));
				livros.add(l);
			}
			return livros;
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
