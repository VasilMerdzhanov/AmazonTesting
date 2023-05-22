import java.awt.AWTException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class AmazonCrawler {

    static WebDriver driver;

    public static void main(String[] args) throws InterruptedException, AWTException {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Mythos\\Desktop\\Drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.amazon.com/");

        Thread.sleep(3000);
        driver.findElement(By.className("hm-icon-label")).click();
        Thread.sleep(3000);

        List < WebElement > links = driver.findElements(By.tagName("a")); // List of the Web Elements that has the "a" tag,
        //which is in this case helps up to find the links

        String idForTxtFile = new SimpleDateFormat("dd.MM.yyyy_HH.mm.ss").format(new Date()); //create a stamp for the time being
        //combine the stamp and file name-path

        String path_name_file = System.getProperty("user.dir") + "\\" + idForTxtFile + "_results.txt" ;
        System.out.println(path_name_file+" is created!");


        for (int i = 0; i < links.size(); i++) { //for loop to examine each link, each instance
            WebElement ele = links.get(i); // will use verifyLinkActive method separately
            String url = ele.getAttribute("href");
            verifyLinkActive(url, path_name_file);
        }
    }

    //Method to verify the active link
    public static void verifyLinkActive(String linkUrl, String file) {

        try {
            URL url = new URL(linkUrl);

            HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();

            httpURLConnect.setConnectTimeout(3000);

            httpURLConnect.connect();

            if (httpURLConnect.getResponseCode() == 200) {

                System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage());
                String output = linkUrl + " - " + httpURLConnect.getResponseMessage();
                FileWriter writer = new FileWriter(file, true);
                BufferedWriter br = new BufferedWriter(writer);
                br.write("\r\n"); // write new line
                br.write(output);
                br.close();

            }
            if (httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
                System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage() + " - " + HttpURLConnection.HTTP_NOT_FOUND);
                String output = linkUrl + " - " + httpURLConnect.getResponseMessage() + " - " + HttpURLConnection.HTTP_NOT_FOUND;
                FileWriter writer = new FileWriter(file, true);
                BufferedWriter br = new BufferedWriter(writer);
                br.write("\r\n"); // write new line
                br.write(output);
                br.close();
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}