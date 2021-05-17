package pages.particulars;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import pages.utils.WaitUtils;
//import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ResourcesPage {
    // private data-members
    private WebDriver driver;
    // page attributes
    private final By selectResourceType = By.xpath("//select[@name='_sft_resource_type[]' and @class='sf-input-select']");
    private final By searchResults = By.xpath("//*[@id='search-filter-results-22']//div//div//div");
    private final By resourcesCardsTop = By.xpath(".//div//a/div[@class='resources-top']");
    private final By resourcesCardsBottom = By.xpath(".//div//a/div[@class='resources-bottom']");
    private final By searchBarInput = By.xpath("//input[@name='_sf_search[]' and @class='sf-input-text']");

    private final By searchIcon = By.xpath("//input[@name='_sf_submit']");

    public static final String[] resourceTypesList = new String[]{
        "RESOURCE TYPE", "Analyst Research", "Case Study", "Datasheet", "Video", "Webinar", "White Paper"
    };

    // constructor
    public ResourcesPage(WebDriver driver) { this.driver = driver; }

    // utility functions
    public String getCurrentPageURL() { return driver.getCurrentUrl(); }


    public void setSearchFilterDropdown(String typeName) throws InterruptedException {
        Select resourceType = new Select(WaitUtils.waitForElementToBeClickable(driver, selectResourceType, 10));
        resourceType.selectByVisibleText(typeName);
    }

    public void searchResourceByInput(String inputValue) throws InterruptedException {
        // thread sleep. TODO: change to the explicit wait
        Thread.sleep(8000L);

        // page full load wait
//        new WebDriverWait(driver, 10).until(
//                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

        // page full load wait
//        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
//            public Boolean apply(WebDriver driver) {
//                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
//            }
//        };
//        WebDriverWait wait = new WebDriverWait(driver, 15);
//        wait.until(expectation);

        // implicit wait
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // explicit wait
        WebElement searchBar = WaitUtils.waitForElementToBeClickable(driver, searchBarInput, 8);
        WebElement searchBtn = WaitUtils.waitForElementToBeClickable(driver, searchIcon, 8);

        searchBar.sendKeys(inputValue);
        searchBtn.submit();
    }

    public List<WebElement> getResourcesSearchResults() throws InterruptedException {

        Thread.sleep(2000L);
        WebElement searchResultCardsList = WaitUtils.waitForElementToBeClickable(driver, searchResults, 5);
        // data containers
        List<WebElement> cardsTop = searchResultCardsList.findElements(resourcesCardsTop);
        List<WebElement> cardsBottom = searchResultCardsList.findElements(resourcesCardsBottom);

        for(int i = 0; i < cardsTop.size(); ++i) {
            System.out.println("=> " + (i+1) + ": " + cardsTop.get(i).getText());;
        }

        return cardsTop;
    }

    public String getResourcesInvalidSearchResult() throws InterruptedException {
        Thread.sleep(1000L);
        return driver.findElement(searchResults).getText();
    }


}
