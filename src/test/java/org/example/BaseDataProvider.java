package org.example;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BaseDataProvider {

    @Test(dataProvider = "test", groups = "Smoke")
    public void testData(String name,String date){
        System.out.println(name + " | " +date);
    }

    @Test(dataProvider = "getLoginDetails")
    public void login(String name,String date, String status){
        System.out.println(name + " | " +date+ " | "+status);
    }

    @DataProvider(name="test")
    public Object[][] getData(){
        Object[][] data = new Object[3][2]; //--HERE FIRST WE GIVE ROWS AND THEN COLUMNS
        data[0][0]= "Neha";
        data[0][1]= "2301";

        data[1][0]= "Arvik";
        data[1][1]= "2303";

        data[2][0]= "Ganesh";
        data[2][1]= "0409";
        return data;
    }

    @DataProvider
    public Object[][] getLoginDetails(){
        return new Object[][]{
                {"Neha","2301","logged in"},
                {"Arvik","2303","Signed in"},
                {"Ganesh","0409","Logged out"}
        };
    }
}
