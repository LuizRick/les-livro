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
import entities.cadastros.Cartao;
import entities.cadastros.CartaoCredito;
import entities.cadastros.Cidade;
import entities.cadastros.Cliente;
import entities.cadastros.Endereco;
import entities.cadastros.Estado;
import entities.cadastros.Pais;
import entities.cadastros.PessoaFisica;
import entities.cadastros.Telefone;
import entities.cadastros.TipoTelefone;
import webapp.controle.web.vh.IViewHelper;

public class ClienteViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Cliente cliente = new Cliente();
		List<Cartao> cartoes = new ArrayList<Cartao>();
		List<Endereco> enderecos = new ArrayList<Endereco>();
		PessoaFisica pessoa = new PessoaFisica();
		String nome = request.getParameter("txtNome");
		String cpf = request.getParameter("txtCpf");
		String email = request.getParameter("txtEmail");
		String senha = request.getParameter("txtSenha");
		String nascimento = request.getParameter("txtNascimento");
		String sexo = request.getParameter("radSexo");
		String tTelefone = request.getParameter("tipo");
		String telefone = request.getParameter("telefone");
		String titulares[] = request.getParameterValues("txtTitulares");
		String numCard[] = request.getParameterValues("txtCardNum");
		String bandCard[] = request.getParameterValues("txtCardBand");
		String codCard[] = request.getParameterValues("txtCardCod");
		String valCard[] = request.getParameterValues("txtCardVal");
		
		String logradouro[] = request.getParameterValues("txtLogradouro");
		String bairro[] = request.getParameterValues("txtBairro");
		String cep[] = request.getParameterValues("txtCep");
		String numero[] = request.getParameterValues("txtNumero");
		String complemento[] = request.getParameterValues("txtComplemento");
		String nomeRes[] = request.getParameterValues("txtNomesRes");
		String tipoRes[] = request.getParameterValues("txtTipoRes");
		String tipoLog[] = request.getParameterValues("txtTipoLog");
		String cidade[] = request.getParameterValues("txtCidade");
		String estados[] = request.getParameterValues("txtEstados");
		String pais[] = request.getParameterValues("txtPaises");
		String preferencial[] = request.getParameterValues("checkPreferencial");
		
		for(int i = 0; i < titulares.length;i++) {
			CartaoCredito cartao = new CartaoCredito();
			cartao.setTitular(titulares[i]);
			cartao.setNumero(numCard[i]);
			cartao.setBandeira(bandCard[i]);
			cartao.setCodigo(codCard[i]);
			cartao.setValidade(ConvertDate.converteStringDate(valCard[i]));
			cartoes.add(cartao);
		}
		for(int i = 0;i < logradouro.length;i++) {
			Endereco endereco = new Endereco();
			endereco.setLogradouro(logradouro[i]);
			endereco.setBairro(bairro[i]);
			endereco.setCep(cep[i]);
			endereco.setNumero(numero[i]);
			endereco.setComplemento(complemento[i]);
			endereco.setNome(nomeRes[i]);
			endereco.setTipoLogradouro(tipoLog[i]);
			endereco.setTipoResidencia(tipoRes[i]);
			Cidade cid = new Cidade();
			cid.setNome(cidade[i]);
			Estado objEstado = new Estado();
			objEstado.setNome(estados[i]);
			Pais objPais = new Pais();
			objPais.setNome(pais[i]);
			objPais.setEstado(objEstado);
			objEstado.setPais(objPais);
			cid.setEstado(objEstado);
			if(preferencial[i] != null) {
				endereco.setPreferencial(true);
			}else {
				endereco.setPreferencial(false);
			}
			endereco.setCidade(cid);
			enderecos.add(endereco);
		}
		pessoa.setCpf(cpf);
		pessoa.setNome(nome);
		pessoa.setGenero(sexo.charAt(0));
		pessoa.setNascimento(ConvertDate.converteStringDate(nascimento));
		Telefone tel = new Telefone();
		tel.setArea(telefone.split("-")[0].replaceAll("[()]", ""));
		tel.setNumero(telefone.split("-")[1] + "-" + telefone.split("-")[2]);
		TipoTelefone tipoTel = TipoTelefone.values()[Integer.parseInt(tTelefone)];
		tel.setTipo(tipoTel);
		cliente.setPessoa(pessoa);
		cliente.setEmail(email);
		cliente.setSenha(senha);
		List<Telefone> telefones = new ArrayList<Telefone>();
		telefones.add(tel);
		cliente.setTelefone(telefones);
		cliente.setCartao(cartoes);
		cliente.setEndereco(enderecos);
		return cliente;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String operacao = request.getParameter("operacao");
		RequestDispatcher d = null;
		if(resultado.getMsg() == null){
			if(operacao.equals("SALVAR")){
				resultado.setMsg("Cliente Salvo com sucesso");
			}
			request.getSession().setAttribute("resultado",resultado);
			d = request.getRequestDispatcher("FrmConsultarCliente.jsp");
		}
		d.forward(request, response);
	}

}
