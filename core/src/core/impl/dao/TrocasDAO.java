package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dominio.EntidadeDominio;
import entities.produto.Livro;
import entities.venda.CarrinhoCompra;
import entities.venda.Compra;
import entities.venda.Item;
import entities.venda.StatusCompra;
import entities.venda.TipoTroca;
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
		PreparedStatement pst = null;
		openConnection();
		try {
			pst = connection.prepareStatement("");
		}catch(SQLException ex) {
			ex.printStackTrace();
			connection.rollback();
		}
		
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		openConnection();
		try {
			Trocas troca = (Trocas) entidade;
			List<EntidadeDominio> entidades = new ArrayList<>();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT a.id, a.status, a.id_venda, a.flg_tipotroca\r\n" + 
					"	FROM public.pedidos_troca a ");
			if(entidade != null) {
				sql.append("WHERE 1=1");
				if(troca.getId() != null && troca.getId() > 0)
					sql.append(" AND a.id = " + troca.getId());
				if(troca.getStatus() != null)
					sql.append(" AND a.status = " + troca.getStatus().getStatus());
				if(troca.getTipo() != null)
					sql.append(" and a.flg_tipotroca = " + troca.getTipo().valor);
			}
			pst = connection.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Trocas t = new Trocas();
				Compra c = new Compra();
				CarrinhoCompra cart = new CarrinhoCompra();
				t.setId(rs.getInt("id"));
				t.setStatus(StatusCompra.status(rs.getInt("status")));
				t.setTipo(TipoTroca.getStatus(rs.getInt("flg_tipotroca")));
				c.setId(rs.getInt("id_venda"));
				pst = connection.prepareStatement("SELECT a.id iditem, a.id_pedido_troca, a.id_item_pedido,\r\n" +
						"b.id idvenda,  b.id_venda_vendas, b.id_livro, b.qtd,"+
						"c.id idlivro, c.autor, c.ano, c.titulo, c.editora, c.edicao, c.isbn, c.npaginas" + 
						"	FROM public.pedido_troca_itens a" + 
						"   INNER JOIN public.vendas_produtos b ON a.id_item_pedido = b.id" +  
						"	INNER JOIN public.livro c ON c.id = b.id_livro WHERE a.id_pedido_troca = ?");
				pst.setInt(1, t.getId());
				ResultSet rsp = pst.executeQuery();
				while(rsp.next()) {
					Item item = new Item();
					item.setId(rsp.getInt("iditem"));
					item.setQuantidade(rsp.getInt("qtd"));
					Livro livro  = new Livro();
					livro.setId(rsp.getInt("idlivro"));
					livro.setAutor(rsp.getString("autor"));
					livro.setAno(rsp.getString("ano"));
					livro.setTitulo(rsp.getString("titulo"));
					livro.setEditora(rsp.getString("editora"));
					livro.setEdicao(rsp.getString("edicao"));
					livro.setIsbn(rsp.getString("isbn"));
					item.setProduto(livro);
					cart.getItens().add(item);
				}
				c.setProdutos(cart);
				t.setCompra(c);
				entidades.add(t);
			}
			return entidades;
		}catch(SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}

}
