package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CookiesPractice {

    @Test
    public void cookiePract(){
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);
        WebDriver driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        Set<Cookie> cook = driver.manage().getCookies();
        for(Cookie c: cook){
            System.out.println(c.getName() + c.getValue() + c.getDomain() + c.getPath() + c.getExpiry());
        }

        Cookie c1 = driver.manage().getCookieNamed("session-username");
        System.out.println(c1.getName() + c1.getValue());

        driver.manage().deleteCookie(c1);

        System.out.println(driver.manage().getCookieNamed("session-username"));

        driver.findElement(By.className("shopping_cart_link")).click();

        Assert.assertTrue(driver.findElement(By.cssSelector(".login_wrapper")).isDisplayed());
    }
}
