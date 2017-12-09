package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.util.ValidateUtils;
import dominio.EntidadeDominio;
import entities.usuarios.Notificacao;

public class NotificacoesDAO extends AbstractJdbcDAO {
	
	public NotificacoesDAO() {
		// TODO Auto-generated constructor stub
		super("notificacoes", "id");
	}
	
	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		PreparedStatement pst = null;
		Notificacao not = (Notificacao) entidade;
		openConnection();
		try {
			pst = connection.prepareStatement("UPDATE notificacoes SET flg_lido = ? WHERE id = ?");
			pst.setBoolean(1, not.getLido());
			pst.setInt(2, not.getId());
			pst.executeUpdate();
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		ValidateUtils utils = new ValidateUtils();
		List<EntidadeDominio> lst = new ArrayList<>();
		openConnection();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT id, mensagem, 'pageSetStatus' status, flg_lido, id_cliente\r\n" + 
					" FROM public.notificacoes");
			if(entidade != null) {
				sql.append(" WHERE 1=1 ");
				Notificacao not = (Notificacao) entidade;
				if(!utils.isNullOrEmpty(not.getLido()))
					sql.append(" AND flg_lido = " + not.getLido());

				if(!utils.isNullOrEmpty(not.getIdCliente()))
					sql.append(" AND id_cliente = " + not.getIdCliente());
				
				if(!utils.isNullOrEmpty(not.getId()))
					sql.append(" AND id = " + not.getId());
			}
			pst = connection.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Notificacao n = new Notificacao();
				n.setId(rs.getInt("id"));
				n.setMensagem(rs.getString("mensagem"));
				n.setIdCliente(rs.getInt("id_cliente"));
				n.setPage(rs.getString("status"));
				n.setLido(rs.getBoolean("flg_lido"));
				lst.add(n);
			}
			return lst;
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
