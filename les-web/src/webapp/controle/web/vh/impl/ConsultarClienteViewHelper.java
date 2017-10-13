package webapp.controle.web.vh.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.dfs.aplicacao.Resultado;
import core.util.ConvertDate;
import dominio.EntidadeDominio;
import entities.cadastros.Cliente;
import entities.cadastros.PessoaFisica;
import entities.cadastros.Telefone;
import entities.cadastros.TipoTelefone;
import webapp.controle.web.vh.IViewHelper;

public class ConsultarClienteViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Cliente cliente = new Cliente();
		List<Telefone> telefones = new ArrayList<Telefone>();
		PessoaFisica pessoa = new PessoaFisica();
		String id = request.getParameter("id");
		String nome = request.getParameter("txtNome");
		String cpf = request.getParameter("txtCpf");
		String email = request.getParameter("txtEmail");
		String senha = request.getParameter("txtSenha");
		String nascimento = request.getParameter("txtNascimento");
		String sexo = request.getParameter("radSexo");
		String tTelefone = request.getParameter("tipo");
		String telefone = request.getParameter("telefone");
		pessoa.setCpf(cpf);
		pessoa.setNome(nome);
		if (sexo != null)
			pessoa.setGenero(sexo.charAt(0));
		if (nascimento != null && !nascimento.equals(""))
			pessoa.setNascimento(ConvertDate.converteStringDate(nascimento));
		if (telefone != null && !telefone.equals("")) {
			Telefone tel = new Telefone();
			tel.setArea(telefone.split("-")[0].replaceAll("[()]", ""));
			tel.setNumero(telefone.split("-")[1] + "-" + telefone.split("-")[2]);
			TipoTelefone tipoTel = TipoTelefone.values()[Integer.parseInt(tTelefone)];
			tel.setTipo(tipoTel);
			telefones.add(tel);
			cliente.setTelefone(telefones);
		}
		if(id != null){
			cliente.setId(Integer.parseInt(id));
		}
		cliente.setPessoa(pessoa);
		cliente.setEmail(email);
		cliente.setSenha(senha);
		return cliente;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String operacao = request.getParameter("operacao");
		RequestDispatcher d = null;
		if (resultado.getMsg() == null) {
			if (operacao.equals("CONSULTAR")) {
				request.getSession().setAttribute("resultado", resultado);
			}
			d = request.getRequestDispatcher("ListarClientes.jsp");
		}
		if (operacao.equals("VISUALIZAR")) {
			request.getSession().setAttribute("cliente", resultado.getEntidades().get(0));
			d = request.getRequestDispatcher("frmCadastrar.jsp");// cadastro de cliente
		}
		request.setAttribute("resultado", resultado);
		d.forward(request, response);
	}
}
