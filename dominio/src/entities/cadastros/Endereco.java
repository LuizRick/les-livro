package entities.cadastros;

import dominio.EntidadeDominio;

public class Endereco extends EntidadeDominio {

	private String logradouro;

	private String bairro;

	private String cep;

	private String numero;

	private String complemento;

	private String nome;

	private Cidade cidade;

	private String tipoLogradouro;

	private String tipoResidencia;

	private String observacao;

	private Boolean preferencial;

	public String id_cliente;

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getTipoLogradouro() {
		return tipoLogradouro;
	}

	public void setTipoLogradouro(String tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}

	public String getTipoResidencia() {
		return tipoResidencia;
	}

	public void setTipoResidencia(String tipoResidencia) {
		this.tipoResidencia = tipoResidencia;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Boolean getPreferencial() {
		return preferencial;
	}

	public void setPreferencial(Boolean preferencial) {
		this.preferencial = preferencial;
	}

	public String getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(String id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getEnderecoCompleto(String endereco) {
		if (endereco != null)
			return endereco;
		else
			endereco = "Rua:" + this.logradouro + "; Numero:" + this.numero + "; Bairro:" + this.bairro + "; Cep:"
					+ this.cep + "Cidade: " + this.cidade.getNome() + "; Estado: "
					+ this.getCidade().getEstado().getNome();
		return endereco;
	}

}
