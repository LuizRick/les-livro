package web.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.view.RedirectView;

import core.dfs.aplicacao.ListaCupomsCompra;
import core.dfs.aplicacao.Resultado;
import core.impl.dao.ClienteDAO;
import entities.cadastros.Cartao;
import entities.cadastros.CartaoCredito;
import entities.cadastros.Cliente;
import entities.cadastros.CupomCompra;
import entities.cadastros.Endereco;
import entities.produto.IProduto;
import entities.produto.Livro;
import entities.venda.CarrinhoCompra;
import entities.venda.Compra;
import web.controle.web.command.ICommand;
import web.controle.web.command.impl.AlterarClienteCommand;
import web.controle.web.command.impl.ConsultarClienteCommand;
import web.controle.web.command.impl.ConsultarCommand;
import web.controle.web.command.impl.SalvarClienteCommand;
import web.controle.web.command.impl.SalvarCommand;
import entities.venda.Item;
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

	@RequestMapping("/loginCliente")
	public String loginCliente(Cliente cliente, HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		ClienteDAO dao = new ClienteDAO();
		cliente = (Cliente) dao.consultar(cliente).get(0);
		if (cliente != null) {
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
		for (Item i : carrinho.getItens()) {
			if (i.getId() == item.getId())
				return "redirect:/public/carrinho";
		}
		Resultado resultado = commands.get("CONSULTARLIVRO").execute(livro);
		if (resultado.getEntidades() != null) {
			livro = (Livro) resultado.getEntidades().get(0);
			item.setProduto(livro);
			if (livro.getEstoque() < item.getQuantidade())
				resultado.setMsg("estoque insuficiente");
			else {
				resultado.setMsg("item adicionado com sucesso");
				carrinho.getItens().add(item);
			}
		}
		model.addAttribute("resultado", resultado);
		return "redirect:/public/carrinho";
	}

	@RequestMapping(value = "/public/editarItem", method = RequestMethod.POST)
	public String editraItem(@ModelAttribute("carrinho") CarrinhoCompra carrinho, String operacao, Item item,
			Model model) {
		Resultado resultado = new Resultado();
		for (int i = 0; i < carrinho.getItens().size(); i++) {
			if (operacao.equals("EDITAR") && item.getId() == carrinho.getItens().get(i).getId()) {
				if (item.getQuantidade() > ((Livro) carrinho.getItens().get(i).getProduto()).getEstoque()) {
					resultado.setMsg("Erro na edição estoque insuficiente");

				} else {
					resultado.setMsg("item editado com sucesso");
					item.setProduto(carrinho.getItens().get(i).getProduto());
					carrinho.getItens().set(i, item);
				}
			} else if (operacao.equals("EXCLUIR") && item.getId() == carrinho.getItens().get(i).getId()) {
				carrinho.getItens().remove(i);
				resultado.setMsg("item excluido com sucesso");
			}
			model.addAttribute("resultado", resultado);
		}
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

	@RequestMapping("/public/finalizar")
	public String finalizarCompra(@ModelAttribute("carrinho") CarrinhoCompra carrinho ,Model model,
			HttpServletRequest request) {
		if (request.getSession().getAttribute("cliente") == null)
			return "redirect:/login.xhtml?msg=faca+login";
		Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
		model.addAttribute("cliente", cliente);
		Double total = 0D;
		for (Item item : carrinho.getItens()) {
			Livro l = (Livro) item.getProduto();
			total += l.getValor();
		}
		model.addAttribute("total", total);
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
		}
		return "ecommerce/addcartao";
	}

	@RequestMapping(value = "/public/adicionarEndereco", method = RequestMethod.GET)
	public String adicionarEndereco(HttpServletRequest request) {
		if (request.getSession().getAttribute("cliente") == null)
			return "redirect:/login.xhtml?msg=faca+login";
		return "ecommerce/addendereco";
	}

	@RequestMapping(value = "/public/adicionarEndereco", method = RequestMethod.POST)
	public String adicionarEndereco(Endereco endereco, String operacao, Boolean addPerfil, Model model) {
		if (addPerfil) {
			ICommand command = commands.get(operacao);
			Resultado resultado = command.execute(endereco);
			if (resultado.getMsg() == null)
				resultado.setMsg("Endereço salvo com sucesso");
			model.addAttribute("result", resultado);
		}
		return "ecommerce/addendereco";
	}
	
	@RequestMapping(value="/public/verificarCupom")
	public void verificarCupom(CupomCompra cupom,HttpServletResponse response) throws IOException {
		ListaCupomsCompra cupons = new ListaCupomsCompra();
		cupom = cupons.getCupom(cupom.getCodigoCupom());
		if(cupom != null) {
			JSONObject object = new JSONObject(cupom);
			response.setStatus(200);
			response.getWriter().write(object.toString());
		}else {
			response.setStatus(200);
			response.getWriter().write("{}");
		}
	}
	
	@RequestMapping(value="/public/setcompra",method=RequestMethod.POST)
	public void setCompra(Compra venda,String operacao,Model model) {
		ICommand commnad = commands.get(operacao);
		Resultado result = commnad.execute(venda);
	}
}
