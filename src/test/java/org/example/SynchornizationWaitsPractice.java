package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class SynchornizationWaitsPractice {

    @Test
    public void waitsPractice() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-features=PasswordLeakDetection");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get("https://rahulshettyacademy.com/loginpagePractise/");
        driver.manage().window().maximize();

        String userName = driver.findElement(By.xpath("(//p/b/i)[1]")).getText();
        String password = driver.findElement(By.xpath("(//p/b/i)[2]")).getText();
        driver.findElement(By.cssSelector("input#username")).sendKeys(userName);
        driver.findElement(By.cssSelector("input#password")).sendKeys(password);

        driver.findElement(By.xpath("//span[contains(text(),'User')]/following-sibling::span")).click();
        w.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#okayBtn")));
        driver.findElement(By.cssSelector("#okayBtn")).click();

        Select dd1 = new Select(driver.findElement(By.cssSelector("select.form-control")));
        dd1.selectByValue("consult");

        driver.findElement(By.id("terms")).click();

        driver.findElement(By.id("signInBtn")).click();

        w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Shop Name']")));

        List<WebElement> Cards = driver.findElements(By.cssSelector(".card.h-100"));
        for (int i = 0; i < Cards.size(); i++)
            driver.findElements(By.xpath("//button[contains(text(),'Add')]")).get(i).click();

        driver.findElement(By.cssSelector(".nav-link.btn.btn-primary")).click();
        w.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-success")));

        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
        driver.findElement(By.tagName("button")).click();

        Wait<WebDriver> w1 = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30)).
                pollingEvery(Duration.ofSeconds(3)).ignoring(NoSuchElementException.class);

        WebElement msg = w1.until(new Function<WebDriver, WebElement>(){
            public WebElement apply(WebDriver driver){
                if(driver.findElement(By.cssSelector("div#finish h4")).isDisplayed())
                    return driver.findElement(By.cssSelector("div#finish h4"));
                else
                    return null;
            }
        });

        System.out.println(driver.findElement(By.cssSelector("div#finish h4")).getText());
    }
}
