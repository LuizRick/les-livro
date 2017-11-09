package web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import core.impl.dao.ClienteDAO;
import entities.cadastros.Cliente;

@Controller
public class PageController {

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
}
