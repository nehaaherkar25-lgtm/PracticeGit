package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class ActionsPractice {

    ExtentReports ext;

    @Test
    public void actionsMouseKeyboard(){
        ExtentTest test = ext.createTest("Actions test");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.amazon.com");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        Actions a = new Actions(driver);

        a.moveToElement(driver.findElement(By.cssSelector("div[class='nav-div'] a"))).perform();
        System.out.println(driver.findElement(By.cssSelector("a[lang='en-US'] span.nav-text")).getText());
        a.moveToElement(driver.findElement(By.cssSelector("div[class='nav-div'] a"))).contextClick().perform();

        a.moveToElement(driver.findElement(By.id("twotabsearchtextbox"))).click().keyDown(Keys.SHIFT)
                .sendKeys("arvik").doubleClick().perform();

        test.addScreenCaptureFromPath("screenshot.png");
        test.fail("check fail");
        ext.flush();
    }

    @BeforeTest
    public void configReport(){
        String path = System.getProperty("user.dir")+"\\reports\\index.html";
        ExtentSparkReporter rep = new ExtentSparkReporter(path);
        rep.config().setReportName("E2E Automation Report");
        rep.config().setDocumentTitle("E2E Report");

        ext  = new ExtentReports();
        ext.attachReporter(rep);
        ext.setSystemInfo("Tester","Auto tester");
    }
}
