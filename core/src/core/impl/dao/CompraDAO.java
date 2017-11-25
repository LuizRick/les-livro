package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.util.ValidateUtils;
import dominio.EntidadeDominio;
import entities.cadastros.Cartao;
import entities.cadastros.CartaoCredito;
import entities.cadastros.CupomCompra;
import entities.cadastros.TipoCupom;
import entities.produto.Dimensao;
import entities.produto.GrupoPrecificacao;
import entities.produto.Livro;
import entities.venda.CarrinhoCompra;
import entities.venda.Compra;
import entities.venda.Frete;
import entities.venda.Item;
import entities.venda.StatusCompra;

public class CompraDAO extends AbstractJdbcDAO {
	
	public CompraDAO() {
		// TODO Auto-generated constructor stub
		super("vendas", "id");
	}
	
	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		Compra compras = (Compra) entidade;
		PreparedStatement pst = null;
		openConnection();
		try {
			connection.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO public.vendas(\r\n" + 
					"	status, endereco, total,id_cliente)\r\n" + 
					"	VALUES (?, ?, ?,?);");
			compras.getStatusCompra();
			pst = connection.prepareStatement(sql.toString(), java.sql.Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, StatusCompra.Processamento.statusCompra);
			pst.setString(2,compras.getFrete().getEndereco().getEnderecoCompleto(null));
			pst.setDouble(3, compras.getTotal());
			pst.setInt(4, compras.getCliente().getId());
			pst.executeUpdate();
			ResultSet rsCar = pst.getGeneratedKeys();
			while(rsCar.next())
				compras.setId(rsCar.getInt(1));
			for(int i = 0;i < compras.getProdutos().getItens().size(); i ++) {
				Item item = compras.getProdutos().getItens().get(i);
				pst = connection.prepareStatement("INSERT INTO public.vendas_produtos(\r\n" + 
						"	qtd, id_venda_vendas, id_livro)\r\n" + 
						"	VALUES (?, ?, ?);");
				pst.setInt(1, item.getQuantidade());
				pst.setInt(2,compras.getId());
				pst.setInt(3, item.getProduto().getId());
				pst.executeUpdate();
			}
			
			for(int i = 0;i < compras.getFormasPagamento().size(); i++) {
				CartaoCredito cartao = compras.getFormasPagamento().get(i);
				pst = connection.prepareStatement("INSERT INTO public.vendas_pagamento(\r\n" + 
						"	valor,id_venda_vendas, numero_cartao)\r\n" + 
						"	VALUES (?, ?, ?);");
				pst.setDouble(1, cartao.getValor());
				pst.setInt(2, compras.getId());
				pst.setString(3, cartao.getNumero().replaceAll("-", ""));
				pst.executeUpdate();
			}
			
			for(int i = 0; i < compras.getCuponCompra().size(); i++) {
				CupomCompra cupom = compras.getCuponCompra().get(i);
				pst = connection.prepareStatement("INSERT INTO public.cupons_compra(\r\n" + 
						"	codigo_cupom, valor, id_venda_vendas,tipo_cupom)\r\n" + 
						"	VALUES (?, ?, ?,?);");
				pst.setString(1, cupom.getCodigoCupom());
				pst.setDouble(2, cupom.getValor());
				pst.setInt(3, compras.getId());
				pst.setInt(4, cupom.getTipo().valorTipoCupom);
				pst.executeUpdate();
			}
			connection.commit();
		}catch(SQLException ex) {
			ex.printStackTrace();
		}finally {
			connection.close();
		}
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		Compra compra = (Compra) entidade;
		openConnection();
		PreparedStatement pst = null;
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE public.vendas\r\n" + 
				"	SET status= ?" + 
				"	WHERE id_venda = ?;");
		try {
			pst = connection.prepareStatement(sql.toString());
			pst.setInt(1, compra.getStatusCompra().statusCompra);
			pst.setInt(2, compra.getId());
			pst.executeQuery();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connection.close();
		}
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		List<EntidadeDominio> compras = new ArrayList<>();
		PreparedStatement pst = null;
		openConnection();
		ValidateUtils util = new ValidateUtils();
		StringBuilder sql = new StringBuilder(),   //conter dados do cliente e compra/venda correspondente
				sqlProdutos = new StringBuilder(), //conter sql dos produtos
				sqlFormPag = new StringBuilder(); //formas de pagamento
		sql.append("SELECT id_venda, status, endereco, total,id_cliente\r\n" + 
				"	FROM public.vendas a");
		sql.append("INNER JOIN cliente b ON a.id_cliente = b.id");
		if(entidade != null) {
			Compra compra = (Compra) entidade;
			sql.append(" WHERE 1=1");
			if(!util.isNullOrEmpty(compra.getId())) {
				sql.append(" AND a.id_venda = " + compra.getId());
			}
			
			if(!util.isNullOrEmpty(compra.getStatusCompra().statusCompra)) {
				sql.append(" AND a.status = " + compra.getStatusCompra().statusCompra);
			}
			
			if(!util.isNullOrEmpty(compra.getTotal())) {
				sql.append(" AND a.total = " + compra.getTotal());
			}
			if(!util.isNullOrEmpty(compra.getCliente()) && !util.isNullOrEmpty(compra.getCliente().getId())){
				sql.append(" AND a.id_cliente = " + compra.getCliente().getId());
			}
		}
		try {
			pst = connection.prepareStatement(sql.toString());
			ResultSet rsCarCompra = pst.executeQuery();
			while(rsCarCompra.next()) {
				Compra c = new Compra();
				c.setId(rsCarCompra.getInt("id_venda"));
				c.setTotal(rsCarCompra.getDouble("total"));
				c.setStatusCompra(StatusCompra.values()[rsCarCompra.getInt("status")]);
				Frete f = new Frete();
				f.setEnderecoEntrega(rsCarCompra.getString("endereco"));
				c.setFrete(f);
				//seleciona os produtos
				sqlProdutos.append("SELECT a.id as idvenda, a.qtd, a.id_venda_vendas, a.id_livro,"
						+ "b.id, .b.autor, b.ano, b.titulo, b.editora, b.edicao, b.isbn, b.npaginas,"
						+ "b.sinopse, b.status, b.altura, b.largura, b.peso, b.profundidade, b.id_grupo_precificacao, "
						+ "b.usuario, b.valor, b.estoque\r\n" +
						"	FROM public.vendas_produtos a;");
				sqlProdutos.append("INNER JOIN livro b ON a.id_livro = b.id");
				sqlProdutos.append(" WHERE a.id_venda_vendas = ?");
				pst = connection.prepareStatement(sqlProdutos.toString());
				pst.setInt(1, c.getId());
				ResultSet rsCar = pst.executeQuery();
				CarrinhoCompra cart = new CarrinhoCompra();
				while(rsCar.next()) {
					Item item = new Item();
					item.setQuantidade(rsCar.getInt("qtd"));
					item.setId(rsCar.getInt("idvenda"));
					Livro l = new Livro();
					l.setId(rsCar.getInt("id"));
					l.setTitulo(rsCar.getString("titulo"));
					l.setEdicao(rsCar.getString("edicao"));
					l.setAutor(rsCar.getString("autor"));
					l.setIsbn(rsCar.getString("isbn"));
					l.setAno(rsCar.getString("ano"));
					l.setNpaginas(rsCar.getString("npaginas"));
					l.setStatus(rsCar.getInt("status"));
					l.setDimensao(new Dimensao());
					l.getDimensao().setAltura(rsCar.getDouble("altura"));
					l.setEditora(rsCar.getString("editora"));
					l.getDimensao().setLargura(rsCar.getDouble("largura"));
					l.getDimensao().setPeso(rsCar.getDouble("peso"));
					l.setGrupoPrecificacao(new GrupoPrecificacao());
					l.getGrupoPrecificacao().setId(rsCar.getInt("id_grupo_precificacao"));
					l.getDimensao().setProfundidade(rsCar.getDouble("profundidade"));
					l.setSinopse(rsCar.getString("sinopse"));
					l.setValor(rsCar.getDouble("valor"));
					l.setEstoque(rsCar.getInt("estoque"));
					item.setProduto(l);
					cart.getItens().add(item);
				}
				c.setProdutos(cart);
				sqlFormPag.append("SELECT id, valor, id_venda_vendas, numero_cartao\r\n" + 
						",b.titular,b. numero, b.bandeira, b.codigo_seguranca, b.validade FROM public.vendas_pagamento a \n\r");
				sqlFormPag.append(" INNER JOIN vendas_pagamento b on b.numero = a.numero_cartao");
				sqlFormPag.append(" WHERE a.id_venda_vendas = " + c.getId());
				pst = connection.prepareStatement(sqlFormPag.toString());
				ResultSet rsCartoes =  pst.executeQuery();
				List<CartaoCredito> cartoes = new ArrayList<>(); 
				while(rsCartoes.next()) {
					CartaoCredito cc = new CartaoCredito();
					cc.setTitular(rsCartoes.getString("titular"));
					cc.setNumero(rsCartoes.getString("numero"));
					cc.setBandeira(rsCartoes.getString("bandeira"));
					cc.setCodigo(rsCartoes.getString("codigo_seguranca"));
					cc.setValidade(rsCartoes.getDate("validade"));
					cartoes.add(cc);
				}
				c.setFormasPagamento(cartoes);
				pst = connection.prepareStatement("SELECT id, codigo_cupom, valor, id_venda_vendas,tipo_cupom\r\n" + 
						"	FROM public.cupons_compra WHERE id_venda_vendas = " + c.getId());
				ResultSet rsCups = pst.executeQuery();
				List<CupomCompra> cups = new ArrayList<CupomCompra>();
				while(rsCups.next()) {
					CupomCompra cpc = new CupomCompra();
					cpc.setCodigoCupom(rsCups.getString("codigo_cupom"));
					cpc.setValor(rsCups.getDouble("valor"));
					cpc.setTipo(TipoCupom.values()[rsCups.getInt("tipo_cupom")]);
					cups.add(cpc);
				}
				c.setCuponCompra(cups);
				compras.add(c);
			}
			return compras;
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			connection.close();
		}
		return null;
	}
}
