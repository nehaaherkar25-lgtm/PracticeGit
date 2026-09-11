package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ScrollPractice {

    @Test
    public void scrollToElemPractice() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        driver.manage().window().maximize();

        JavascriptExecutor js = (JavascriptExecutor)driver;
        Thread.sleep(3000);
        js.executeScript("window.scrollBy(0,500)");
        Thread.sleep(3000);
        js.executeScript("document.querySelector('.tableFixHead').scrollTop=500");
        Thread.sleep(3000);
        js.executeScript("document.querySelector('.tableFixHead').scrollTop=0");
        Thread.sleep(3000);
        js.executeScript("document.querySelector('.tableFixHead tbody tr:nth-child(6)').scrollIntoView({behavior:'smooth', block:'center'})");
        Thread.sleep(5000);

        //document.querySelector(".tableFixHead tbody tr:nth-child(6)").scrollIntoView()

        List<WebElement> amts = driver.findElements(By.cssSelector(".tableFixHead tbody td:nth-child(4)"));
        int total = 0;
        for(WebElement amt:amts){
            total = total + Integer.parseInt(amt.getText());
        }
        int expectedTotal = Integer.parseInt(driver.findElement(By.className("totalAmount")).getText().split(":")[1].trim());
        System.out.println("Actual:" + total + "Expected: " + expectedTotal);
        Assert.assertEquals(total,expectedTotal);

        List<WebElement> row = driver.findElements(By.xpath("//table[@name='courses']//tr"));
        System.out.println("Number of rows: "+row.size());
        System.out.println("Number of columns: "+driver.findElements(By.xpath("//table[@name='courses']//tr/th")).size());
        System.out.println(row.get(2).getText());
        List<WebElement> prices = driver.findElements(By.xpath("//table[@name='courses']//td[3]"));
        int price = 0;
        for(int i=0;i<prices.size();i++){
            price = price + Integer.parseInt(prices.get(i).getText());
        }
        System.out.println("Total price of courses: "+price);
        driver.close();
    }
}
