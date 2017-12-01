package org.testes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Test {

	protected WebDriver driver;
	public Test() {
		System.setProperty("webdriver.chrome.driver", "C:\\Applications\\webdrivers\\chromedriver.exe");
		driver = new ChromeDriver();
	}

}
