package entities.cadastros;

import dominio.EntidadeDominio;

public class Cartao extends EntidadeDominio {

	private String numero;
	
	private String titular;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

}
