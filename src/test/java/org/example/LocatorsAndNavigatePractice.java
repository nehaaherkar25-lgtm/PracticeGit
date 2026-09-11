package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.Arrays;

public class LocatorsAndNavigatePractice {

    @Test
    public void newte(){

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        String name = "neha";
        driver.get("https://rahulshettyacademy.com/locatorspractice/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to("https://google.com");
        driver.navigate().back();
        driver.navigate().forward();
        driver.navigate().back();

        System.out.println(driver.getTitle() + driver.getCurrentUrl());
        driver.findElement(By.id("inputUsername")).sendKeys(name);
        driver.findElement(By.name("inputPassword")).sendKeys("arvik");
        driver.findElement(By.cssSelector("input[value='rmbrUsername']")).click();
        driver.findElement(By.cssSelector("input[value='agreeTerms']")).click();
        driver.findElement(By.className("signInBtn")).click();

        String errorMsg = driver.findElement(By.className("error")).getText();
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(errorMsg, "* Incorrect username or password");

        driver.findElement(By.linkText("Forgot your password?")).click();
        driver.findElement(By.xpath("//input[@placeholder='Name']")).sendKeys("neha");
        driver.findElement(By.xpath("//form/input[2]")).sendKeys("neha@g.com");
        driver.findElement(By.xpath("//input[@placeholder='Phone Number']")).sendKeys("8889990007");

        WebElement reset = driver.findElement(By.cssSelector(".reset-pwd-btn"));
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));
        w.until(ExpectedConditions.elementToBeClickable(reset));
        reset.click();

        String msg = driver.findElement(By.cssSelector("form p")).getText();
        String[] splitMsg = msg.split("'");

        driver.findElement(By.cssSelector(".go-to-login-btn")).click();

        driver.findElement(By.cssSelector("input[placeholder*='User']")).sendKeys(name);
        driver.findElement(By.xpath("(//form)[2]/input/following-sibling::input")).sendKeys(splitMsg[1]);
        WebDriverWait w1 = new WebDriverWait(driver, Duration.ofSeconds(10));
        w1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='An Academy to Learn Earn & Shine  in your QA Career']")));
        driver.findElement(By.xpath("//button[contains(@class,'submit')]")).click();

        By actualText = By.xpath("//div[@class='login-container']/h1");
        w1.until(ExpectedConditions.visibilityOfElementLocated(actualText));
        String logInMSg = driver.findElement(By.xpath("//div[@class='login-container']/p")).getText();
        sa.assertEquals(logInMSg,"You are successfully logged in.");
        sa.assertEquals(driver.findElement(By.tagName("h2")).getText(),"Hello "+name+ ",");
        System.out.println(driver.findElement(By.xpath("//div[@class='login-container']//p/preceding::h1")).getText());
        driver.findElement(By.xpath("//*[text()='Log Out']")).click();
        driver.close();
        sa.assertAll();
        // ancestor - parent - following-sibling - preceding - descendant
    }
}
