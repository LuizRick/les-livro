package entities.produto;

import dominio.EntidadeDominio;

public class GrupoPrecificacao extends EntidadeDominio {

	private String descricao;
	
	private double margem;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getMargem() {
		return margem;
	}

	public void setMargem(double margem) {
		this.margem = margem;
	}

	
}
