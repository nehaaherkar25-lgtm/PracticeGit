package org.example;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class AlertsPractice {

    @Test
    public void alertsCheck() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        driver.manage().window().maximize();
        SoftAssert sa = new SoftAssert();
        String name = "Neha";

        driver.findElement(By.cssSelector("input[placeholder='Enter Your Name']")).sendKeys(name);
        driver.findElement(By.id("alertbtn")).click();

        Alert a = driver.switchTo().alert();
        sa.assertEquals(a.getText(), "Hello " + name + ", share this practice page and share your knowledge");
        a.accept();
        driver.findElement(By.cssSelector("input[placeholder='Enter Your Name']")).sendKeys("Arvik");
        driver.findElement(By.id("confirmbtn")).click();
        System.out.println(driver.switchTo().alert().getText());
        driver.switchTo().alert().dismiss();
    }
}
