/**
 * 
 */
package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.util.ValidateUtils;
import dominio.EntidadeDominio;
import entities.cadastros.CartaoCredito;

/**
 * @author luizl
 *
 */
public class CartaoCreditoDAO extends AbstractJdbcDAO {

	public CartaoCreditoDAO() {
		// TODO Auto-generated constructor stub
		super("cartao_credito","id");
	}
	
	/* (non-Javadoc)
	 * @see core.dfs.IDAO#salvar(dominio.EntidadeDominio)
	 */
	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		openConnection();
		CartaoCredito cartao = (CartaoCredito) entidade;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO public.cartao_credito(\r\n" + 
					"            titular, numero, bandeira, codigo_seguranca, validade, id_cliente)\r\n" + 
					"    VALUES (?, ?, ?, ?, ?, ?);\r\n" + 
					"");
			pst = connection.prepareStatement(sql.toString());
			pst.setString(1, cartao.getTitular());
			pst.setString(2, cartao.getNumero());
			pst.setString(3, cartao.getBandeira());
			pst.setString(4, cartao.getCodigo());
			pst.setDate(5,new java.sql.Date(cartao.getValidade().getTime()));
			pst.setInt(6, cartao.getId_cliente());
			pst.executeUpdate();
		}catch(SQLException ex) {
			ex.printStackTrace();
		}finally {
			connection.close();
		}
	}

	/* (non-Javadoc)
	 * @see core.dfs.IDAO#alterar(dominio.EntidadeDominio)
	 */
	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		openConnection();
		CartaoCredito cartao = (CartaoCredito) entidade;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE public.cartao_credito\r\n" + 
					"   SET titular=?, numero=?, bandeira=?, codigo_seguranca=?, validade=? , id_cliente = ? " +
					" WHERE id = ? ");
			pst = connection.prepareStatement(sql.toString());
			pst = connection.prepareStatement(sql.toString());
			pst.setString(1, cartao.getTitular());
			pst.setString(2, cartao.getNumero());
			pst.setString(3, cartao.getBandeira());
			pst.setString(4, cartao.getCodigo());
			pst.setDate(5,new java.sql.Date(cartao.getValidade().getTime()));
			pst.setInt(6, cartao.getId_cliente());
			pst.setInt(7, cartao.getId());
			pst.executeUpdate();
		}catch(SQLException ex) {
			ex.printStackTrace();
		}finally {
			connection.close();
		}
	}

	/* (non-Javadoc)
	 * @see core.dfs.IDAO#consultar(dominio.EntidadeDominio)
	 */
	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		openConnection();
		List<EntidadeDominio> cartoes = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, titular, numero, bandeira, codigo_seguranca, validade, id_cliente\r\n" + 
				"  FROM public.cartao_credito;\r\n");
		if(entidade != null) {
			CartaoCredito cartao = (CartaoCredito) entidade;
			sql.append(" WHERE 1=1 ");
			ValidateUtils vUtils = new ValidateUtils();
			if(!vUtils.isNullOrEmpty(cartao.getId()))
				sql.append(" and id = " + cartao.getId());
			if(!vUtils.isNullOrEmpty(cartao.getTitular()))
				sql.append(" and titulo = '" + cartao.getTitular() + "'");
			if(!vUtils.isNullOrEmpty(cartao.getNumero()))
				sql.append(" and numero = '" + cartao.getNumero() + "'");
			if(!vUtils.isNullOrEmpty(cartao.getBandeira()))
				sql.append(" and bandeira ='" + cartao.getBandeira() + "'");
			if(!vUtils.isNullOrEmpty(cartao.getCodigo()))
				sql.append(" and codigo_seguranca = '" + cartao.getCodigo() + "'");
			if(cartao.getValidade() != null)
				sql.append(" and validade = '" + cartao.getValidade().toString() + "'");
			if(cartao.getId_cliente() != null && cartao.getId_cliente() > 0)
				sql.append(" and id_cliente = " + cartao.getId_cliente());
		}
		try {
			pst = connection.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				CartaoCredito c = new CartaoCredito();
				c.setId(rs.getInt("id"));
				c.setTitular(rs.getString("titular"));
				c.setNumero(rs.getString("numero"));
				c.setBandeira(rs.getString("bandeira"));
				c.setCodigo(rs.getString("codigo_seguranca"));
				c.setValidade(rs.getDate("validade"));
				c.setId_cliente(rs.getInt("id_cliente"));
				cartoes.add(c);
			}
			return cartoes;
		}catch(SQLException ex) {
			ex.printStackTrace();
		}finally {
			connection.close();
		}
		return null;
	}

}
