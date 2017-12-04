package web.controllers;

import java.awt.image.IndexColorModel;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.json.JSONObject;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import core.dfs.aplicacao.ListaCupomsCompra;
import core.dfs.aplicacao.Resultado;
import core.impl.dao.ClienteDAO;
import dominio.EntidadeDominio;
import entities.cadastros.Cartao;
import entities.cadastros.CartaoCredito;
import entities.cadastros.Cliente;
import entities.cadastros.CupomCompra;
import entities.cadastros.Endereco;
import entities.produto.IProduto;
import entities.produto.Livro;
import entities.venda.CarrinhoCompra;
import entities.venda.Compra;
import web.controle.web.AprovarCartao;
import web.controle.web.command.ICommand;
import web.controle.web.command.impl.AlterarClienteCommand;
import web.controle.web.command.impl.ConsultarClienteCommand;
import web.controle.web.command.impl.ConsultarCommand;
import web.controle.web.command.impl.SalvarClienteCommand;
import web.controle.web.command.impl.SalvarCommand;
import entities.venda.Item;
import entities.venda.StatusCompra;
import entities.venda.Trocas;
import entities.venda.Venda;

@Controller
@SessionAttributes({ "carrinho", "resultado" })
public class PageController {

	private static Map<String, ICommand> commands;

	public PageController() {
		// TODO Auto-generated constructor stub
		commands = new HashMap<String, ICommand>();
		commands.put("SALVAR", new SalvarClienteCommand());
		commands.put("CONSULTAR", new ConsultarClienteCommand());
		commands.put("ALTERAR", new AlterarClienteCommand());
		commands.put("CONSULTARLIVRO", new ConsultarCommand());
	}

	@RequestMapping("/olaSpring")
	public String execute() {
		System.out.println("Executando methdo spring");
		return "ok";
	}

	@RequestMapping("loginCliente")
	public String loginCliente(Cliente cliente, HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		ClienteDAO dao = new ClienteDAO();
		List<EntidadeDominio> clientes = dao.consultar(cliente);
		if (clientes.size() > 0)
			cliente = (Cliente) dao.consultar(cliente).get(0);
		if (cliente.getId() != null) {
			request.getSession().setAttribute("cliente", cliente);
			return "redirect:/public/verifyCart";
		}
		return "redirect:login.xhtml";
	}

	@RequestMapping("/public/index")
	public String processar(Livro livro, Model model) {
		Resultado resultado = commands.get("CONSULTARLIVRO").execute(livro);
		List<Livro> lista = new ArrayList<Livro>();
		for (int i = 0; i < resultado.getEntidades().size(); i++) {
			Livro l = (Livro) resultado.getEntidades().get(i);
			if (l.getStatus() > 0)
				lista.add(l);
		}
		model.addAttribute("livros", lista);
		if (!model.containsAttribute("carrinho"))
			model.addAttribute("carrinho", new CarrinhoCompra());
		return "ecommerce/index";
	}

	@RequestMapping("/public/produto")
	public String produto(Livro livro, Model model) {
		Resultado resultado = commands.get("CONSULTARLIVRO").execute(livro);
		Livro l = (Livro) resultado.getEntidades().get(0);
		model.addAttribute("livro", l);
		return "ecommerce/produto";
	}

	@RequestMapping(value = "/public/additem", method = RequestMethod.POST)
	public String addcarinho(@ModelAttribute("carrinho") CarrinhoCompra carrinho, Item item, Model model) {
		Livro livro = new Livro();
		livro.setId(item.getId());
		Resultado resultado = commands.get("CONSULTARLIVRO").execute(livro);
		if (resultado.getEntidades() != null) {
			livro = (Livro) resultado.getEntidades().get(0);
			item.setProduto(livro);
			if (livro.getEstoque() < item.getQuantidade())
				resultado.setMsg("estoque insuficiente");
			else if (!carrinho.hasItem(livro.getId())) {
				resultado.setMsg("adicionado com sucesso");
				carrinho.getItens().add(item);
			} else {
				for (Item i : carrinho.getItens()) {
					if (i.getId() == item.getId() && livro.getEstoque() > i.getQuantidade()) {
						i.setQuantidade(item.getQuantidade() + i.getQuantidade());
						resultado.setMsg("Produto adicionado com sucesso");
						return "redirect:/public/carrinho";
					} else {
						resultado.setMsg("Estoque Insuficiente");
					}
				}
			}
		}
		model.addAttribute("resultado", resultado);
		return "redirect:/public/carrinho";
	}

	@RequestMapping(value = "/public/editarItem", method = RequestMethod.POST)
	public String editraItem(@ModelAttribute("carrinho") CarrinhoCompra carrinho, String operacao, Item item,
			Model model) {
		if (operacao.equals("ALTERAR QUANTIDADE"))
			operacao = "EDITAR";
		if (operacao.equals("REMOVER"))
			operacao = "EXCLUIR";
		Resultado resultado = new Resultado();
		Livro livro;
		for (int i = 0; i < carrinho.getItens().size(); i++) {
			livro = ((Livro) carrinho.getItens().get(i).getProduto());
			if (operacao.equals("EDITAR") && item.getId() == carrinho.getItens().get(i).getId()) {
				if (item.getQuantidade() > livro.getEstoque()) {
					resultado.setMsg("Erro na alteração da quantidade <strong>estoque insuficiente</strong>");

				} else {
					resultado.setMsg("quantidade alterada com sucesso");
					item.setProduto(carrinho.getItens().get(i).getProduto());
					carrinho.getItens().set(i, item);
				}
			} else if (operacao.equals("EXCLUIR") && item.getId() == carrinho.getItens().get(i).getId()) {
				carrinho.getItens().remove(i);
				resultado.setMsg("foi excluido com sucesso");
			}
			resultado.setEntidades(new ArrayList<EntidadeDominio>());
			resultado.getEntidades().add(livro);
		}
		model.addAttribute("resultado", resultado);
		return "redirect:/public/carrinho";
	}

	@RequestMapping("/public/carrinho")
	public String listarItensCarrinho(@ModelAttribute("carrinho") CarrinhoCompra carrinho, SessionStatus session) {

		return "ecommerce/carrinho";
	}

	@RequestMapping(value = "/public/carrinho", method = RequestMethod.GET)
	public String listarItensCarrinho(Model model, SessionStatus session) {
		if (session.isComplete()) {
			if (!model.containsAttribute("carrinho"))
				model.addAttribute("carrinho", new CarrinhoCompra());
		}
		return "ecommerce/carrinho";
	}

	@RequestMapping(value = "/public/finalizar")
	public String finalizarCompra(@ModelAttribute("carrinho") CarrinhoCompra carrinho, Model model,
			HttpServletRequest request) {
		if (request.getSession().getAttribute("cliente") == null)
			return "redirect:/login.xhtml?msg=faca+login";
		Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
		model.addAttribute("cliente", cliente);
		Double total = 0D;
		for (Item item : carrinho.getItens()) {
			Livro l = (Livro) item.getProduto();
			total += l.getValor() * item.getQuantidade();
		}
		ObjectMapper mapper = new ObjectMapper();
		model.addAttribute("total", total);
		try {
			model.addAttribute("produtos", mapper.writeValueAsString(carrinho.getItens()));
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
			model.addAttribute("produtos", "{}");
		}
		return "ecommerce/finalizar";
	}

	@ExceptionHandler(HttpSessionRequiredException.class)
	public RedirectView handlerSessionAtributes(Exception ex) {
		RedirectView rw = new RedirectView("/les-web/public/index");
		return rw;
	}

	@RequestMapping("/public/verifyCart")
	public String verifyCart(Model model) {
		if (!model.containsAttribute("carrinho"))
			model.addAttribute("carrinho", new CarrinhoCompra());
		if (model.containsAttribute("resultado"))
			model.addAttribute("resultado", new Resultado());
		return "redirect:/public/carrinho";
	}

	@RequestMapping(value = "/public/adicionarCartao", method = RequestMethod.GET)
	public String adicionarCartao(HttpServletRequest request) {
		if (request.getSession().getAttribute("cliente") == null)
			return "redirect:/login.xhtml?msg=faca+login";
		return "ecommerce/addcartao";
	}

	@RequestMapping(value = "/public/adicionarCartao", method = RequestMethod.POST)
	public String adicionarCartao(@DateTimeFormat(pattern = "dd/MM/yyyy") CartaoCredito cartao, String operacao,
			Model model, HttpServletRequest request) {
		if (cartao.getAddPerfil() != null) {
			ICommand command = commands.get(operacao);
			Resultado resultado = command.execute(cartao);
			if (resultado.getMsg() == null)
				resultado.setMsg("Salvo com sucesso");
			model.addAttribute("result", resultado);
			return "ecommerce/addcartao";
		}
		Resultado resultado = new Resultado();
		Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
		cliente.getCartao().add(cartao);
		request.getSession().setAttribute("cliente", cliente);
		model.addAttribute("result", resultado);
		return "ecommerce/addcartao";
	}

	@RequestMapping(value = "/public/adicionarEndereco", method = RequestMethod.GET)
	public String adicionarEndereco(HttpServletRequest request) {
		if (request.getSession().getAttribute("cliente") == null)
			return "redirect:/login.xhtml?msg=faca+login";
		return "ecommerce/addendereco";
	}

	@RequestMapping(value = "/public/adicionarEndereco", method = RequestMethod.POST)
	public String adicionarEndereco(Endereco endereco, String operacao, Boolean addPerfil, Model model,
			HttpServletRequest request) {
		if (addPerfil != null && addPerfil) {
			ICommand command = commands.get(operacao);
			Resultado resultado = command.execute(endereco);
			if (resultado.getMsg() == null)
				resultado.setMsg("Endereço salvo com sucesso");
			model.addAttribute("result", resultado);
		}
		Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
		cliente.getEndereco().add(endereco);
		return "ecommerce/addendereco";
	}

	@RequestMapping(value = "/public/verificarCupom")
	public void verificarCupom(CupomCompra cupom, HttpServletResponse response) throws IOException {
		ListaCupomsCompra cupons = new ListaCupomsCompra();
		cupom = cupons.getCupom(cupom.getCodigoCupom());
		if (cupom != null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
			String json = mapper.writeValueAsString(cupom);
			response.setStatus(200);
			response.getWriter().write(json);
		} else {
			response.setStatus(200);
			response.getWriter().write("{}");
		}
	}

	@RequestMapping(value = "public/pedidos")
	public String compras(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("cliente") == null)
			return "redirect:/login.xhtml?msg=faca+login";
		Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
		Compra compra = new Compra();
		compra.setCliente(cliente);
		ICommand command = commands.get("CONSULTAR");
		Resultado result = command.execute(compra);
		model.addAttribute("pedidos", result.getEntidades());
		return "ecommerce/pedidos";
	}

	@RequestMapping(value = "public/sair")
	public String sairCliente(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/login.xhtml";
	}

	@RequestMapping(value = "pedidos/listar")
	public String listarPedidos(Model model, HttpServletRequest request) {
		ICommand command = commands.get("CONSULTAR");
		Compra compra = new Compra();
		Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
		compra.setCliente(cliente);
		Resultado result = command.execute(compra);
		model.addAttribute("pedidos", result.getEntidades());
		return "adm/listarPedidos";
	}

	@RequestMapping(value = "pedidos/setstatus")
	public void setStatus(String compra, String operacao, HttpServletResponse response) throws Exception {
		ICommand command = commands.get(operacao);
		ObjectMapper mapper = new ObjectMapper();
		Compra entidade = mapper.readValue(compra, Compra.class);
		entidade.setStatusCompra(AprovarCartao.validarCompra());
		Resultado result = command.execute(entidade);
		response.setStatus(200);
		response.getWriter().write("{}");
	}

	@RequestMapping(value = "pedidos/visualizarPedidoCliente")
	public String getPedidoCliente(String compra, Model model, HttpServletRequest request) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Compra cp = mapper.readValue(compra, Compra.class);
		model.addAttribute("pedido", cp);
		return "adm/pedidocliente";
	}

	@RequestMapping(value = "pedidos/alterarpedido")
	public String setPedidoStatus(String compra, String operacao, String statusCompra, Model model) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Compra cp = mapper.readValue(compra, Compra.class);
		cp.setStatusCompra(StatusCompra.valueOf(statusCompra));
		ICommand command = commands.get(operacao);
		Resultado resultado = command.execute(cp);
		if (resultado.getMsg() == null)
			resultado.setMsg("Status alterado com sucesso");
		model.addAttribute("resultado", resultado);
		return "redirect:/pedidos/listar";
	}
	
	@RequestMapping(value="pedidos/reqtroca",method=RequestMethod.GET)
	public String requisaoTroca(Integer id,Model model,HttpServletRequest request) {
		if(id != null) {
			Compra compra = new Compra();
			compra.setId(id);
			Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
			compra.setCliente(cliente);
			ICommand command = commands.get("CONSULTAR");
			Resultado resultado = command.execute(compra);
			if(resultado.getMsg() == null) {
				model.addAttribute("compra", resultado.getEntidades().get(0));
			}
			model.addAttribute("resultado",resultado);
		}else {
			return "redirect:/les-web/public/pedidos";		
		}
		
		return "ecommerce/reqTroca";
	}
	
	@RequestMapping(value="pedido/trocar")
	public String fazerTroca(String compra,Model model,HttpServletRequest request) {
		if(compra != null) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				Compra c =  mapper.readValue(compra, Compra.class);
				Trocas troca = new Trocas();
				troca.setCompra(c);
				troca.setStatus(StatusCompra.Emtroca);
				ICommand command = commands.get("SALVAR");
				Resultado resultado = command.execute(troca);
				model.addAttribute("resultado", resultado);
				return "ecommerce/trocas";
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return "redirect:pedidos/solicitacoes";
	}
	
	@RequestMapping(value="pedidos/solicitacoes")
	public String solicitacoesTroca() {
		
		return "ecommerce/trocas";
	}
}
