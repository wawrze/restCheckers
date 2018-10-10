package com.wawrze.restcheckers.frontendtests;

import com.wawrze.restcheckers.frontendtests.config.WebDriverConfig;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

public class ChromeCheckersTestSuite {

    private static final String BASE_URL = "https://wawrze.github.io/checkers/checkers.html";
    private WebDriver driver;

    @Before
    public void initTests() {
        driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driver.get(BASE_URL);
    }

    @After
    public void cleanUpAfterTest() {
        driver.close();
    }

}