package com.wawrze.restcheckers.frontendtests;

import com.wawrze.restcheckers.frontendtests.config.WebDriverConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FirefoxCheckersTestSuite {

    private static final String BASE_URL = "https://wawrze.github.io/checkers/checkers.html";
    private WebDriver driver;

    final String XPATH_GAME_NAME = "/html/body/div/div[1]/div[1]/fieldset/input";
    final String XPATH_RULES = "/html/body/div/div[1]/div[1]/fieldset/select[1]";
    final String XPATH_WHITE_PLAYER = "/html/body/div/div[1]/div[1]/fieldset/select[2]";
    final String XPATH_BLACK_PLAYER = "/html/body/div/div[1]/div[1]/fieldset/select[3]";
    final String XPATH_CREATE_BUTTON = "/html/body/div/div[1]/div[1]/fieldset/button";
    final String XPATH_END_BUTTON = "/html/body/div/div[1]/div[2]/fieldset/button";
    final String XPATH_NEXT_MOVE = "/html/body/div/div[3]/div[3]/div[2]/fieldset/button";

    @Before
    public void initTests() {
        driver = WebDriverConfig.getDriver(WebDriverConfig.FIREFOX);
        driver.get(BASE_URL);
    }

    @After
    public void cleanUpAfterTest() {
        driver.close();
    }

    @Test
    public void shouldCreateAndDeleteNewGame() throws InterruptedException {
        Thread.sleep(2000);

        WebElement name = driver.findElement(By.xpath(XPATH_GAME_NAME));
        name.sendKeys("test");
        WebElement rules = driver.findElement(By.xpath(XPATH_RULES));
        Select select = new Select(rules);
        select.selectByIndex(1);
        WebElement whitePlayer = driver.findElement(By.xpath(XPATH_WHITE_PLAYER));
        select = new Select(whitePlayer);
        select.selectByIndex(0);
        WebElement blackPlayer = driver.findElement(By.xpath(XPATH_BLACK_PLAYER));
        select = new Select(blackPlayer);
        select.selectByIndex(0);
        WebElement createButton = driver.findElement(By.xpath(XPATH_CREATE_BUTTON));
        createButton.click();

        Thread.sleep(2000);

        WebElement endButton = driver.findElement(By.xpath(XPATH_END_BUTTON));
        endButton.click();
    }

    @Test
    public void shouldPlay2xAIGameClassicRules() throws InterruptedException {
        Thread.sleep(2000);

        WebElement name = driver.findElement(By.xpath(XPATH_GAME_NAME));
        name.sendKeys("test");
        WebElement rules = driver.findElement(By.xpath(XPATH_RULES));
        Select select = new Select(rules);
        select.selectByIndex(1);
        WebElement whitePlayer = driver.findElement(By.xpath(XPATH_WHITE_PLAYER));
        select = new Select(whitePlayer);
        select.selectByIndex(1);
        WebElement blackPlayer = driver.findElement(By.xpath(XPATH_BLACK_PLAYER));
        select = new Select(blackPlayer);
        select.selectByIndex(1);
        WebElement createButton = driver.findElement(By.xpath(XPATH_CREATE_BUTTON));
        createButton.click();

        Thread.sleep(2000);

        WebElement nextMoveButton = driver.findElement(By.xpath(XPATH_NEXT_MOVE));
        while(!nextMoveButton.getCssValue("display").equals("none")) {
            try {
                System.out.println();
                Thread.sleep(500);
                nextMoveButton.click();
            }
            catch(Exception e) {}
        }
    }

}