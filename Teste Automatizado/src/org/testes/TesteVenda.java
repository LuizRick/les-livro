package org.testes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TesteVenda extends Test{
	
	public void addCarrinho(String operacao) {
		driver.navigate().to("localhost:8080/les-web/");
		WebElement query = driver.findElement(By.name("email"));
		query.sendKeys("luizmon.silva@gmail.com");
		query = driver.findElement(By.name("senha"));
		query.sendKeys("123456");
		driver.findElement(By.name("operacao")).click();
	}
}
