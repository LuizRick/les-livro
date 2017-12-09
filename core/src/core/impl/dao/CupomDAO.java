package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.util.ValidateUtils;
import dominio.EntidadeDominio;
import entities.cadastros.Cliente;
import entities.cadastros.CupomCompra;
import entities.cadastros.TipoCupom;

public class CupomDAO extends AbstractJdbcDAO {
	
	public CupomDAO() {
		// TODO Auto-generated constructor stub
		super("cupom_compra","id");
	}
	
	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		openConnection();
		try {
			connection.setAutoCommit(false);
			CupomCompra cupom = (CupomCompra) entidade;
			pst = connection.prepareStatement("INSERT INTO public.cupons_compra(\r\n" + 
					"	icodigo_cupom, valor, id_venda_vendas, tipo_cupom)\r\n" + 
					"	VALUES (?, ?, ?, ?);");
			pst.setInt(1, cupom.getId());
			pst.setDouble(2, cupom.getValor());
			pst.setInt(3, cupom.getIdVenda());
			pst.setInt(4, cupom.getTipo().valorTipoCupom);
			pst.executeUpdate();
			connection.commit();
		}catch(SQLException ex) {
			ex.printStackTrace();
			connection.rollback();
		}
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		openConnection();
		CupomCompra cupom = (CupomCompra) entidade;
		try {
			pst = connection.prepareStatement("UPDATE public.cupom_cliente\r\n" + 
					"	valido = ? " + 
					"	WHERE id = ?");
			pst.setBoolean(1, cupom.getValido());
			pst.setInt(2, cupom.getId());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		ValidateUtils u = new ValidateUtils();
		List<EntidadeDominio> lst = new ArrayList<>();
		openConnection();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT a.id idcupom, a.codigo_cupom, a.valor, a.id_venda_vendas, a.tipo_cupom,a.created\r\n" + 
					", b.id idcupomcli, b.id_cliente, b.id_cupom, b.valido " + 
					"	FROM public.cupons_compra a");
			sql.append(" LEFT JOIN cupom_cliente b ON b.id_cupom = a.id");
			if(entidade != null) {
				sql.append(" WHERE 1=1 ");
				CupomCompra cupom = (CupomCompra) entidade;
				if(!u.isNullOrEmpty(cupom.getId()))
					sql.append(" AND a.id = " + cupom.getId());
				if(!u.isNullOrEmpty(cupom.getCliente()) && !u.isNullOrEmpty(cupom.getCliente().getId()))
					sql.append(" AND b.id_cliente = " + cupom.getCliente().getId());
				if(!u.isNullOrEmpty(cupom.getCodigoCupom()))
					sql.append(" AND a.codigo_cupom = '" + cupom.getCodigoCupom() + "'");
			}
			pst = connection.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				CupomCompra c = new CupomCompra();
				c.setId(rs.getInt("idcupom"));
				c.setCodigoCupom(rs.getString("codigo_cupom"));
				c.setValor(rs.getDouble("valor"));
				c.setIdVenda(rs.getInt("id_venda_vendas"));
				c.setTipo(TipoCupom.getTipoCupom(rs.getShort("tipo_cupom")));
				c.setValido(rs.getBoolean("valido"));
				c.setIdCupom(rs.getInt("idcupomcli"));
				c.setDtCadastro(rs.getDate("created"));
				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("id_cliente"));
				c.setCliente(cliente);
				lst.add(c);
			}
			return lst;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
