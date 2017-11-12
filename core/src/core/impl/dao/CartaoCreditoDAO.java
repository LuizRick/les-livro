/**
 * 
 */
package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see core.dfs.IDAO#consultar(dominio.EntidadeDominio)
	 */
	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
