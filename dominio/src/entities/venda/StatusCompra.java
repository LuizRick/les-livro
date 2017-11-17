package entities.venda;

public enum StatusCompra {
	Processamento(1),Troca(2),Aprovado(3),Reprovado(4),Transporte(5),Entregue(6);
	
	public int statusCompra;
	
	private StatusCompra(int valor) {
		// TODO Auto-generated constructor stub
		statusCompra  = valor;
	}
}
