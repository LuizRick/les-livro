package entities.cadastros;

import entities.usuarios.Usuario;

import java.util.List;

public class Cliente extends Usuario {

	private String email;

	private String senha;

	private Pessoa pessoa;

	private List<Endereco> endereco;

	private List<Telefone> telefone;

	private Ranking ranking;

	private List<Cartao> cartao;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Endereco> getEndereco() {
		return endereco;
	}

	public void setEndereco(List<Endereco> endereco) {
		this.endereco = endereco;
	}

	public List<Telefone> getTelefone() {
		return telefone;
	}

	public void setTelefone(List<Telefone> telefone) {
		this.telefone = telefone;
	}

	public Ranking getRanking() {
		return ranking;
	}

	public void setRanking(Ranking ranking) {
		this.ranking = ranking;
	}

	public List<Cartao> getCartao() {
		return cartao;
	}

	public void setCartao(List<Cartao> cartao) {
		this.cartao = cartao;
	}

}
