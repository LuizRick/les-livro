package entities.produto;

import dominio.EntidadeDominio;

public class StatusVenda extends EntidadeDominio {
	
	private String motivo;
	private short categoria;
	private Integer idLivro;
	private Boolean status;
	
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public short getCategoria() {
		return categoria;
	}
	public void setCategoria(short categoria) {
		this.categoria = categoria;
	}
	public Integer getIdLivro() {
		return idLivro;
	}
	public void setIdLivro(Integer idLivro) {
		this.idLivro = idLivro;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
}
