package pages.particulars;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.time.Duration;

public class ParabankAdminPage {
    // private data-members
    // driver declaration
    private WebDriver driver;
    // page attributes
    private final By checkboxAccessModeSOAP = By.id("accessMode1");   // for later usage
    private final By checkboxAccessModeRESTxml = By.id("accessMode2");
    private final By inputWebServiceEndpointREST = By.id("restEndpoint");
    private final By initialBalance = By.id("initialBalance");
    private final By minimalBalance = By.id("minimumBalance");
    private final By menuLoanProcessor = By.id("loanProcessor");
    private final By loanProcessorThreshold = By.id("loanProcessorThreshold");

    private final By submitBtn = By.cssSelector("input[value='Submit']");
    private final By submissionStatus = By.xpath("//p[contains(@style, 'color')][1]/b[1]");
    // error messages
    private final By errorMsgInitBalance = By.id("initialBalance.errors");
    private final By errorMsgMinBalance = By.id("minimumBalance.errors");
    private final By errorMsgThreshold = By.id("loanProcessorThreshold.errors");



    // constructor
    public ParabankAdminPage(WebDriver driver) { this.driver = driver; }

    // Fluent Wait (initialization of wait instance)
    public FluentWait<WebDriver> createFluentWait(int durationMLS, int periodMLS) {
        return new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofMillis(durationMLS))    // (durationMLS, TimeUnit.MILLISECONDS)
                .pollingEvery(Duration.ofMillis(periodMLS))     // (periodMLS, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);
    }

    // set Data Access Mode type
    public void setDataAccessMode() {
        WebElement setAccessModeToRESTxml = createFluentWait(3000, 100)
                .until(ExpectedConditions.elementToBeClickable(checkboxAccessModeRESTxml));
        setAccessModeToRESTxml.click();
    }
    // fill Web Service information
    public void fillWebServiceEndpointREST(String endpoint) {
        WebElement inputEndpointREST = driver.findElement(inputWebServiceEndpointREST);
        if( ! inputEndpointREST.getAttribute("value").isEmpty()) {
            inputEndpointREST.clear();
        }
        inputEndpointREST.sendKeys(endpoint);
    }
    // fill Application information
    public void setInitialBalance(double initBalance) throws IOException {
        WebElement inputInitBalance = createFluentWait(2000, 100)
                .until(ExpectedConditions.visibilityOfElementLocated(initialBalance));
        if(inputInitBalance.isEnabled()) {
            clearFieldValue(inputInitBalance);
            inputInitBalance.sendKeys(String.valueOf(initBalance));
        }
        else { throw new IOException("Error: Min.Balance field is not editable"); }
    }
    public void setMinimalBalance(double minBalance) throws IOException {
        WebElement inputMinBalance = createFluentWait(2000, 100)
                .until(ExpectedConditions.visibilityOfElementLocated(minimalBalance));
        if(inputMinBalance.isEnabled()) {
            clearFieldValue(inputMinBalance);
            inputMinBalance.sendKeys(String.valueOf(minBalance));
        }
        else { throw new IOException("Error: Min.Balance field is not editable"); }
    }
    public void setThreshold(int threshold) throws IOException {
        WebElement thresholdInput = createFluentWait(2000, 100)
                .until(ExpectedConditions.visibilityOfElementLocated(loanProcessorThreshold));
        if(thresholdInput.isEnabled()) {
            clearFieldValue(thresholdInput);
            thresholdInput.sendKeys(String.valueOf(threshold));
        }
        else { throw new IOException("Error: Min.Balance field is not editable"); }
    }

    public void clearFieldValue(WebElement inputField) {
        if( ! inputField.getAttribute("value").isEmpty() ) {
            inputField.clear();
        }
    }
    // TODO: refactor and place all WebElements into a Map, to access them by single function
    public WebElement getInitBalanceElement() {
        return driver.findElement(initialBalance);
    }
    public WebElement getMinBalanceElement() {
        return driver.findElement(minimalBalance);
    }
    public WebElement getThresholdElement() {
        return driver.findElement(loanProcessorThreshold);
    }

    // getter functions for mandatory fields of balance
    public String getRESTEndpointValue() {
        return driver.findElement(inputWebServiceEndpointREST).getAttribute("value");
    }
    public String getInitialBalanceValue() {
        return driver.findElement(initialBalance).getAttribute("value");
    }
    public String getMinimalBalanceValue() {
        return driver.findElement(minimalBalance).getAttribute("value");
    }
    public String getThresholdValue() {
        return driver.findElement(loanProcessorThreshold).getAttribute("value");
    }

    public void setLoanProcessorType(String loanProcessorType) {
        Select loanProcessorOptions = new Select(driver.findElement(menuLoanProcessor));
        loanProcessorOptions.selectByValue(loanProcessorType);
    }

    public boolean checkSubmissionStatusAvailability() {
        return (!driver.findElements(submissionStatus).isEmpty());
    }

    // TODO: refactor and create single GetBlankFieldError(errorMessageType) method
    //       to return error message for specified field by parameter
    public String getBlankInitBalanceError() {
        if (!driver.findElements(errorMsgInitBalance).isEmpty()) {
            return driver.findElement(errorMsgInitBalance).getText();
        }
        return "";
    }
    public String getBlankMinBalanceError() {
        if (!driver.findElements(errorMsgMinBalance).isEmpty()) {
            return driver.findElement(errorMsgMinBalance).getText();
        }
        return "";
    }
    public String getBlankThresholdError() {
        if (!driver.findElements(errorMsgThreshold).isEmpty()) {
            return driver.findElement(errorMsgThreshold).getText();
        }
        return "";
    }

    // submit the filled in applications
    public void pressSubmitBtn() {
        driver.findElement(submitBtn).click();
    }
    // check if submission was successful
    public String getSubmissionStatus() {
        return driver.findElement(submissionStatus).getText();
    }

    public void refreshPage() { driver.navigate().refresh(); }

}
