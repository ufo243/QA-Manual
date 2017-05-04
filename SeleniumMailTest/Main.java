import java.util.List;

import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Main {
  private WebDriver driver;
  private String baseUrl;
  private String searchRequest;


  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "C:\\JAVA\\selenium\\chromedriver.exe" );
    driver = new ChromeDriver(); // Chrome
    //System.setProperty("webdriver.gecko.driver", "C:\\JAVA\\selenium\\geckodriver.exe" );
    //driver = new FirefoxDriver(); // FireFox

    searchRequest = String.valueOf(System.currentTimeMillis());
    baseUrl = "http://webmail.meta.ua";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

  }

  @Test
  public void searchMail() throws Exception {
    // Login and Send Mail
    driver.get(baseUrl);
    driver.findElement(By.id("login-field")).clear();
    driver.findElement(By.id("login-field")).sendKeys("test243@meta.ua");
    driver.findElement(By.id("pass-field")).clear();
    driver.findElement(By.id("pass-field")).sendKeys("asdffdsA1");
    driver.findElement(By.id("loginbtnua")).click();
    driver.findElement(By.linkText("Почта")).click();
    driver.findElement(By.id("id_send_email")).click();
    driver.findElement(By.id("send_to")).clear();
    driver.findElement(By.id("send_to")).sendKeys("test243@meta.ua");
    driver.findElement(By.id("subject")).clear();
    driver.findElement(By.id("subject")).sendKeys(searchRequest);
    driver.findElement(By.id("body")).clear();
    driver.findElement(By.id("body")).sendKeys("body");
    driver.findElement(By.className("panel_submit")).click();
    driver.findElement(By.id("id_logout")).click();
    System.out.println("Send letter with Subject: " + searchRequest);

    //TimeUnit.SECONDS.sleep(1);

    // Login and Check Mail
    driver.get(baseUrl);
    driver.findElement(By.id("login-field")).clear();
    driver.findElement(By.id("login-field")).sendKeys("test243@meta.ua");
    driver.findElement(By.id("pass-field")).clear();
    driver.findElement(By.id("pass-field")).sendKeys("asdffdsA1");
    driver.findElement(By.id("loginbtnua")).click();
    driver.findElement(By.linkText("Почта")).click();
    driver.findElement(By.id("simple_search")).clear();
    driver.findElement(By.id("simple_search")).sendKeys(searchRequest);
    driver.findElement(By.id("search_button_small")).click();
    List<WebElement> searchData = driver.findElements(By.id("hslice"));

    int count = 0;
    for (WebElement element : searchData) {
      if (element.getText().contains(searchRequest)){
        count++;
      }
    }

    System.out.println("Found " + count + " letter(s) with Subject: " + searchRequest);
    driver.findElement(By.id("id_logout")).click();

    assertThat(count, is(1));
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
  }

}
