package org.example;

import org.testng.TestNG;

public class TestNGRunner{
    public static void main(String[] args) {

        TestNG testng = new TestNG();

        testng.setTestSuites(
                java.util.Collections.singletonList("testng3.xml")
        );

        testng.run();
    }
}
