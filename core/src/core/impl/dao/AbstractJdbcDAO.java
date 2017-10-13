package core.impl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import core.dfs.IDAO;
import core.util.Conexao;
import dominio.EntidadeDominio;

public abstract class AbstractJdbcDAO implements IDAO {
	protected Connection connection;
	protected String table;
	protected String idTable;
	protected boolean ctrlTransaction = true;

	public AbstractJdbcDAO(Connection connection, String table, String idTable) {
		this.table = table;
		this.idTable = idTable;
		this.connection = connection;
	}

	protected AbstractJdbcDAO(String table, String idTable) {
		this.table = table;
		this.idTable = idTable;
	}

	@Override
	public void excluir(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		openConnection();
		PreparedStatement pst = null;
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM");
		sb.append(table);
		sb.append(" where ");
		sb.append(idTable);
		sb.append("=");
		sb.append("?");
		try {
			connection.setAutoCommit(false);
			pst = connection.prepareStatement(sb.toString());
			pst.setInt(1, entidade.getId());
			pst.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			// TODO: handle exception
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				if (ctrlTransaction)
					connection.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void openConnection() {
		try {

			if (connection == null || connection.isClosed())
				connection = Conexao.getConnection();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
