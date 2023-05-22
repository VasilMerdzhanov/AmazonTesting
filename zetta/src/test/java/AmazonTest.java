import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AmazonTest {
    WebDriver driver;
    List<WebElement> products;

    @BeforeTest
    public void beforeTest() {
        // Set ChromeDriver path
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Mythos\\Desktop\\Drivers\\chromedriver.exe"); //Make sure to replace "path/to/chromedriver" with the actual path to your ChromeDriver executable.
        //WebDriverManager.chromedriver().setup(); // Another way to set ChromeDriver
        // Create a new instance of the Chrome driver
        driver = new ChromeDriver();
        //Maximize the window
        driver.manage().window().maximize();
        //Add implicitlyWait
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    // Step 1: Enter amazon.com and check the homepage
    @Test(priority = 1)
    public void checkHomepage(){
        //WebDriver - Launch up https://www.amazon.com
        driver.get("https://www.amazon.com");

        System.out.println(driver.getCurrentUrl());
        System.out.println(driver.getTitle());

        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://www.amazon.com/";
        String actualTitle = driver.getTitle();
        String expectedTitle = "Amazon.com. Spend less. Smile more.";

        //Check the output by Url
        Assert.assertEquals(actualUrl,expectedUrl);
        //Check the output by Title
        Assert.assertEquals(actualTitle,expectedTitle);
    }

    // Step 2: Search for "laptop"
    @Test(priority = 2)
    public void searchForLaptopWord(){
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys("laptop");
        searchBox.submit();
        WebElement laptopResults = driver.findElement(By.className("a-color-state"));
        String actualResultsPage = laptopResults.getText();
        String expectedPageResults = "\"laptop\"";

        //Check the search results by "laptop" word
        Assert.assertEquals(actualResultsPage, expectedPageResults);
        System.out.println("Performed search for 'laptop'");
    }

    // Step 3: Add non-discounted products in stock on the first page to the cart
    @Test(priority = 3)
    public void addNonDiscountedProductsOnTheFirstPageToTheCart(){
        driver.get("https://www.amazon.com/s?k=laptop&ref=nb_sb_noss");
        products = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']"));

        for (int i = 0; i < products.size(); i++) {
            WebElement product = products.get(i);

           // sleep(3);

            // Check if the product has a discount   ??????
            List<WebElement> discountElements = product.findElements(By.xpath(".//span[contains(@class, 'a-price-')]//span[contains(@class, 'a-offscreen')]"));
            if (!discountElements.isEmpty()) {
                continue; // Skip products with a discount
            }

            //sleep(5);

            WebElement productLink = product.findElement(By.tagName("a"));
            String productUrl = productLink.getAttribute("href");

            // Open the product page
            driver.get(productUrl);

            //sleep(2);

            // Add the product to the cart
            WebElement addToCartButton = driver.findElement(By.id("add-to-cart-button"));
            addToCartButton.click();
            sleep(5);
            System.out.println("Added product to the cart");

            // Go back to the search results page
            driver.navigate().back();

            // Re-fetch the product list as the page might have refreshed
             products = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']"));
        }
    }

    // Step 4: Go to cart and check if the products are correct
    @Test(priority = 4)
    public void checkIfTheProductsAreCorrect(){
        WebElement cartButton = driver.findElement(By.id("nav-cart"));
        cartButton.click();
        System.out.println("Opened the cart");

        List<WebElement> cartProductPrices = driver.findElements(By.xpath("//span[@class='a-price-whole']"));
        boolean productsMatch = true;

        for (WebElement cartProductPrice : cartProductPrices) {
            String cartPrice = cartProductPrice.getText();
            boolean productFound = false;

            for (WebElement product : products) {
                WebElement priceElement = product.findElement(By.xpath(".//span[contains(@class,'a-price-whole')]"));
                String productPrice = priceElement.getText();

                if (productPrice.equals(cartPrice)) {
                    productFound = true;
                    break;
                }
                sleep(2);
            }

            if (!productFound) {
                productsMatch = false;
                break;
            }
        }

       // sleep(4);

        if (productsMatch) {
            System.out.println("Products in the cart are correct");
        } else {
            System.out.println("Products in the cart are incorrect");
        }
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }

    private void sleep(int i) {

        try {
            Thread.sleep(i * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
