package entities.cadastros;

import dominio.EntidadeDominio;

public class Ranking extends EntidadeDominio {
	
	private String posicao;
	
	private String qtdCompra;

	public String getPosicao() {
		return posicao;
	}

	public void setPosicao(String posicao) {
		this.posicao = posicao;
	}

	public String getQtdCompra() {
		return qtdCompra;
	}

	public void setQtdCompra(String qtdCompra) {
		this.qtdCompra = qtdCompra;
	}
}
