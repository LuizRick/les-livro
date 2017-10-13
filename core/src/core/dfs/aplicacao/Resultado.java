package core.dfs.aplicacao;

import java.util.List;
import dominio.EntidadeDominio;

public class Resultado extends EntidadeAplicacao{

	private String msg;
	private List<EntidadeDominio> entidades;
	
	public String getMsg(){
		return msg;
	}
	
	public void setMsg(String msg){
		this.msg = msg;
	}

	public List<EntidadeDominio> getEntidades() {
		return entidades;
	}

	public void setEntidades(List<EntidadeDominio> entidades) {
		this.entidades = entidades;
	}
	
}
