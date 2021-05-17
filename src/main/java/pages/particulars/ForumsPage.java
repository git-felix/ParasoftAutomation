package pages.particulars;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.utils.WaitUtils;

public class ForumsPage {
    // private data-members
    private WebDriver driver;
    private By searchBarInput = By.id("Form_Search");
    private By searchGoBtn = By.id("Form_Go");


    // constructor
    public ForumsPage(WebDriver driver) { this.driver = driver; }

    // utility functions
    public String getCurrentPageURL() { return driver.getCurrentUrl(); }

    public void inputSearchText(String inputValue) {
        WebElement searchBar = WaitUtils.waitForElementToBeClickable(driver, searchBarInput, 5);
        // empty the field if any value is prefilled
        if(!searchBar.getAttribute("value").isEmpty()) {
            searchBar.clear();
        }
        searchBar.sendKeys(inputValue);
    }

    public ForumSearchPage pressSearchButton() {
        WaitUtils.waitForElementToBeClickable(driver, searchGoBtn, 5).submit();
        return new ForumSearchPage(driver);
    }



}
