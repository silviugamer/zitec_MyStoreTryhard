package zitecMyStore_practice;

public class MyClass {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		TestClass test = new TestClass();
		// tests
//		test.verifyTitle();
//		test.verifyLoginBtn();
//		test.verifyContactBtn();
//		test.verifyPhoneNumber();
//		test.verifyCatalog();
		test.buyFirstProduct();
//		test.quickViewOrder();
	}
}
