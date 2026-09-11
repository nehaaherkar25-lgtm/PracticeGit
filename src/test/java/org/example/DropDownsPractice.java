package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

public class DropDownsPractice {

    @Test
    public static void dropDown() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
        driver.manage().window().maximize();

        SoftAssert sa = new SoftAssert();

        //Static dropdown
        WebElement staticDD = driver.findElement(By.xpath("//select[contains(@id,'DropDownListCurrency')]"));
        Select sdropdown = new Select(staticDD);
        sdropdown.selectByIndex(2);
        System.out.println(sdropdown.getFirstSelectedOption().getText());
        sdropdown.selectByVisibleText("USD");
        System.out.println(sdropdown.getFirstSelectedOption().getText());
        sdropdown.selectByValue("INR");
        System.out.println(sdropdown.getFirstSelectedOption().getText());
        sa.assertEquals(sdropdown.getFirstSelectedOption().getText(),"INR");

        //Static without select tag
        driver.findElement(By.xpath("//div[@id='divpaxinfo']")).click();
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
        w.until(ExpectedConditions.visibilityOfElementLocated(By.id("hrefIncAdt")));
        increaseNumber(driver,3,By.id("hrefIncAdt"));
        increaseNumber(driver,1,By.id("hrefIncChd"));
        increaseNumber(driver,1,By.id("hrefIncInf"));
        driver.findElement(By.cssSelector("input[value='Done']")).click();
        System.out.println(driver.findElement(By.xpath("//div[@id='divpaxinfo']")).getText());
        sa.assertEquals(driver.findElement(By.xpath("//div[@id='divpaxinfo']")).getText(),"4 Adult, 1 Child, 1 Infant");

        //dynamic dropdown
        driver.findElement(By.xpath("//input[@value='Departure City' and contains(@id,'originStation1_CTXT')]")).click();
        driver.findElement(By.xpath("//div[contains(@id,'originStation1_CTNR')]//a[@value='PNQ']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[contains(@id,'destinationStation1_CTNR')]//a[@value='GOI']")).click();

        //Calendar
        Thread.sleep(1000);
        driver.findElement(By.cssSelector(".ui-state-default.ui-state-active")).click();
        sa.assertTrue(driver.findElement(By.id("Div1")).getDomAttribute("style").contains("0.5"));
        driver.findElement(By.id("ctl00_mainContent_rbtnl_Trip_1")).click();
        sa.assertTrue(driver.findElement(By.id("Div1")).getDomAttribute("style").contains("1"));
        Thread.sleep(2000);

        //checkbox
        driver.findElement(By.cssSelector("input[id*='friendsandfamily']")).click();
        driver.findElement(By.cssSelector("input[id*='StudentDiscount']")).click();
        sa.assertFalse(driver.findElement(By.cssSelector("input[id*='friendsandfamily']")).isSelected());
        sa.assertTrue(driver.findElement(By.cssSelector("input[id*='StudentDiscount']")).isSelected());

        driver.findElement(By.id("ctl00_mainContent_btn_FindFlights")).click();
        Thread.sleep(5000);

        //AutoSuggest dropdown
        driver.findElement(By.id("autosuggest")).sendKeys("Island");
        Thread.sleep(2000);
        List<WebElement> countryList = driver.findElements(By.cssSelector("li[class='ui-menu-item'] a"));
        for(WebElement c : countryList){
            if(c.getText().equalsIgnoreCase("Cook Islands")){
                c.click();
                break;
            }
        }
        sa.assertEquals(driver.findElement(By.id("autosuggest")).getAttribute("value"),"Cook Islands");

        sa.assertAll();
    }

    public  static void increaseNumber(WebDriver driver, int n, By elem){
        int i = 1;
        while(i<=n){
            driver.findElement(elem).click();
            i++;
        }

    }

    @Test
    public static void dropDownAssignment() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        SoftAssert sa = new SoftAssert();

        WebElement inputC = driver.findElement(By.id("autocomplete"));
        inputC.sendKeys("Uni");
        //Thread.sleep(2000);
        String expCountry = "United Arab Emirates";
        List<WebElement> countries = driver.findElements(By.cssSelector("li.ui-menu-item div"));
        for(WebElement country:countries){
            inputC.sendKeys(Keys.DOWN);
            if(country.getText().equalsIgnoreCase(expCountry)){
                sa.assertEquals(driver.findElement(By.id("autocomplete")).getAttribute("value"),country.getText());
               // country.click();
                break;
            }
        }
        //sa.assertEquals(driver.findElement(By.id("autocomplete")).getAttribute("value"),expCountry);
        sa.assertAll();
    }
}
