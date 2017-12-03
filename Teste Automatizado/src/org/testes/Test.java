package org.testes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Test  {

	protected WebDriver driver;
	public Test() {
		
	}
	
	public void openDriver() {
		System.setProperty("webdriver.chrome.driver", "C:\\Applications\\webdrivers\\chromedriver.exe");
		driver = new ChromeDriver();
	}
}
