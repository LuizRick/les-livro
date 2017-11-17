package entities.venda;

import entities.cadastros.Endereco;

public class Frete {
	private Endereco endereco;
	private Double valor;
	
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
}
