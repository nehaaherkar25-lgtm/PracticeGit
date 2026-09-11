package org.example;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Set;

public class E2EScreenshotLinksPractice {

    @Test
    public void linksPractice() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        driver.manage().window().maximize();

        List<WebElement> links = driver.findElements(By.xpath("//div[@id='gf-BIG']/table//td[1]//a"));
        Actions a = new Actions(driver);
       /* for(WebElement link:links){
            a.moveToElement(link).keyDown(Keys.CONTROL).click().perform();
        }*/
        for(int i = 1; i<links.size();i++){
            a.moveToElement(links.get(i)).keyDown(Keys.CONTROL).click().perform();
            Thread.sleep(3000);
        }

        Set<String> windows = driver.getWindowHandles();
        for (String w:windows){
            driver.switchTo().window(w);
            System.out.println(driver.getTitle());
        }
    }

    @Test
    public void assignment6(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        driver.manage().window().maximize();

        WebElement op = driver.findElement(By.xpath("//input[@id='checkBoxOption3']"));
        op.click();
        String label = op.findElement(By.xpath("./parent::label")).getText();

        Select s= new Select(driver.findElement(By.id("dropdown-class-example")));
        s.selectByVisibleText(label);

        driver.findElement(By.cssSelector("#name")).sendKeys(label);
        driver.findElement(By.cssSelector("#alertbtn")).click();
        Alert a = driver.switchTo().alert();
        String t2 = a.getText().split(",")[0].split(" ")[1];
        Assert.assertEquals(t2, label);
    }

    @Test
    public void linksPracticeWithoutOpening() throws IOException, URISyntaxException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        driver.manage().window().maximize();
        SoftAssert sa = new SoftAssert();
        List<WebElement> links = driver.findElements(By.xpath("//div[@id='gf-BIG']/table//a"));

        File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src,new File("C:\\SeleniumDownloads\\screenshot.png"));

        for(WebElement link: links) {
            String url = link.getAttribute("href");
            HttpURLConnection conn = (HttpURLConnection) new URI(url).toURL().openConnection();
            conn.setRequestMethod("HEAD");
            conn.connect();
            int respCode=conn.getResponseCode();
            System.out.println(link.getText() + " : " +respCode);
            sa.assertTrue(respCode<400,link.getText()+" is broken with "+respCode+ " and "+conn.getResponseMessage());
            /*if(conn.getResponseCode()>400){
                System.out.println(link.getText() + " : this link is broken");
                sa.assertTrue(false);
            }
            else{
                System.out.println(link.getText() + " : this link is working");
                sa.assertTrue(true);
            }*/
        }
        sa.assertAll();
    }
}
