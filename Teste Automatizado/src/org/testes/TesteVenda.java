package org.testes;

import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import core.impl.dao.LivroDAO;
import core.util.CreditCardNumberGenerator;
import dominio.EntidadeDominio;
import entities.produto.Livro;

public class TesteVenda extends Test{
	
	public void testAddCarrinho(String operacao) {
		try {
			fazerLogin();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			
		}
	}
	
	public void testAddNovoEndereco(String operacao) {
		try {
			fazerLogin();
			driver.navigate().to("http://localhost:8080/les-web/public/adicionarEndereco");
			driver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
			WebElement elt = driver.findElement(By.name("logradouro"));
			elt.sendKeys("Teste " + Calendar.getInstance().getTime().toString());
			elt = driver.findElement(By.name("bairro"));
			elt.sendKeys("Bairro " + Calendar.getInstance().getTime().toString());
			elt = driver.findElement(By.name("cep"));
			elt.sendKeys("08570661");
			elt = driver.findElement(By.name("numero"));
			elt.sendKeys("33" + new Random().nextInt(199));
			elt = driver.findElement(By.name("complemento"));
			elt.sendKeys("complemento teste no selenium");
			elt = driver.findElement(By.name("nome"));
			elt.sendKeys("nome" + Calendar.getInstance().getTime().toString());
			elt = driver.findElement(By.name("tipoResidencia"));
			elt.sendKeys("casa");
			elt = driver.findElement(By.name("tipoLogradouro"));
			elt.sendKeys("logradouro" + Calendar.getInstance().getTime().toString());
			elt = driver.findElement(By.name("cidade.nome"));
			elt.sendKeys("mogi das cruzes");
			elt = driver.findElement(By.name("cidade.estado.nome"));
			elt.sendKeys("sp");
			elt = driver.findElement(By.name("cidade.estado.pais.nome"));
			elt.sendKeys("brasil");
			if(new Random().nextDouble() * 100 > 20 )
				driver.findElement(By.name("addPerfil")).click();
			driver.findElement(By.name("operacao")).click();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void testAddNovoCartao(String operacao) {
		try {
			CreditCardNumberGenerator ccg = new CreditCardNumberGenerator();
			fazerLogin();
			driver.navigate().to("http://localhost:8080/les-web/public/adicionarCartao");
			driver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
			WebElement elt = driver.findElement(By.name("titular"));
			elt.sendKeys("Luiz - " +  new Random().nextInt(122));
			elt = driver.findElement(By.name("numero"));
			elt.sendKeys(ccg.generate("3056", 19));
			elt = driver.findElement(By.name("bandeira"));
			elt.sendKeys("Diner's CLub/Carte Blanche");
			elt = driver.findElement(By.name("codigo"));
			elt.sendKeys(new Random().nextInt(999)+"");
			elt = driver.findElement(By.name("validade"));
			elt.sendKeys("20/12/2020");
			elt = driver.findElement(By.name("addPerfil"));
			elt.click();
			driver.findElement(By.name("operacao")).click();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void testFazerCompra1(String operacao) {
		try {
			fazerLogin();
			driver.navigate().to("http://localhost:8080/les-web/public/index");
			LivroDAO dao = new LivroDAO();
			@SuppressWarnings("unchecked")
			List<Livro> lst = (List<Livro>)(List<?>) dao.consultar(null);
			int livro = lst.get(new Random().nextInt(lst.size())).getId();
			WebElement elt = driver.findElement(By.id(livro+""));
			elt.submit();
			driver.navigate().to("http://localhost:8080/les-web/public/finalizar");
			driver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
			elt = driver.findElement(By.name("frete.endereco.id"));
			elt.click();
			elt = driver.findElement(By.name("formasPagamento.id"));
			elt.click();
			elt = driver.findElement(By.name("formasPagamento.valor"));
			for(Livro l : lst) {
				if(l.getId() == livro)
				{
					elt.clear();
					elt.sendKeys("" + l.getValor());
				}
			}
			elt = driver.findElement(By.name("operacao"));
			elt.click();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void testFinalizarCompra2(String operacao) {
		try {
			fazerLogin();
			driver.navigate().to("http://localhost:8080/les-web/public/index");
			LivroDAO dao = new LivroDAO();
			@SuppressWarnings("unchecked")
			List<Livro> lst = (List<Livro>)(List<?>) dao.consultar(null);
			int livro = lst.get(new Random().nextInt(lst.size())).getId();
			WebElement elt = driver.findElement(By.id(livro+""));
			elt.submit();
			driver.navigate().to("http://localhost:8080/les-web/public/finalizar");
			driver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
			elt = driver.findElement(By.name("frete.endereco.id"));
			elt.click();
			elt = driver.findElement(By.name("formasPagamento.id"));
			elt.click();
			elt = driver.findElement(By.name("formasPagamento.valor"));
			for(Livro l : lst) {
				if(l.getId() == livro)
				{
					elt.clear();
					elt.sendKeys("" + l.getValor());
				}
			}
			elt = driver.findElement(By.name("codigoCupom"));
			elt.sendKeys("025");
			elt = driver.findElement(By.name("btnAddCupom"));
			elt.click();
			elt = driver.findElement(By.name("operacao"));
			elt.click();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void fazerLogin() throws Exception {
		openDriver();
		String window = driver.getWindowHandle();
		driver.switchTo().window(window);
		driver.navigate().to("localhost:8080/les-web/");
		WebElement query = driver.findElement(By.name("email"));
		query.sendKeys("luizmon.silva@gmail.com");
		query = driver.findElement(By.name("senha"));
		query.sendKeys("123456");
		driver.findElement(By.name("operacao")).click();
	}
}
