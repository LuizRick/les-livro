package entities.usuarios;

public class Gerente extends Pessoa implements ICargoSuperior {
	private String setor;

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}
}
