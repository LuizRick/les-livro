package entities.venda;

import dominio.EntidadeDominio;

public class Trocas extends EntidadeDominio{
	private Compra compra;
	private StatusCompra status;

	public StatusCompra getStatus() {
		return status;
	}

	public void setStatus(StatusCompra status) {
		this.status = status;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}
}
