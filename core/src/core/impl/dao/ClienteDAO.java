package core.impl.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sun.corba.se.spi.orbutil.fsm.State;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import core.util.ConvertDate;
import dominio.EntidadeDominio;
import entities.cadastros.Cliente;
import entities.cadastros.PessoaFisica;
import entities.cadastros.Telefone;
import entities.produto.Livro;

public class ClienteDAO extends AbstractJdbcDAO {

	public ClienteDAO() {
		// TODO Auto-generated constructor stub
		super("cliente","id");
	}
	
	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		openConnection();
		PreparedStatement pst = null;
		Cliente cliente = (Cliente) entidade;
		try{
			connection.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO cliente(nome, nascimento, genero, cpf, email, senha)"
					+ "VALUES (?, ?, ?, ?, ?, ?);");
			pst = connection.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, cliente.getPessoa().getNome());
			Date date = new Date(cliente.getPessoa().getNascimento().getTime());
			pst.setDate(2, date);
			pst.setString(3, cliente.getPessoa().getGenero() + "");
			pst.setString(4, ((PessoaFisica)cliente.getPessoa()).getCpf());
			pst.setString(5, cliente.getEmail());
			pst.setString(6, cliente.getSenha());
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			int id = 0;
			if(rs.next())
				id = rs.getInt(1);
			cliente.setId(id);
			for(int i = 0;i < cliente.getTelefone().size();i++){
				Telefone t = cliente.getTelefone().get(i);
				pst = connection.prepareStatement("INSERT INTO telefone(codigo_area, numero, tipo, id_cliente)"
						+ " VALUES (?, ?, ?, ?);");
				pst.setString(1, t.getArea());
				pst.setString(2, t.getNumero());
				pst.setInt(3, t.getTipo().valorTipo);
				pst.setInt(4, cliente.getId());
				pst.executeUpdate();
			}
			connection.commit();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
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
		
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		Cliente cliente = (Cliente) entidade;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT a.id, a.nome, a.nascimento, a.genero, a.cpf, a.email, a.senha,"
				+ "coalesce(b.numero,'') as numero,coalesce(b.codigo_area,'') as area FROM cliente a ");
		sb.append("LEFT JOIN telefone b on (b.id_cliente = a.id)");
		sb.append("WHERE 1=1 ");
		if(cliente.getId() != null){
			sb.append(" and a.id=" + cliente.getId());
		}
		if(cliente.getPessoa() != null && cliente.getPessoa().getNome() != null){
			sb.append(" and a.nome ilike '%" + cliente.getPessoa().getNome() + "%'");
		}
		if(cliente.getPessoa() != null && ((PessoaFisica)cliente.getPessoa()).getCpf() != null){
			sb.append(" and a.cpf ilike '%" + ((PessoaFisica)cliente.getPessoa()).getCpf() + "%'");
		}
		if(cliente.getEmail() != null && !cliente.getEmail().equals("")){
			sb.append(" and a.email ilike '%" + cliente.getEmail() + "%'");
		}
		if(cliente.getSenha() != null && !cliente.getSenha().equals("")){
			sb.append(" and a.senha = '%" + cliente.getSenha() + "%'");
		}
		if(cliente.getPessoa() != null && cliente.getPessoa().getNascimento() != null){
			sb.append(" and a.nascimento = '" + cliente.getPessoa().getNascimento() + "'");
		}
		if(Character.isAlphabetic(cliente.getPessoa().getGenero())){
			sb.append(" and a.genero = " + cliente.getPessoa().getGenero());
		}
		if(cliente.getTelefone() != null && cliente.getTelefone().get(0).getNumero() != null){
			sb.append( "and b.numero ilike '%" + cliente.getTelefone().get(0).getNumero() + "%'");
		}
		try{
			openConnection();
			pst = connection.prepareStatement(sb.toString());
			ResultSet rs = pst.executeQuery();
			List<EntidadeDominio> clientes = new ArrayList<>();
			while(rs.next()){
				Cliente c = new Cliente();
				c.setId(rs.getInt("id"));
				PessoaFisica p = new PessoaFisica();
				p.setNome(rs.getString("nome"));
				p.setCpf(rs.getString("cpf"));
				p.setGenero(rs.getString("genero").charAt(0));
				p.setNascimento(new java.util.Date(rs.getDate("nascimento").getTime()));
				c.setEmail(rs.getString("email"));
				c.setSenha(rs.getString("senha"));
				c.setPessoa(p);
				Telefone t = new Telefone();
				t.setArea(rs.getString("area"));
				t.setNumero(t.getArea() + "" + rs.getString("numero"));
				List<Telefone> lst = new ArrayList<>();
				lst.add(t);
				c.setTelefone(lst);
				clientes.add(c);
				
			}
			return clientes;
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	

}
