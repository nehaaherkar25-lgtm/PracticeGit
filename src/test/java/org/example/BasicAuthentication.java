package org.example;

import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.function.Predicate;

public class BasicAuthentication {

    @Test
    public void authenticationPopup(){
        ChromeDriver driver = new ChromeDriver();

        Predicate<URI> pURI = uri -> uri.getHost().contains("httpbin.org");
        ((HasAuthentication)driver).register(pURI, UsernameAndPassword.of("foo","bar"));
        driver.get("https://httpbin.org/basic-auth/foo/bar");
    }
}
