package entities.venda;

public enum TipoTroca {
	Parcial(1),Completa(2);
	
	public int valor;
	
	private TipoTroca(int valorE) {
		valor = valorE;
	}
	
	
	public static TipoTroca getStatus(int valor) {
		switch(valor) {
		case 1:
			return TipoTroca.Parcial;
		case 2:
			return TipoTroca.Completa;
		}
		return null;
	}
	
}
