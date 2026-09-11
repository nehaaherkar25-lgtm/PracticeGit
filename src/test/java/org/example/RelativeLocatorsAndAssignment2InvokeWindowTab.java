package org.example;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static org.openqa.selenium.support.locators.RelativeLocator.*;

public class RelativeLocatorsAndAssignment2InvokeWindowTab {

    public WebDriver driver;

    @Test
    public void assignUIInvokeWindowTab() throws InterruptedException, IOException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/angularpractice/");
        driver.manage().window().maximize();
        String pw = driver.getWindowHandle();
        String name="";
        driver.switchTo().newWindow(WindowType.TAB);
        Set<String> windows = driver.getWindowHandles();
        for(String w:windows){
            if(!w.equalsIgnoreCase(pw)){
                driver.switchTo().window(w);
                driver.get("https://rahulshettyacademy.com/course-library");
                name = driver.findElement(By.xpath("(//div[contains(@class,'grid')]//h3)[1]")).getText();
                break;
            }
        }
        driver.switchTo().window(pw);
        WebElement n = driver.findElement(By.name("name"));
        n.sendKeys(name);
        File src = n.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src,new File("C:\\SeleniumDownloads\\S1.png"));
        System.out.println(n.getRect().getDimension().getHeight()+ " and "+n.getRect().getHeight());
        System.out.println(n.getRect().getDimension().getWidth()+ " and "+n.getRect().getWidth());
        driver.findElement(By.name("email")).sendKeys("neha25@gmail.com");
        driver.findElement(By.id("exampleInputPassword1")).sendKeys("Arvik@23");
        driver.findElement(By.id("exampleCheck1")).click();
        Select Gender = new Select(driver.findElement(By.id("exampleFormControlSelect1")));
        Gender.selectByVisibleText("Female");
        driver.findElement(By.id("inlineRadio1")).click();
        driver.findElement(By.name("bday")).sendKeys("23-01-1994");
        driver.findElement(By.cssSelector("input[value='Submit']")).click();
        Thread.sleep(1000);
        System.out.println(driver.findElement(By.cssSelector("div[class*='alert-success']")).getText());
        //driver.close();
    }

    @Test(groups={"Smoke"})
    public void relativeLocPract(){
        driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/angularpractice/");
        driver.manage().window().maximize();

        //ABOVE
        WebElement aboveElm = driver.findElement(By.name("name"));
        System.out.println(driver.findElement(with(By.tagName("label")).above(aboveElm)).getText());

        //BELOW
        WebElement belowElm = driver.findElement(By.xpath("//label[@for='dateofBirth']"));
        driver.findElement(with(By.tagName("input")).below(belowElm)).click();

        //TOLEFTOF
        WebElement leftOf = driver.findElement(By.xpath("//*[contains(text(),'Check me out')]"));
        driver.findElement(with(By.tagName("input")).toLeftOf(leftOf)).click();

        //TORIGHTOF
        WebElement rightOf = driver.findElement(By.id("inlineRadio1"));
        System.out.println(driver.findElement(with(By.tagName("label")).toRightOf(rightOf)).getText());
        Assert.assertTrue(false);
    }
}
