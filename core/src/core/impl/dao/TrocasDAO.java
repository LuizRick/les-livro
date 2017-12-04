package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dominio.EntidadeDominio;
import entities.venda.Item;
import entities.venda.StatusCompra;
import entities.venda.Trocas;

public class TrocasDAO extends AbstractJdbcDAO {
	
	public TrocasDAO() {
		// TODO Auto-generated constructor stub
		super("pedidos_troca", "id");
	}
	
	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		openConnection();
		try {
			connection.setAutoCommit(false);
			Trocas troca = (Trocas) entidade;
			pst = connection.prepareStatement("INSERT INTO public.pedidos_troca(\r\n" + 
					"	status)\r\n" + 
					"	VALUES (?);", PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setInt(1, troca.getStatus().statusCompra);
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			while(rs.next())
				troca.setId(rs.getInt(1));
			for(int i = 0;i < troca.getCompra().getProdutos().getItens().size();i++) {
				Item item = troca.getCompra().getProdutos().getItens().get(i);
				pst = connection.prepareStatement("INSERT INTO public.pedido_troca_itens(" + 
						"	id_pedido_troca, id_item_pedido)\r\n" + 
						"	VALUES (?, ?);");
				pst.setInt(1,troca.getId());
				pst.setInt(2,item.getId());
				pst.executeUpdate();
				connection.commit();
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			connection.rollback();
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
		try {
			Trocas troca = (Trocas) entidade;
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT a.id, a.status\r\n" + 
					"	FROM public.pedidos_troca a");
			if(entidade != null) {
				sql.append("WHERE 1=1");
				if(troca.getId() != null && troca.getId() > 0)
					sql.append(" AND a.id = " + troca.getId());
				if(troca.getStatus() != null)
					sql.append(" AND a.status = " + troca.getStatus().getStatus());
			}
			pst = connection.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				troca.setId(rs.getInt("id"));
				troca.setStatus(StatusCompra.status(rs.getInt("status")));
				pst = connection.prepareStatement("SELECT a.id, a.id_pedido_troca, a.id_item_pedido\r\n" +
						"b.id, b.id_pedido_troca, b.id_item_pedido, "+
						"c.id, c.autor, c.ano, c.titulo, c.editora, c.edicao, c.isbn, c.npaginas" + 
						"	FROM public.pedido_troca_itens a" + 
						"   INNER JOIN public.vendas_produtos b ON a.id_item_pedido = b.id" + 
						"	INNER JOIN public.livro c ON c.id = b.id_livro WHERE a.id_pedido_troca = ?");
				pst.setInt(1, troca.getId());
			}
		}catch(SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}

}
