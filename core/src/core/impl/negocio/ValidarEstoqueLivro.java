package core.impl.negocio;

import java.util.List;

import core.dfs.IStrategy;
import core.impl.dao.LivroDAO;
import dominio.EntidadeDominio;
import entities.produto.Livro;
import entities.venda.Compra;
import entities.venda.Item;

public class ValidarEstoqueLivro implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		LivroDAO livroDAO = new LivroDAO();
		Compra compra = (Compra) entidade;
		Livro livro  = new Livro();
		@SuppressWarnings("unchecked")
		List<Livro> lst = (List<Livro>) (List<?>) livroDAO.consultar(livro);
		for(Item item : compra.getProdutos().getItens()) {
			Livro itemLivro = (Livro) item.getProduto();
			for(Livro l: lst) {
				if(item.getId() == l.getId() && item.getQuantidade() > l.getEstoque())
					return "Erro - Não foi possivel processar o pedido quantidade maior do que a em estoque";
			}
		}
		return null;
	}

}
