package entities.cadastros;

public enum TipoCupom {
	Promocional(1),Compra(2),Troca(3);
	
	public int valorTipoCupom;
	
	private TipoCupom(int valor) {
		// TODO Auto-generated constructor stub
		valorTipoCupom = valor;
	}
}
