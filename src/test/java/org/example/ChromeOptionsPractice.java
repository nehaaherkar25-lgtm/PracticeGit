package org.example;

import com.sun.net.httpserver.HttpServer;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ChromeOptionsPractice {

    @Parameters("URL")
    @Test(groups={"Smoke"})
    public void sslCert(String urlname) throws InterruptedException {
        ChromeOptions op = new ChromeOptions();
        //1. for ssl certication
        op.setAcceptInsecureCerts(true);

        //2. to add extension
        op.addExtensions(new File("C:\\Users\\NEHA\\Selenium\\SeleniumPractice\\src\\main\\resources.crx"));

        //3. to add proxy
        Proxy p = new Proxy();
        p.setHttpProxy("47.237.153.201:8000");
        op.setCapability("proxy",p);

        //4. to start chrome in default this mode
        op.addArguments("--start-maximized");

        //6. for download file to sepcific folder
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.default_directory", "C:\\SeleniumDownloads");
        prefs.put("download.prompt_for_download", false);
        op.setExperimentalOption("prefs",prefs);

        WebDriver driver = new ChromeDriver(op);

        driver.get(urlname);

        driver.get("https://www.selenium.dev/selenium/web/downloads/download.html");
        driver.findElement(By.id("file-1")).click();
        Thread.sleep(3000);
    }

    @Test //6. diable popup
    public void disableLocPractice() throws InterruptedException, IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);

        server.createContext("/", exchange -> {
            String html = """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <title>Location Permission Test</title>
                    </head>
                    <body>
                        <h1>Location Permission Test</h1>
                        <p id="result">Waiting for location permission...</p>
                        <script>
                            navigator.geolocation.getCurrentPosition(
                                function(position) {
                                    document.getElementById("result").innerText =
                                        "Location permission ALLOWED";
                                },
                                function(error) {
                                    document.getElementById("result").innerText =
                                        "Location permission DENIED";
                                }
                            );
                        </script>
                    </body>
                    </html>
                    """;

            exchange.getResponseHeaders().set("Content-Type", "text/html");

            exchange.sendResponseHeaders(200,html.getBytes(StandardCharsets.UTF_8).length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(html.getBytes(StandardCharsets.UTF_8));
            }
        });

        server.start();

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Arrays.asList("disable-popup-blocking"));
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080");
        Thread.sleep(10000);
        server.stop(0);
    }
}
