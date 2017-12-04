package entities.cadastros;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CartaoCredito extends Cartao {

	private String bandeira;

	private String codigo;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date validade;
	
	private Boolean addPerfil;
	
	private Integer id_cliente;
	
	private Double valor; 
	
	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getBandeira() {
		return bandeira;
	}

	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Date getValidade() {
		return validade;
	}

	public void setValidade(Date validade) {
		this.validade = validade;
	}

	public Boolean getAddPerfil() {
		return addPerfil;
	}

	public void setAddPerfil(Boolean addPerfil) {
		this.addPerfil = addPerfil;
	}

	public Integer getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(Integer id_cliente) {
		this.id_cliente = id_cliente;
	}

}
