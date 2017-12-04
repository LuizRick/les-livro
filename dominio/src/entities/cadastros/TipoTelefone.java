package entities.cadastros;

public enum TipoTelefone {
	Residencial(1), Celular(2), Comercial(3);

	public int valorTipo;

	TipoTelefone(int valor) {
		valorTipo = valor;
	}

	public static TipoTelefone setValue(int valor) {
		switch (valor) {
		case 1:
			return TipoTelefone.Residencial;
		case 2:
			return TipoTelefone.Celular;
		case 3:
			return TipoTelefone.Comercial;
		}
		return null;
	}
}
