package entities.cadastros;

public enum TipoCupom {
	Promocional(1),Compra(2),Troca(3);
	
	public int valorTipoCupom;
	
	private TipoCupom(int valor) {
		// TODO Auto-generated constructor stub
		valorTipoCupom = valor;
	}
	
	public static TipoCupom getTipoCupom(int v) {
		switch (v) {
		case 1:
			return TipoCupom.Promocional;
		case 2:
			return TipoCupom.Troca;
		case 3:
			return TipoCupom.Compra;
		default:
			return null;
		}
	}
}
