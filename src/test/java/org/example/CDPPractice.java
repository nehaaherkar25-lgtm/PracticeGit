package org.example;

import com.google.common.collect.ImmutableList;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
//import org.openqa.selenium.devtools.latest.network.Network;
import org.openqa.selenium.devtools.v153.network.Network;
import org.openqa.selenium.devtools.v153.emulation.Emulation;
import org.openqa.selenium.devtools.v153.fetch.Fetch;
import org.openqa.selenium.devtools.v153.network.model.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CDPPractice {
    @Test
    public void cdpPractMobile(){
        ChromeDriver driver = new ChromeDriver();
        DevTools devTools = driver.getDevTools();

        devTools.createSession();
        devTools.send(Emulation.setDeviceMetricsOverride(600, 1000, 50, true,
                Optional.empty(), Optional.empty(),Optional.empty(),Optional.empty(),
                Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),
                Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty()));

        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        WebElement cornerBar = driver.findElement(By.className("navbar-toggler"));
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(cornerBar.isDisplayed());
        cornerBar.click();
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
        w.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".navbar-collapse.collapse.show")));
        driver.findElement(By.linkText("Library")).click();
        sa.assertAll();
    }

    @Test
    public void directCdpCmd(){
        ChromeDriver driver = new ChromeDriver();
        DevTools devTools = driver.getDevTools();

        devTools.createSession();

        Map deviceMetrics = new HashMap();
        deviceMetrics.put("width",600);
        deviceMetrics.put("height",1000);
        deviceMetrics.put("deviceScaleFactor", 50);
        deviceMetrics.put("mobile",true);
        driver.executeCdpCommand("Emulation.setDeviceMetricsOverride",deviceMetrics);

        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        WebElement cornerBar = driver.findElement(By.className("navbar-toggler"));
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(cornerBar.isDisplayed());
        cornerBar.click();
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
        w.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".navbar-collapse.collapse.show")));
        driver.findElement(By.linkText("Library")).click();
        sa.assertAll();
    }

    @Test
    public void geolocationPract(){
        ChromeDriver driver = new ChromeDriver();
        DevTools devTools = driver.getDevTools();

        devTools.createSession();
        //devTools.send(Emulation.setGeolocationOverride(Optional.of(40), Optional.of(3), Optional.of(1),
          //      Optional.empty(),Optional.empty(),Optional.empty(), Optional.empty()));

        devTools.send(
                Emulation.setGeolocationOverride(
                        Optional.of(40),   // latitude
                        Optional.of(-3.7038),  // longitude
                        Optional.of(100.0),     // accuracy in meters
                        Optional.empty(),       // altitude
                        Optional.empty(),       // altitude accuracy
                        Optional.empty(),       // heading
                        Optional.empty()        // speed
                ));
        driver.get("C:\\Users\\NEHA\\Selenium\\GitClone\\src\\test\\resources\\goecheck.html");
        driver.findElement(By.id("getLocation")).click();
        //driver.findElement(By.name("q")).sendKeys("netflix", Keys.ENTER);
    }

    public void diffCdpCmds() {
        ChromeDriver driver = new ChromeDriver();
        DevTools devTools = driver.getDevTools();

        devTools.createSession();

        devTools.send(Network.enable(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty()));

        devTools.addListener(Network.requestWillBeSent(), request -> {
            Request res = request.getRequest();
            System.out.println(res.getUrl());
        });

        //to get the api code if failed from network
        devTools.addListener(Network.responseReceived(), response -> {
            Response res = response.getResponse();
            if (res.getStatus().toString().startsWith("4"))
                System.out.println(res.getUrl());
        });

        //to mock api to get desired output - here it will pause the req make changes and the ask the network call to continue with new changes
        devTools.addListener(Fetch.requestPaused(), request -> {
            if (request.getRequest().getUrl().contains("=abc")) {
                String mockedURL = request.getRequest().getUrl().replace("=abc", "=old");
                devTools.send(Fetch.continueRequest(request.getRequestId(), Optional.of(mockedURL),
                        Optional.of(request.getRequest().getMethod()), Optional.empty(), Optional.empty(), Optional.empty()));
            }
        });

        //to fail api to validate some msgs after fail appear correctly on ui
        devTools.addListener(Fetch.requestPaused(), request -> {
            devTools.send(Fetch.failRequest(request.getRequestId(), ErrorReason.FAILED));
        });

        //block unwanted network calls
        devTools.send(Network.setBlockedURLs(Optional.empty(), Optional.of(List.of("*.jpg","*.css"))));

        //emulate network speed to become slow
        devTools.send(Network.emulateNetworkConditions(false,3000,20000,
                10000, Optional.of(ConnectionType.ETHERNET), Optional.empty(),
                Optional.empty(), Optional.empty())); // -- depricated

        NetworkConditions conditions =
                new NetworkConditions("", 3000, 20000, 10000,
                        Optional.of(ConnectionType.ETHERNET),Optional.empty(), Optional.empty(),
                        Optional.empty(),Optional.empty());
        devTools.send(Network.emulateNetworkConditionsByRule(Optional.of(false), Optional.empty(), List.of(conditions)));

        //whenever any failure happens
        devTools.addListener(Network.loadingFailed(), failed -> {
            System.out.println(failed.getErrorText());
            System.out.println(failed.getTimestamp());
        });
    }
}
