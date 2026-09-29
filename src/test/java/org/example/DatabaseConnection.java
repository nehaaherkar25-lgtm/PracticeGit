package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.sql.*;

public class DatabaseConnection {

    @Test
    public void connectToDB() throws SQLException {
        WebDriver driver = new ChromeDriver();
        String host = "localhost";
        String port = "3306";
        Connection con = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/demo","root","MySql@1234");
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("select * from credentials where scenario ='rewardscard'");

        driver.get("https://www.instagram.com/");
        if(rs.next()){
            String dbU = rs.getString("username");
            String dbP = rs.getString("password");

            driver.findElement(By.name("email")).sendKeys(dbU);
            driver.findElement(By.name("pass")).sendKeys(dbP);
        }
    }
}
