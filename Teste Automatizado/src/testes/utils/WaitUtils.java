package testes.utils;

import java.sql.Driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {
	
	public static boolean aguardaAteQueValueMude(final WebElement element,WebDriver driver)  
	{
	    WebDriverWait wait = new WebDriverWait(driver, 10);

	    return wait.until(new ExpectedCondition<Boolean>() {
	        public Boolean apply(WebDriver driver) {
	            String value = element.getAttribute("value");

	            if(!value.equals("")) {
	                return true;
	            }

	            return false;
	        }
	    });
	}
	
	
	public static void valueNotEmpty(WebElement element,WebDriver driver,IExecute lamb) {
		WebDriverWait wait = new WebDriverWait(driver, 10);

	    wait.until(new ExpectedCondition<Boolean>() {
	        public Boolean apply(WebDriver driver) {
	            String value = element.getText();

	            if(!value.equals("")) {
	            	lamb.execute();
	                return true;
	            }

	            return false;
	        }
	    });
	}
}
