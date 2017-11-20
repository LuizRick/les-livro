package webapp.controle.web.vh.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.dfs.aplicacao.Resultado;
import dominio.EntidadeDominio;
import entities.cadastros.Cliente;
import entities.venda.Venda;
import webapp.controle.web.vh.IViewHelper;

public class CompraViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String cartoes[] = request.getParameterValues("formasPagamento.id");
		String valores[] = request.getParameterValues("formasPagamento.valor");
		String cuponCompra[] = request.getParameterValues("cupomCompra");
		String enderecoId = request.getParameter("frete.endereco.id");
		Venda venda = new Venda();
		Cliente cliente = (Cliente)request.getSession().getAttribute("cliente");
		return venda;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub 
		String operacao = request.getParameter("operacao");
		RequestDispatcher d = null;
		if(resultado.getMsg() == null){
			if(operacao.equals("SALVAR")){
				resultado.setMsg("Venda feita com sucesso");
			}
			request.getSession().setAttribute("resultado",resultado);
			d = request.getRequestDispatcher("public/compras");
		}
		d.forward(request, response);
	}

}
