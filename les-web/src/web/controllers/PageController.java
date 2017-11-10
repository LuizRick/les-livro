package web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import core.dfs.aplicacao.Resultado;
import core.impl.dao.ClienteDAO;
import entities.cadastros.Cliente;
import entities.produto.Livro;
import web.controle.web.command.ICommand;
import web.controle.web.command.impl.ConsultarCommand;
import web.controle.web.command.impl.SalvarCommand;

@Controller
public class PageController {
	
	private static Map<String, ICommand> commands;
	
	 public PageController() {
		// TODO Auto-generated constructor stub
		commands = new HashMap<String, ICommand>();
		commands.put("SALVAR", new SalvarCommand());
		commands.put("CONSULTAR", new ConsultarCommand());
	}
	
	@RequestMapping("/olaSpring")
	public String execute() {
		System.out.println("Executando methdo spring");
		return "ok";
	}
	
	@RequestMapping("/loginCliente")
	public String loginCliente(Cliente cliente,HttpServletResponse response,HttpServletRequest request) throws Exception {
		ClienteDAO dao = new ClienteDAO();
		cliente = (Cliente) dao.consultar(cliente).get(0);
		if(cliente != null) {
			request.getSession().setAttribute("cliente", cliente);
			return "redirect:/cliente/ListarClientes.jsp";
		}
		return "redirect:login.xhtml";
	}
	
	@RequestMapping("/public/index")
	public String processar(Livro livro,Model model) {
		Resultado resultado =  commands.get("CONSULTAR").execute(livro);
		List<Livro> lista = new ArrayList<Livro>();
		for(int i = 0;i < resultado.getEntidades().size();i++) {
			lista.add((Livro)resultado.getEntidades().get(i));
		}
		model.addAttribute("livros", lista);
		return "ecommerce/index";
	}
	
	@RequestMapping("/public/produto")
	public String produto(Livro livro,Model model) {
		Resultado resultado =  commands.get("CONSULTAR").execute(livro);
		Livro l = (Livro)resultado.getEntidades().get(0);
		model.addAttribute("livro", l);
		return "ecommerce/produto";
	}
	
}
