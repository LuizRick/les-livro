package core.impl.negocio;


import core.dfs.IStrategy;
import core.util.ValidateUtils;
import dominio.EntidadeDominio;
import entities.produto.Livro;

public class ValidarCamposObrigadoriosLivro implements IStrategy {
	ValidateUtils v;
	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		Livro l = (Livro) entidade;
		v = new ValidateUtils();
		if(v.isNullOrEmpty(l.getAutor()) || v.isNullOrEmpty(l.getAno()) || v.isNullOrEmpty(l.getTitulo()) || v.isNullOrEmpty(l.getEditora())
				|| v.isNullOrEmpty(l.getEdicao()) || v.isNullOrEmpty(l.getIsbn()) || v.isNullOrEmpty(l.getNpaginas()) || v.isNullOrEmpty(l.getSinopse())
				|| l.getCategoria().size() <= 0){
			return "Os campos: autor, ano, titulo, editora, edição, ISBN, Numero de Paginas, Sinopse e  categorias são obrigatorios";
		}
		return null;
	}

}
