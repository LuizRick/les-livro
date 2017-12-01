package web.controle.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.dfs.aplicacao.Resultado;
import dominio.EntidadeDominio;
import web.controle.web.command.ICommand;
import web.controle.web.command.impl.AlterarClienteCommand;
import web.controle.web.command.impl.ConsultarClienteCommand;
import web.controle.web.command.impl.ConsultarCommand;
import web.controle.web.command.impl.SalvarClienteCommand;
import web.controle.web.command.impl.VisualizarClienteCommand;
import webapp.controle.web.vh.IViewHelper;
import webapp.controle.web.vh.impl.ClienteViewHelper;
import webapp.controle.web.vh.impl.ConsultarClienteViewHelper;
import webapp.controle.web.vh.impl.CompraViewHelper;

/**
 * Servlet implementation class Servlet
 */
@WebServlet({"/Servlet","/cliente/SalvarCliente","/cliente/ConsultarCliente","/public/setcompra"})
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Map<String, ICommand> commands;
	private static Map<String, IViewHelper> vhs;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        // TODO Auto-generated constructor stub
        commands = new HashMap<String, ICommand>();
        commands.put("SALVAR", new SalvarClienteCommand());
        commands.put("CONSULTAR", new  ConsultarClienteCommand());
        commands.put("VISUALIZAR", new VisualizarClienteCommand());
        commands.put("ALTERAR",new AlterarClienteCommand());
        vhs = new HashMap<String, IViewHelper>();
        vhs.put("/les-web/cliente/SalvarCliente",new ClienteViewHelper());
        vhs.put("/les-web/cliente/ConsultarCliente", new ConsultarClienteViewHelper());
        vhs.put("/les-web/public/setcompra", new CompraViewHelper());
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcessRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcessRequest(request, response);
	}
	
	protected void doProcessRequest(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String operacao = request.getParameter("operacao");
		IViewHelper vh = vhs.get(uri);
		EntidadeDominio entidade = vh.getEntidade(request);
		ICommand command = commands.get(operacao);
		Resultado resultado = command.execute(entidade);
		vh.setView(resultado, request, response);
	}

}
