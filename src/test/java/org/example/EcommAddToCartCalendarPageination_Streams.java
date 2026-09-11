package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EcommAddToCartCalendarPageination_Streams {

    @Test
    public void addItemsToCart(){
        WebDriverManager.chromedriver().setup();
        WebDriver driver  = new ChromeDriver();
        String[] itemsNeeded = {"Nuts Mixture","Tomato","Pomegranate","Cucumber","Brocolli"};

        driver.get("https://rahulshettyacademy.com/seleniumPractise/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(5));
        SoftAssert sa = new SoftAssert();

        addItems(driver, itemsNeeded);
        checkoutPage(driver);
       // driver.close();
    }

    public static void addItems(WebDriver driver, String[] itemsNeeded){
        //get list of all product names
        //List<WebElement> products= driver.findElements(By.cssSelector("h4.product-name"));
        int count=0;

        List<WebElement> product= driver.findElements(By.xpath("//div[@class='product']"));
        /*//iterate through the products from website
        for(WebElement p: product){
            String name = p.getText().split("-")[0].trim();
            List<String> itemsNeededList = Arrays.asList(itemsNeeded);
            //check if the product on website is the one that is needed
            if(itemsNeededList.contains(name)){
                //if product matched click on add to cart of that product
                p.findElement(By.xpath(".//div[@class='product-action']/button")).click(); //make p point to the entire box of product
                //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("button[class='added']")));
                count++;
                if(count==itemsNeeded.length)
                    break;
            }
        }*/

       for(int i = 0; i<product.size();i++){
            String name = product.get(i).getText().split("-")[0].trim();
            List<String> itemsNeededList = Arrays.asList(itemsNeeded);
            if(itemsNeededList.contains(name)){
                /*driver.findElements(By.xpath("//button[text()='ADD TO CART']")).get(i).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("button[class='added']")));*/
                //driver.findElements(By.xpath("//div[@class='product-action']/button")).get(i).click(); --> IF MAIN PROD IS NOT POINTING TO WHOLE PRODUCT BOX
                product.get(i).findElement(By.xpath(".//div[@class='product-action']/button")).click();
                count++;
                if(count==itemsNeeded.length)
                    break;
            }
        }
    }

    public static void checkoutPage(WebDriver driver){
        driver.findElement(By.className("cart-icon")).click();
        driver.findElement(By.cssSelector("div.cart-preview button")).click();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Place Order']")));
        driver.findElement(By.className("promoCode")).sendKeys("rahulshettyacademy");
        driver.findElement(By.className("promoBtn")).click();
    }

    @Test
    public void calendarPractice() throws InterruptedException {
        //WebDriverManager.chromedriver().setup();
        WebDriver driver  = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/seleniumPractise/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        String month = "6", date = "15", year = "2027";
        String[] expectedV = {month,date,year};
        SoftAssert sa = new SoftAssert();

        String parentW = driver.getWindowHandle();
        driver.findElement(By.linkText("Top Deals")).click();
        Set<String> windows = driver.getWindowHandles();

        for(String w:windows){
            if(!w.equalsIgnoreCase(parentW)){
                driver.switchTo().window(w);
                Thread.sleep(3000);
                driver.findElement(By.cssSelector("button.react-date-picker__calendar-button")).click();
                driver.findElement(By.cssSelector("button.react-calendar__navigation__label")).click();
                driver.findElement(By.cssSelector("button.react-calendar__navigation__label")).click();
                driver.findElement(By.xpath("//button[text()='"+year+"']")).click();
                driver.findElement(By.xpath("//div[@class='react-calendar__year-view__months']/button["+month+"]")).click();
                driver.findElement(By.xpath("//div[@class='react-calendar__month-view__days']/button/abbr[text()='"+date+"']")).click();

                /*String actualM = driver.findElement(By.xpath("//div[@class='react-date-picker__inputGroup']/input[@name='month']")).getDomAttribute("value");
                String actualD = driver.findElement(By.xpath("//div[@class='react-date-picker__inputGroup']/input[@name='day']")).getDomAttribute("value");
                String actualY = driver.findElement(By.xpath("//div[@class='react-date-picker__inputGroup']/input[@name='year']")).getDomAttribute("value");
                sa.assertEquals(actualM,month);
                sa.assertEquals(actualD,date);
                sa.assertEquals(actualY,year);
                sa.assertAll();*/

                List<WebElement> actualV = driver.findElements(By.cssSelector(".react-date-picker__inputGroup__input"));
                for(int i = 0;i<actualV.size();i++){
                    sa.assertEquals(actualV.get(i).getDomAttribute("Value"),expectedV[i]);
                }

                //1.click on the column
                driver.findElement(By.xpath("//th[contains(@aria-label,'Veg/fruit name')]")).click();
                //2. captue all webelem in list
                List<WebElement> originalSorted = driver.findElements(By.xpath("//table//tbody/tr/td[1]"));
                //3.capture text of all webelem in new original list
                List<String> os = originalSorted.stream().map(s->s.getText()).collect(Collectors.toList());
                //4. sort on the original list
                List<String> sorted = os.stream().sorted().collect(Collectors.toList());
                //5.compare original and sorted
                sa.assertTrue(os.equals(sorted));
                sa.assertAll();
                driver.close();
            }
        }
    }

    @Test(groups="Smoke", dependsOnMethods="filterCheck")
    public void pagination() throws InterruptedException {
        //WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.findElement(By.xpath("//th[contains(@aria-label,'Veg/fruit name')]")).click();
        List<String> price;
        do {
            List<WebElement> originalSorted = driver.findElements(By.xpath("//table//tbody/tr/td[1]"));
            price = originalSorted.stream().filter(s-> s.getText().contains("Rice")).map(s->getPrice(s)).collect(Collectors.toList());
            price.forEach(s-> System.out.println(s));
            if(price.size()<1){
                driver.findElement(By.cssSelector("a[aria-label='Next']")).click();
            }
        }while(price.size()<1);
        String p="";
        do {
            List<WebElement> originalSorted = driver.findElements(By.xpath("//table//tbody/tr/td[1]"));
            for(int i =0; i < originalSorted.size();i++){
                if(originalSorted.get(i).getText().equalsIgnoreCase("Rice")){
                    p = originalSorted.get(i).findElement(By.xpath("following-sibling::td[1]")).getText();
                    System.out.println(p);
                    break;
                }
            }
            if(p.length()<1)
                driver.findElement(By.cssSelector("a[aria-label='Next']")).click();
        }while(p.length()<1);
    }

    private static String getPrice(WebElement s) {
        return s.findElement(By.xpath("following-sibling::td[1]")).getText();
    }

    @Test()
    public void filterCheck() throws InterruptedException {
        //WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.findElement(By.id("search-field")).sendKeys("Rice");

        List<WebElement> result = driver.findElements(By.xpath("//table//tbody/tr/td[1]"));
        boolean flag = result.stream().allMatch(s->s.getText().contains("Rice"));
        Assert.assertTrue(flag);
    }
}
