package org.example;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class DownloadUploadPractice {

    @Test
    public void downloadFile() throws InterruptedException, IOException {
        WebDriver driver= new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/upload-download-test/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        WebDriverWait w = new WebDriverWait(driver,Duration.ofSeconds(5));
        String fruit1="Apple";
        driver.findElement(By.id("downloadButton")).click();
        Thread.sleep(3000);
        String path = System.getProperty("user.home")+"\\Downloads\\download.xlsx";

        List<WebElement> fruitNames=driver.findElements(By.cssSelector(".rdt_TableBody div[role='row']"));
        for(WebElement fruit : fruitNames){
            WebElement fr= fruit.findElement(By.cssSelector("div:nth-child(2)"));
            if(fr.getText().equalsIgnoreCase("Apple")) {
                System.out.println("Before : "+fruit.findElement(By.cssSelector("div:nth-child(4)")).getText());
                break;
            }
        }

        int fColNo = getValueFromExcel(path,"fruit_name");
        int pColNo = getValueFromExcel(path,"price");
        Assert.assertTrue(writeInExcel(path,fColNo,pColNo,"230" ));


        WebElement upload = driver.findElement(By.cssSelector("input[type='file']"));
        upload.sendKeys(path);
        By toster = By.className("Toastify__toast-body");
        w.until(ExpectedConditions.visibilityOfElementLocated(toster));
        Assert.assertEquals(driver.findElement(toster).getText(),"Updated Excel Data Successfully.");
        w.until(ExpectedConditions.invisibilityOfElementLocated(toster));

        String columNo = driver.findElement(By.xpath("//div[text()='Price']")).getAttribute("data-column-id");
        System.out.println("After : "+driver.findElement(By.xpath("//div[text()='"+fruit1+"']/parent::div/following-sibling::div[@data-column-id='"+columNo+"']")).getText());

    }

    public int getValueFromExcel(String path, String columnName) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet("Sheet1");
        XSSFRow row = sheet.getRow(0);
        int colNo = -1;
        for(Cell cell: row){
            if(cell.getStringCellValue().equalsIgnoreCase(columnName)) {
                colNo = cell.getColumnIndex();
                break;
            }
        }
        workbook.close();
        fis.close();
        return colNo;
    }

    public boolean writeInExcel(String path, int fColNo, int pColNo, String number) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        DataFormatter formatter = new DataFormatter();
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        int rowCount = sheet.getLastRowNum();
        for (int i = 1; i < rowCount ; i++) {
            Row r = sheet.getRow(i);
            Cell fruitCell = r.getCell(fColNo);
            if (fruitCell != null && formatter.formatCellValue(fruitCell).equalsIgnoreCase("Apple")) {
                Cell c = r.getCell(pColNo);
                c.setCellValue(number);
                System.out.println(formatter.formatCellValue(c));
                break;
            }
        }

        FileOutputStream fos = new FileOutputStream(path);
        workbook.write(fos);
        workbook.close();
        fis.close();
        return true;
    }
}
