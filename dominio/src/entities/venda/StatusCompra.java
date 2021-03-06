package entities.venda;

public enum StatusCompra {
	Processamento(1),Troca(2),Aprovado(3),Reprovado(4),Transporte(5),Entregue(6),Emtroca(7),Trocado(8);
	
	public int statusCompra;
	
	private StatusCompra(int valor) {
		// TODO Auto-generated constructor stub
		statusCompra  = valor;
	}
	
	public static StatusCompra status(int s) {
		switch (s) {
		case 1:
			return StatusCompra.Processamento;
		case 2:
			return StatusCompra.Troca;
		case 3:
			return StatusCompra.Aprovado;
		case 4:
			return StatusCompra.Reprovado;
		case 5:
			return StatusCompra.Transporte;
		case 6:
			return StatusCompra.Entregue;
		case 7:
			return StatusCompra.Emtroca;
		case 8:
			return StatusCompra.Trocado;
		}
		return StatusCompra.Troca;
	}
	
	public int getStatus() {
		return statusCompra;
	}
}
