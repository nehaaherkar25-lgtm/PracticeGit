package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class WindowHandlePractice {

    @Test
    public void multiWindowHandle(){

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-features=PasswordLeakDetection");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
        //driver.get("https://rahulshettyacademy.com/loginpagePractise/");
        driver.manage().window().maximize();

        /*driver.findElement(By.partialLinkText("Free Access to InterviewQues")).click();

        Set<String> windows = driver.getWindowHandles();
        Iterator<String> it = windows.iterator();
        String parent = it.next();
        String child = it.next();
        driver.switchTo().window(child);

        driver.findElement(By.cssSelector(".im-para.red")).getText();
        String email = driver.findElement(By.cssSelector(".im-para.red")).getText().split("at")[1].trim().split(" ")[0];

        driver.switchTo().window(parent);
        driver.findElement(By.cssSelector("input#username")).sendKeys(email);*/


        driver.get("https://the-internet.herokuapp.com/");
        driver.findElement(By.xpath("//a[text()='Multiple Windows']")).click();

        //w.until(ExpectedConditions.visibilityOfElementLocated(By.className("example")));
        String parentWindow = driver.getWindowHandle();
        driver.findElement(By.linkText("Click Here")).click();

        Set<String> windows = driver.getWindowHandles();
        windows.size();

       /* Iterator<String> it = windows.iterator();
        while(it.hasNext()){
            String nextW = it.next();
            if(!nextW.equalsIgnoreCase(parentWindow)) {
                driver.switchTo().window(nextW);
                System.out.println(driver.findElement(By.tagName("h3")).getText());
                driver.close();
            }
        }*/

        for(String win: windows){
            if(!win.equals(parentWindow)){
                driver.switchTo().window(win);
                System.out.println(driver.findElement(By.tagName("h3")).getText());
                driver.close();
            }
        }

        driver.switchTo().window(parentWindow);
        System.out.println(driver.findElement(By.tagName("h3")).getText());
    }
}
