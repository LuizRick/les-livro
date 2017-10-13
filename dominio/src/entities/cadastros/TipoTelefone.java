package entities.cadastros;

public enum TipoTelefone {
	Residencial(1),Celular(2),Comercial(3);

	public int valorTipo;
	TipoTelefone(int valor){
		valorTipo = valor;
	}
}
