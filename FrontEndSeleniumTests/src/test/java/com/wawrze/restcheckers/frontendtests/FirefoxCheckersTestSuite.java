package com.wawrze.restcheckers.frontendtests;

import com.wawrze.restcheckers.frontendtests.config.WebDriverConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FirefoxCheckersTestSuite {

    private static final String BASE_URL = "https://wawrze.github.io/checkers/checkers.html";
    //    private static final String BASE_URL = "file:///C:/Users/Wawra/Documents/Java_Projects/RestCheckers/front-end/index.html";
    private WebDriver driver;

    private static final String XPATH_GAME_NAME = "/html/body/div/div[1]/div[1]/fieldset/input";
    private static final String XPATH_RULES = "/html/body/div/div[1]/div[1]/fieldset/select[1]";
    private static final String XPATH_WHITE_PLAYER = "/html/body/div/div[1]/div[1]/fieldset/select[2]";
    private static final String XPATH_BLACK_PLAYER = "/html/body/div/div[1]/div[1]/fieldset/select[3]";
    private static final String XPATH_CREATE_BUTTON = "/html/body/div/div[1]/div[1]/fieldset/button";
    private static final String XPATH_END_BUTTON = "/html/body/div/div[1]/div[2]/fieldset/button";
    private static final String XPATH_NEXT_MOVE = "/html/body/div/div[3]/div[3]/div[2]/fieldset/button";
    private static final String XPATH_MOVES_COUNTER = "/html/body/div/div[3]/div[3]/div[3]/label[2]";
    private static final String XPATH_GAME_FINISHED = "/html/body/div/div[3]/div[3]/div[4]";
    private static final String XPATH_NEXT_MOVE_INPUT = "/html/body/div/div[3]/div[3]/div[2]/fieldset/input";

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
        WebElement movesCounter = driver.findElement(By.xpath(XPATH_MOVES_COUNTER));
        while (!nextMoveButton.getCssValue("display").equals("none")) {
            Thread.sleep(1200);
            try {
                nextMoveButton.click();
            } catch (ElementNotInteractableException e) {
                WebElement finishedGame = driver.findElement(By.xpath(XPATH_GAME_FINISHED));
                if (finishedGame.getCssValue("display").equals("none"))
                    throw new RuntimeException();
            }
        }
    }

    @Test
    public void shouldPlay2xAIGameEnglishRules() throws InterruptedException {
        Thread.sleep(2000);

        WebElement name = driver.findElement(By.xpath(XPATH_GAME_NAME));
        name.sendKeys("test");
        WebElement rules = driver.findElement(By.xpath(XPATH_RULES));
        Select select = new Select(rules);
        select.selectByIndex(2);
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
        while (!nextMoveButton.getCssValue("display").equals("none")) {
            Thread.sleep(1200);
            try {
                nextMoveButton.click();
            } catch (ElementNotInteractableException e) {
                WebElement finishedGame = driver.findElement(By.xpath(XPATH_GAME_FINISHED));
                if (finishedGame.getCssValue("display").equals("none"))
                    throw new RuntimeException();
            }
        }
    }

    @Test
    public void shouldPlay2xAIGamePoddavkiRules() throws InterruptedException {
        Thread.sleep(2000);

        WebElement name = driver.findElement(By.xpath(XPATH_GAME_NAME));
        name.sendKeys("test");
        WebElement rules = driver.findElement(By.xpath(XPATH_RULES));
        Select select = new Select(rules);
        select.selectByIndex(3);
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
        while (!nextMoveButton.getCssValue("display").equals("none")) {
            Thread.sleep(1200);
            try {
                nextMoveButton.click();
            } catch (ElementNotInteractableException e) {
                WebElement finishedGame = driver.findElement(By.xpath(XPATH_GAME_FINISHED));
                if (finishedGame.getCssValue("display").equals("none"))
                    throw new RuntimeException();
            }
        }
    }

    @Ignore
    @Test
    public void shouldPlayHumanVsComputerGame() throws InterruptedException {
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
        select.selectByIndex(1);
        WebElement createButton = driver.findElement(By.xpath(XPATH_CREATE_BUTTON));
        createButton.click();

        Thread.sleep(2000);

        WebElement nextMoveInput = driver.findElement(By.xpath(XPATH_NEXT_MOVE_INPUT));
        WebElement nextMoveButton = driver.findElement(By.xpath(XPATH_NEXT_MOVE));

        nextMoveInput.sendKeys("F1-E2");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("E2-D1");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("F7-E8");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("G2-F1");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("F3-E2");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("H1-G2");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("G2-F3");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("H3-G2");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("G6-F7");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("H7-G6");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("F3-D5");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("E8-C6");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("C6-A8");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("A8-F3");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("F3-A8");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("A8-F3");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("F3-A8");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("A8-F3");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("F3-A8");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("A8-F3");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("F3-A8");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("E2-C4");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("C4-A2");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("D1-B3");
        nextMoveButton.click();
        Thread.sleep(3000);
        WebElement finishedGame = driver.findElement(By.xpath(XPATH_GAME_FINISHED));
        if (finishedGame.getCssValue("display").equals("none"))
            throw new RuntimeException();
    }

    @Test
    public void shouldPlayHumanVsHumanGame() throws InterruptedException {
        Thread.sleep(2000);

        WebElement name = driver.findElement(By.xpath(XPATH_GAME_NAME));
        name.sendKeys("test");
        WebElement rules = driver.findElement(By.xpath(XPATH_RULES));
        Select select = new Select(rules);
        select.selectByIndex(2);
        WebElement whitePlayer = driver.findElement(By.xpath(XPATH_WHITE_PLAYER));
        select = new Select(whitePlayer);
        select.selectByIndex(0);
        WebElement blackPlayer = driver.findElement(By.xpath(XPATH_BLACK_PLAYER));
        select = new Select(blackPlayer);
        select.selectByIndex(0);
        WebElement createButton = driver.findElement(By.xpath(XPATH_CREATE_BUTTON));
        createButton.click();
        Thread.sleep(2000);
        WebElement nextMoveInput = driver.findElement(By.xpath(XPATH_NEXT_MOVE_INPUT));
        WebElement nextMoveButton = driver.findElement(By.xpath(XPATH_NEXT_MOVE));
        nextMoveInput.sendKeys("F1-E2");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("C2-D1");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("F3-E4");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("D1-F3");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("F3-D5");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("F5-E6");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("B1-C2");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("G4-F3");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("A2-B1");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("F3-E2");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("C2-D1");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("H5-G4");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("D1-F3");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("F3-H5");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("G2-F1");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("B1-C2");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("F1-E2");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("C2-D3");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("H3-G4");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("D3-F1");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("H1-G2");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("F1-H3");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("H3-F5");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("F5-D7");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("G6-F5");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("B3-C2");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("F5-E4");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("D5-F3");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("F7-E6");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("D7-F5");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("G8-F7");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("H5-G6");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("F7-H5");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("C2-D3");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("H5-G4");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("F3-H5");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("H7-G6");
        nextMoveButton.click();
        Thread.sleep(1200);
        nextMoveInput.sendKeys("H5-F7");
        nextMoveButton.click();
        Thread.sleep(1200);

        Thread.sleep(3000);
        WebElement finishedGame = driver.findElement(By.xpath(XPATH_GAME_FINISHED));
        if (finishedGame.getCssValue("display").equals("none"))
            throw new RuntimeException();
    }

}