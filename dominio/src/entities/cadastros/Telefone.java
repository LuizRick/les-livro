package entities.cadastros;

import dominio.EntidadeDominio;

public class Telefone extends EntidadeDominio {

	private String numero;

	private String codArea;
	
	private TipoTelefone tipo;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getArea() {
		return codArea;
	}

	public void setArea(String codArea) {
		this.codArea = codArea;
	}

	public TipoTelefone getTipo() {
		return tipo;
	}

	public void setTipo(TipoTelefone tipo) {
		this.tipo = tipo;
	}

}
