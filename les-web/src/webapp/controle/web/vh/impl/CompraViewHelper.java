package webapp.controle.web.vh.impl;


import java.io.IOException;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.dfs.aplicacao.Resultado;
import dominio.EntidadeDominio;
import entities.cadastros.CartaoCredito;
import entities.cadastros.Cliente;
import entities.cadastros.CupomCompra;
import entities.cadastros.Endereco;
import entities.venda.CarrinhoCompra;
import entities.venda.Compra;
import entities.venda.Frete;

import com.fasterxml.jackson.databind.ObjectMapper;
import webapp.controle.web.vh.IViewHelper;

public class CompraViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		// TODO Auto-generated method stub
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonCartao = request.getParameter("cartoesJSON");
			String jsonCupons = request.getParameter("cuponsJSON");
			String jsonEndereco = request.getParameter("enderecoJSON");
			String total = request.getParameter("total");
			String totalFrete  = request.getParameter("totalFrete");
			String totalDesc  = request.getParameter("totalDesc");
			CartaoCredito[] credito = mapper.readValue(jsonCartao,CartaoCredito[].class);
			Endereco endereco = mapper.readValue(jsonEndereco, Endereco.class);
			Compra compra = new Compra();
			Cliente cliente = (Cliente)request.getSession().getAttribute("cliente");
			CarrinhoCompra carrinho = (CarrinhoCompra) request.getSession().getAttribute("carrinho");
			Frete frete = new Frete();
			frete.setEndereco(endereco);
			frete.setValor(Double.parseDouble(totalFrete));
			compra.setCliente(cliente);
			compra.setProdutos(carrinho);
			compra.setFormasPagamento(Arrays.asList(credito));
			if(jsonCupons != null) {
				CupomCompra[] cupons = mapper.readValue(jsonCupons, CupomCompra[].class);
				compra.setCuponCompra(Arrays.asList(cupons));
			}
			compra.setTotal(Double.parseDouble(total));
			compra.setFrete(frete);
			return compra;
		}catch(Exception ex) {
			return null;
		}
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
			d = request.getRequestDispatcher("public/pedidos");
		}
		d.forward(request, response);
	}

}
