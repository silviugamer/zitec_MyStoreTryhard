package zitecMyStore_practice;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class TestClass {
	// verifies the title of the page;
	public void verifyTitle() {
		WebDriver driver = paramholder.instantiateDriver();
		String expectedTitle = "My Store";
		String actualTitle = null;
		actualTitle = driver.getTitle();
		if (expectedTitle.contentEquals(actualTitle)) {
			System.out.println("PASS - User reached landing page");
		} else {
			System.out.println("FAIL - Test of title failed");
		}
		driver.quit();
	}

	// verifies header login button
	public void verifyLoginBtn() {
		WebDriver driver = paramholder.instantiateDriver();
		String login = "http://automationpractice.com/index.php?controller=authentication&back=my-account";
		WebElement btnLogin = paramholder.getElementByCssSelector(driver, ".login");
		if (btnLogin != null) {
			btnLogin.click();
			if (driver.getCurrentUrl().equals(login)) {
				System.out.println("PASS - Login presence passed");
			} else {
				System.out.println("FAIL - Login presence failed");
			}
		} else {
			System.out.println("FAIL - Login button doesn't exist");
		}
		driver.quit();
	}

	// verifies header contact button
	public void verifyContactBtn() {
		WebDriver driver = paramholder.instantiateDriver();
		String contact = "http://automationpractice.com/index.php?controller=contact";
		WebElement btnContact = paramholder.getElementByCssSelector(driver, "#contact-link");
		if (btnContact != null) {
			btnContact.click();
			if (driver.getCurrentUrl().equals(contact)) {
				System.out.println("PASS - Contact presence passed");
			} else {
				System.out.println("FAIL - Contact presence failed");
			}
		} else {
			System.out.println("FAIL - Contact button doesn't exist");
		}
		driver.quit();
	}

	// verifies existence of phone number
	public void verifyPhoneNumber() {
		WebDriver driver = paramholder.instantiateDriver();
		;
		String phoneText = "0123-456-789";
		WebElement phone = paramholder.getElementByCssSelector(driver, ".shop-phone");
		if (phone != null && phone.findElement(By.tagName("strong")).getText().equals(phoneText)) {
			System.out.println("PASS - Phone number found");
		} else {
			System.out.println("FAIL - Phone number not found");
		}
		driver.quit();
	}

	// verifies catalog of filters on every category
	public void verifyCatalog() {
		WebDriver driver = paramholder.instantiateDriver();
		paramholder.getElementByLinkText(driver, "Women").click();
		System.out.println("Women category is present.");
		paramholder.getElementByLinkText(driver, "Dresses").click();
		System.out.println("Dresses category is present.");
		paramholder.getElementByCssSelector(driver, "#block_top_menu > ul > li > a").click();
		System.out.println("T-shirts category is present");
		WebElement catalog = paramholder.getElementByCssSelector(driver, "#layered_block_left");
		List<WebElement> listOfFilterCategories = catalog.findElements(By.cssSelector(".layered_filter"));
		if (listOfFilterCategories.size() == 9
				&& paramholder.getElementByCssSelector(driver, "#layered_form .layered_price") != null) {
			System.out.println("PASS - Catalog of filters is present");
		} else {
			System.out.println("FAIL - Catalog of filters is not present");
		}
		driver.quit();
	}

	public void buyFirstProduct() throws InterruptedException {
		WebDriver driver = paramholder.instantiateDriver();
		WebElement firstProduct = paramholder.getElementByCssSelector(driver,
				"#homefeatured > li.ajax_block_product.col-xs-12.col-sm-4.col-md-3.first-in-line.first-item-of-tablet-line.first-item-of-mobile-line .product-name");
		String priceOfFirstProduct = paramholder.getElementByCssSelector(driver,
				"#homefeatured > li.ajax_block_product.col-xs-12.col-sm-4.col-md-3.first-in-line.first-item-of-tablet-line.first-item-of-mobile-line .right-block .price")
				.getText();
		String nameOfFirstProduct = firstProduct.getText();
		firstProduct.click();
		if (paramholder.getElementByCssSelector(driver, "#center_column h1").getText().equals(nameOfFirstProduct)) {
			System.out.println("User was directed to the product's page.");
		} else {
			System.out.println("FAIL - User was not directed to the product's page.");
			driver.quit();
		}
		String priceOnProductPage = paramholder.getElementByCssSelector(driver, ".our_price_display").getText();
		if (priceOfFirstProduct.equals(priceOnProductPage)) {
			System.out.println("Price is the same on main page with the price on product page.");
		} else {
			System.out.println("FAIL - Price is NOT the same on main page with the price on product page.");
			driver.quit();
		}
		// getting product ID
		String firstProductId = paramholder.getElementByCssSelector(driver, "#product_page_product_id")
				.getAttribute("value").toString();
		String secondProductId = paramholder.getElementByCssSelector(driver, "#idCombination").getAttribute("value")
				.toString();
		String productId = firstProductId + secondProductId;
		paramholder.getElementByCssSelector(driver, "#add_to_cart button").click();
		Thread.sleep(3000);
		WebElement proceedToCheckout = paramholder.getElementByCssSelector(driver, "#layer_cart");
		if (proceedToCheckout.isDisplayed() && paramholder.getElementByCssSelector(driver, "#layer_cart").getText()
				.contains("Product successfully added to your shopping cart")) {
			System.out.println("User was informed his product was added to cart.");
		} else {
			System.out.println("FAIL - User was NOT informed his product was added to cart.");
			driver.quit();
		}
		proceedToCheckout.findElement(By.cssSelector("div.button-container > a")).click();
		// verifying products in cart
		Boolean isProduct = false;
		List<WebElement> listOfProductsInCart = driver.findElements(By.cssSelector("tbody > tr"));
		for (WebElement product : listOfProductsInCart) {
			if (product.getAttribute("id").toString().replaceAll("[^\\d]", "").replace("00", "").equals(productId)
					&& product.getText().contains(priceOfFirstProduct)) {
				System.out.println("Cart contains the chosen product and the correct price.");
				isProduct = true;
			}
		}
		if (!isProduct) {
			System.out.println("Cart doesn't contain the chosen productor and / or the correct price.");
			driver.quit();
		}
		paramholder.getElementByCssSelector(driver, ".btn-default.standard-checkout.button-medium ").click();
		if (paramholder.getElementByCssSelector(driver, "#authentication") != null) {
			System.out.println("User was directed in the login page.");
		} else {
			System.out.println("FAIL - User was NOT directed in the login page.");
			driver.quit();
		}
		login(driver);
		addressShipPayment(driver);
		driver.quit();
	}

	public void quickViewOrder() throws InterruptedException {
		WebDriver driver = paramholder.instantiateDriver();
		Actions actions = new Actions(driver);
		WebElement secondProduct = paramholder.getElementByCssSelector(driver, "#homefeatured > li:nth-child(2)");
		String nameOfSecondProduct = secondProduct.getText();
		String priceOfSecondProduct = paramholder.getElementByCssSelector(driver,
				"#homefeatured > li.ajax_block_product.col-xs-12.col-sm-4.col-md-3.last-item-of-mobile-line .right-block .price")
				.getText();
		actions.moveToElement(secondProduct).moveToElement(secondProduct.findElement(By.cssSelector(".quick-view")))
				.click().build().perform();
		driver.switchTo().frame(paramholder.getElementByCssSelector(driver, ".fancybox-iframe"));
		// getting product ID
		String firstProductId = paramholder.getElementByCssSelector(driver, "#product_page_product_id")
				.getAttribute("value").toString();
		String secondProductId = paramholder.getElementByCssSelector(driver, "#idCombination").getAttribute("value")
				.toString();
		String productId = firstProductId + secondProductId;

		String priceOnProductPage = paramholder.getElementByCssSelector(driver, "#buy_block .price").getText();
		if (nameOfSecondProduct
				.contains(paramholder.getElementByCssSelector(driver, "#product .pb-center-column h1").getText())) {
			System.out.println("User was directed to the product's popup page.");
		} else {
			System.out.println("FAIL - User was not directed to the product's popup page.");
			driver.quit();
		}
		if (priceOfSecondProduct.equals(priceOnProductPage)) {
			System.out.println("Price is the same on main page with the price on product's popup page.");
		} else {
			System.out.println("FAIL - Price is NOT the same on main page with the price on product's popup page.");
			driver.quit();
		}

		paramholder.getElementByCssSelector(driver, "#add_to_cart > button").click();

		// proceed to checkout
		Thread.sleep(3000);
		driver.switchTo().defaultContent();
		WebElement proceedToCheckout = paramholder.getElementByCssSelector(driver, "#layer_cart");

		if (proceedToCheckout.isDisplayed()) {
			System.out.println("User was informed his product was added to cart.");
		} else {
			System.out.println("FAIL - User was NOT informed his product was added to cart.");
			driver.quit();
		}
		proceedToCheckout.findElement(By.cssSelector("div.button-container > a")).click();

		// verifying products in cart
		Boolean isProduct = false;
		List<WebElement> listOfProductsInCart = driver.findElements(By.cssSelector("tbody > tr"));
		for (WebElement product : listOfProductsInCart) {
			if (product.getAttribute("id").toString().replaceAll("[^\\d]", "").replace("00", "").equals(productId)
					&& product.getText().contains(priceOfSecondProduct)) {
				System.out.println("Cart contains the chosen product and the correct price.");
				isProduct = true;
			}
		}
		if (!isProduct) {
			System.out.println("Cart doesn't contain the chosen productor and / or the correct price.");
			driver.quit();
		}
		paramholder.getElementByCssSelector(driver, ".btn-default.standard-checkout.button-medium ").click();
		if (paramholder.getElementByCssSelector(driver, "#authentication") != null) {
			System.out.println("User was directed in the login page.");
		} else {
			System.out.println("FAIL - User was NOT directed in the login page.");
			driver.quit();
		}
		login(driver);
		addressShipPayment(driver);
		driver.quit();
	}

	private void login(WebDriver driver) {
		paramholder.getElementByCssSelector(driver, "#email").sendKeys("youremail@email.com");
		paramholder.getElementByCssSelector(driver, "#passwd").sendKeys("password123!");
		paramholder.getElementByCssSelector(driver, "#SubmitLogin").click();
	}

	private void addressShipPayment(WebDriver driver) {
		if (paramholder.getElementByCssSelector(driver, "#center_column > h1").getText().equals("ADDRESSES")) {
			System.out.println("User was directed in the Addresses page.");
		} else {
			System.out.println("FAIL - User was NOT directed in the Addresses page.");
			driver.quit();
		}
		paramholder.getElementByCssSelector(driver, "#center_column button").click();
		if (paramholder.getElementByCssSelector(driver, "#carrier_area") != null) {
			System.out.println("User was directed in the Shipping page.");
		} else {
			System.out.println("FAIL - User was NOT directed in the Shipping page.");
			driver.quit();
		}
		paramholder.getElementByCssSelector(driver, "#uniform-cgv").click();
		paramholder.getElementByCssSelector(driver, "#form button").click();
		paramholder.getElementByCssSelector(driver, "#HOOK_PAYMENT .bankwire").click();
		paramholder.getElementByCssSelector(driver, "#cart_navigation button").click();
		if (paramholder.getElementByCssSelector(driver, ".cheque-indent").getText()
				.equals("Your order on My Store is complete.")) {
			System.out.println("PASS - Order complete");
		} else {
			System.out.println("FAIL - User was NOT able to complete the order.");
		}
	}
}
