package pages.particulars;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import pages.utils.WaitUtils;

import java.util.List;

public class ForumSearchPage {
    // private data-members
    private final WebDriver driver;
    private final By searchBarInput = By.id("Form_search");
    private final By searchResults = By.id("search-results");
    private final By searchEmptyResult = By.className("NoResults");
    private final By searchResultsTitles = By.xpath(".//li[@class='Item Item-Search']/h3/a");
    private final By searchBtnRoot = By.xpath("//div[@class='KeywordsWrap InputAndButton']");
    private final By searchBtn = By.xpath(".//span//button[@class='Button' and @title='Search']");

    // constructor
    public ForumSearchPage(WebDriver driver) { this.driver = driver; }

    // utility functions
    public String getCurrentPageURL() { return driver.getCurrentUrl(); }

    public void performSearchForInput(String inputValue) {
        WebElement searchBar = WaitUtils.waitForElementToBeClickable(driver, searchBarInput, 8);
        // empty the field if any value is prefilled
        if(!searchBar.getAttribute("value").isEmpty()) {
            searchBar.clear();
        }
        searchBar.sendKeys(inputValue);
        driver.findElement(searchBtnRoot).findElement(searchBtn).submit();
    }

    public List<WebElement> getSearchResults() {
        WebElement searchResultCardsList = WaitUtils.waitForElementToBeClickable(driver, searchResults, 8);
        List<WebElement> cardsTitles = searchResultCardsList.findElements(searchResultsTitles);

        for(int i = 0; i < cardsTitles.size(); ++i) {
            System.out.println("=> " + (i+1) + ": " + cardsTitles.get(i).getText());;
        }

        return cardsTitles;
    }

    public String getForumsInvalidSearchResult() throws InterruptedException {
        return WaitUtils.waitForElementToBeClickable(driver, searchEmptyResult, 5).getText();
//        return driver.findElement(searchEmptyResult).getText();
    }

}
