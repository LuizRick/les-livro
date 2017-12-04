package entities.cadastros;

import java.util.Date;

import dominio.EntidadeDominio;
import entities.venda.IFormaPagamento;

public class CupomCompra extends EntidadeDominio implements IFormaPagamento {
	private String codigoCupom;
	private String nome;
	private Double valor;
	private Boolean valido;
	private TipoCupom tipo;
	private Date validade;
	
	public CupomCompra() {
		// TODO Auto-generated constructor stub
	}
	
	
	public CupomCompra(String codigoCupom,String nome,Double valor,Boolean valido) {
		this.codigoCupom = codigoCupom;
		this.nome = nome;
		this.valor = valor;
		this.valido = valido;
	}
	
	public CupomCompra(String codigoCupom,String nome,Double valor,TipoCupom tipo) {
		this(codigoCupom, nome, valor, true);
		this.tipo = tipo;
	}
	
	public String getCodigoCupom() {
		return codigoCupom;
	}
	public void setCodigoCupom(String codigoCupom) {
		this.codigoCupom = codigoCupom;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}

	public boolean isValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}


	public TipoCupom getTipo() {
		return tipo;
	}


	public void setTipo(TipoCupom tipo) {
		this.tipo = tipo;
	}


	public Boolean getValido() {
		return valido;
	}


	public void setValido(Boolean valido) {
		this.valido = valido;
	}


	public Date getValidade() {
		return validade;
	}


	public void setValidade(Date validade) {
		this.validade = validade;
	}
}
