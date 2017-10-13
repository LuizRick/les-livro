package entities.cadastros;

import dominio.EntidadeDominio;
import java.util.Date;

public abstract class Pessoa extends EntidadeDominio {

	private String nome;

	private Date nascimento;

	private char genero;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public char getGenero() {
		return genero;
	}

	public void setGenero(char genero) {
		this.genero = genero;
	}

}
