package web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.dfs.aplicacao.Resultado;
import entities.produto.Categoria;
import web.controle.web.command.ICommand;
import web.controle.web.command.impl.AlterarClienteCommand;
import web.controle.web.command.impl.ConsultarClienteCommand;
import web.controle.web.command.impl.ConsultarCommand;
import web.controle.web.command.impl.SalvarClienteCommand;

@Controller
public class AjaxController {
	private static Map<String, ICommand> commands;
	
	public AjaxController() {
		// TODO Auto-generated constructor stub
		commands = new HashMap<String, ICommand>();
		commands.put("SALVAR", new SalvarClienteCommand());
		commands.put("CONSULTAR", new ConsultarClienteCommand());
		commands.put("ALTERAR", new AlterarClienteCommand());
		commands.put("CONSULTARLIVRO", new ConsultarCommand());
	}
	
	@RequestMapping(value="public/categorias")
	public void getCategorias(Categoria categoria,HttpServletResponse response)  throws Exception {
		ICommand command = commands.get("CONSULTAR");
		Resultado result =  command.execute(categoria);
		response.setStatus(200);
		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().write(mapper.writeValueAsString(result.getEntidades()));
	}
}
