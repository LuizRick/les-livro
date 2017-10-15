package core.impl.negocio;

import core.dfs.IStrategy;
import core.util.ValidateUtils;
import entities.cadastros.Cliente;
import entities.cadastros.Endereco;

public class ValidarEnderecoObrigatorioCliente implements IStrategy{

	
	public String processar(dominio.EntidadeDominio entidade) {
		Cliente cli = (Cliente) entidade;
		ValidateUtils v = new ValidateUtils();
		for(int i = 0;i < cli.getEndereco().size();i++){
			Endereco e = cli.getEndereco().get(i);
			if(v.isNullOrEmpty(e.getBairro()) || v.isNullOrEmpty(e.getCep()) || v.isNullOrEmpty(e.getLogradouro())
					|| v.isNullOrEmpty(e.getNome()) || v.isNullOrEmpty(e.getTipoLogradouro()) || v.isNullOrEmpty(e.getTipoResidencia())
					|| v.isNullOrEmpty(e.getNumero()) ||e.getCidade() == null)
				return "E necessario que todos os campos seja preenchidos em cada endereço";
		}
		return null;
	};
}
