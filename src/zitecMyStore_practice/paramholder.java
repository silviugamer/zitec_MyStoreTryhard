package zitecMyStore_practice;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class paramholder {
	private static int timeoutInSeconds = 10;
	private static String baseUrl = "http://automationpractice.com/index.php";

	public static WebElement getElementByCssSelector(WebDriver driver, String cssSelector) {
		WebElement element = null;
		try {
			@SuppressWarnings("deprecation")
			WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
			element = driver.findElement(By.cssSelector(cssSelector));
		} catch (Exception e) {
			System.out.println("Cannot find element with cssSelector: " + cssSelector);
			driver.quit();
		}
		return element;
	}
	
	public static WebElement getElementByLinkText(WebDriver driver, String LinkText) {
		WebElement element = null;
		try {
			@SuppressWarnings("deprecation")
			WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(LinkText)));
			element = driver.findElement(By.linkText(LinkText));
			//jsShowElement(driver, element);
		} catch (Exception e) {
			System.out.println("Cannot find element with cssSelector: " + LinkText);
			driver.quit();
		}
		return element;
	}
	
	public static WebDriver instantiateDriver() {
		WebDriver driver = new ChromeDriver();
		driver.get(baseUrl);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		return driver;
	}
}
