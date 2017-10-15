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
import entities.cadastros.Cartao;
import entities.cadastros.CartaoCredito;
import entities.cadastros.Cliente;
import entities.cadastros.Endereco;
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
			for(int i=0;i < cliente.getEndereco().size();i++){
				Endereco e = cliente.getEndereco().get(i);
				pst = connection.prepareStatement(
						"INSERT INTO endereco(logradouro, bairro, cep, numero, complemento, nome, tipo_residencia, "
						+ "tipo_logradouro, cidade, estado, pais, id_cliente) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
				pst.setString(1,e.getLogradouro());
				pst.setString(2, e.getBairro());
				pst.setString(3, e.getCep());
				pst.setString(4, e.getNumero());
				pst.setString(5, e.getComplemento());
				pst.setString(6, e.getNome());
				pst.setString(7, e.getTipoResidencia());
				pst.setString(8, e.getTipoLogradouro());
				pst.setString(9, e.getCidade().getNome());
				pst.setString(10, e.getCidade().getEstado().getNome());
				pst.setString(11, e.getCidade().getEstado().getPais().getNome());
				pst.setInt(12, cliente.getId());
				pst.executeUpdate();
			}
			
			for(int i = 0;i <= cliente.getCartao().size();i++){
				CartaoCredito card = (CartaoCredito) cliente.getCartao().get(i);
				pst = connection.prepareStatement("INSERT INTO cartao_credito("+
			            "titular, numero, bandeira, codigo_seguranca, validade, id_cliente)"+
					    "VALUES (?, ?, ?, ?, ?, ?);");
				pst.setString(1, card.getTitular());
				pst.setString(2, card.getNumero());
				pst.setString(3, card.getBandeira());
				pst.setString(4, card.getCodigo());
				pst.setString(5, card.getValidade().toString());
				pst.setInt(6, cliente.getId());
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
		openConnection();
		PreparedStatement pst = null;
		Cliente cliente = (Cliente) entidade;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE cliente SET  nome=?, nascimento=?, genero=?, cpf=?, email=?, senha=? WHERE id = ?;)");
			pst = connection.prepareStatement(sql.toString());
			pst.setString(1, cliente.getPessoa().getNome());
			Date date = new Date(cliente.getPessoa().getNascimento().getTime());
			pst.setDate(2, date);
			pst.setString(3, cliente.getPessoa().getGenero() + "");
			pst.setString(4, ((PessoaFisica)cliente.getPessoa()).getCpf());
			pst.setString(5, cliente.getEmail());
			pst.setString(6, cliente.getSenha());
			pst.setInt(7, cliente.getId());
			pst.executeUpdate();
			pst = connection.prepareStatement(sql.toString());
			for(int i = 0;i < cliente.getTelefone().size();i++){
				Telefone t = cliente.getTelefone().get(i);
				pst = connection.prepareStatement("UPDATE telefone SET  codigo_area=?, numero=?, tipo=? WHERE id_cliente= ?;");
				pst.setString(1, t.getArea());
				pst.setString(2, t.getNumero());
				pst.setInt(3, t.getTipo().valorTipo);
				pst.setInt(4, cliente.getId());
				pst.executeUpdate();
			}
			for(int i=0;i < cliente.getEndereco().size();i++){
				Endereco e = cliente.getEndereco().get(i);
				pst = connection.prepareStatement(
						"UPDATE endereco SET logradouro=?, bairro=?, cep=?, numero=?, complemento=?,"+ 
						       "nome=?, tipo_residencia=?, tipo_logradouro=?, cidade=?, estado=?,"+ 
						       "pais=?, id_cliente=? WHERE id = ?");
				pst.setString(1,e.getLogradouro());
				pst.setString(2, e.getBairro());
				pst.setString(3, e.getCep());
				pst.setString(4, e.getNumero());
				pst.setString(5, e.getComplemento());
				pst.setString(6, e.getNome());
				pst.setString(7, e.getTipoResidencia());
				pst.setString(8, e.getTipoLogradouro());
				pst.setString(9, e.getCidade().getNome());
				pst.setString(10, e.getCidade().getEstado().getNome());
				pst.setString(11, e.getCidade().getEstado().getPais().getNome());
				pst.setInt(12, cliente.getId());
				pst.setInt(13, e.getId());
				pst.executeUpdate();
			}
			
			for(int i = 0;i <= cliente.getCartao().size();i++){
				CartaoCredito card = (CartaoCredito) cliente.getCartao().get(i);
				pst = connection.prepareStatement("UPDATE cartao_credito SET titular=?, numero=?, "
						+ "bandeira=?, codigo_seguranca=?, validade=?, id_cliente=? WHERE id=?;");
				pst.setString(1, card.getTitular());
				pst.setString(2, card.getNumero());
				pst.setString(3, card.getBandeira());
				pst.setString(4, card.getCodigo());
				pst.setString(5, card.getValidade().toString());
				pst.setInt(6, cliente.getId());
				pst.setInt(7, card.getId());
				pst.executeUpdate();
			}
			connection.commit();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
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
