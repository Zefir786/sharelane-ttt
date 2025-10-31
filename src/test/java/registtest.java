import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import java.time.Duration;


public class registtest
{
    WebDriver driver;

    @BeforeMethod
    public void Start()
    {
        driver = new ChromeDriver();
        driver.get("https://sharelane.com/cgi-bin/register.py");
    }
    @Test
    public void checkPositiveLoginTest() {
        driver.findElement(By.name("zip_code")).sendKeys("12345");
        driver.findElement(By.cssSelector("[value='Continue']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("first_name")));
        driver.findElement(By.name("first_name")).sendKeys("username");
        driver.findElement(By.name("last_name")).sendKeys("lastname");
        String uniqueEmail = "test@example.com";
        driver.findElement(By.name("email")).sendKeys(uniqueEmail);
        String password = "jhvtfgtg55";
        driver.findElement(By.name("password1")).sendKeys(password);
        driver.findElement(By.name("password2")).sendKeys(password);
        driver.findElement(By.cssSelector("[value='Register']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/center/table/tbody/tr[6]/td/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/b")));
        String email;
        email = driver.findElement(By.xpath("/html/body/center/table/tbody/tr[6]/td/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/b")).getText();

        driver.findElement(By.xpath("/html/body/center/table/tbody/tr[1]/td/table/tbody/tr/td[1]/a")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[value='Login']")));
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys("1111");
        driver.findElement(By.cssSelector("[value='Login']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/center/table/tbody/tr[1]/td/table/tbody/tr/td[2]/span")));
        boolean elementOnPage;
        if (driver.findElement(By.xpath("/html/body/center/table/tbody/tr[1]/td/table/tbody/tr/td[2]/span")).isDisplayed())
            elementOnPage = true;
        else elementOnPage = false;
        Assert.assertTrue(elementOnPage, "норм");
    }

    @Test
    public void checkNegativeLoginTest() {
        driver.findElement(By.name("zip_code")).sendKeys("12345");
        driver.findElement(By.cssSelector("[value='Continue']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("first_name")));
        driver.findElement(By.name("first_name")).sendKeys("username");
        driver.findElement(By.name("last_name")).sendKeys("lastname");
        String uniqueEmail = "test@example.com";
        driver.findElement(By.name("email")).sendKeys(uniqueEmail);
        String password = "password1";
        driver.findElement(By.name("password1")).sendKeys(password);
        driver.findElement(By.name("password2")).sendKeys(password);
        driver.findElement(By.cssSelector("[value='Register']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/center/table/tbody/tr[6]/td/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/b")));
        String email;
        email = driver.findElement(By.xpath("/html/body/center/table/tbody/tr[6]/td/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/b")).getText();
        driver.findElement(By.xpath("/html/body/center/table/tbody/tr[1]/td/table/tbody/tr/td[1]/a")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[value='Login']")));
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.cssSelector("[value='Login']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/center/table/tbody/tr[4]/td/span")));
        String actual = driver.findElement(By.className("error_message")).getText();
        Assert.assertEquals(actual, "Oops, error. Email and/or password don't match our records", "Сообщение об ошибке не совпадает");
    }

    @AfterMethod(alwaysRun = true)
    public void quit()
    {
        driver.quit();
    }
}
