package pages.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    public static FluentWait<WebDriver> setupFluentWait(WebDriver driver, Long durationMLS, Long periodMLS){
        return new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofMillis(durationMLS))    // (durationMLS, TimeUnit.MILLISECONDS)
                .pollingEvery(Duration.ofMillis(periodMLS))     // (periodMLS, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);
    }

    public static WebElement waitForElementToBeClickable(WebDriver driver, By webElement, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(webElement));
        return element;
    }

    public static By waitForByElementToBePresent(WebDriver driver, By webElement, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.presenceOfElementLocated(webElement));
        return webElement;
    }

    public static WebElement waitForElementToBeVisible(WebDriver driver, By webElement, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(webElement));
        return element;
    }


}
