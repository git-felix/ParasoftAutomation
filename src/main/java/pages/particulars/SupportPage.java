package pages.particulars;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.utils.WaitUtils;

public class SupportPage {
    // private data-members
    private WebDriver driver;
    private static final By forumsBtn = By.xpath("//a[@title='Visit the Parasoft Forums']");

    // constructor
    public SupportPage(WebDriver driver) { this.driver = driver; }

    // utility functions
    public String getCurrentPageURL() { return driver.getCurrentUrl(); }

    public ForumsPage pressGoToTheForumsBtn() {
        WaitUtils.waitForElementToBeClickable(driver, forumsBtn, 5).click();
        return new ForumsPage(driver);
    }
}
