package resources;

import base.BaseTests;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.particulars.ResourcesPage;
import pages.utils.RandomTextGenerator;

import java.util.List;

import static org.testng.Assert.*;

public class TestResourcesPage extends BaseTests {

    @Test
    public void testCorrectPage() {
        ResourcesPage resourcesPage = homePage.getResourcesPage();
        assertEquals(resourcesPage.getCurrentPageURL(), "https://www.parasoft.com/resources/");
    }

    @Test
    public void testSetSearchFilterDropdown() throws InterruptedException {
        ResourcesPage resourcesPage = homePage.getResourcesPage();
        resourcesPage.setSearchFilterDropdown("Webinar");
        resourcesPage.searchResourceByInput("");
    }

    @DataProvider(name = "resourcesTypes")
    public Object[][] getResourcesTypes() {
        return new Object[][] {
                {"Analyst Research", "ANALYST RESEARCH"},
                {"Case Study", "CASE STUDY"},
                {"Datasheet", "DATASHEET"},
                {"Video", "VIDEO"},
                {"Webinar", "WEBINAR"},
                {"White Paper", "WHITEPAPER"}
        };
    }

    @DataProvider(name = "resourcesSearch")
    public Object[][] getResourcesSearchInputs() {
        return new Object[][] {
                {"ISO 26262", "ISO 26262"},
                {"OWASP", "OWASP"},
                {"OWASP", "OWASP API Security Top 10"},
                {"VZVZ", "VZVZ Streamlines"},
                {"REST API test", "REST API Testing"},
                {"Automate API Testing by Artificial Intelligence", "Automate API Testing with Artificial Intelligence"},
                {"Automate API Testing with Machine Learning", "Automate API Testing using AI & Machine Learning"},
                {"&", "&"},
                {"!", "!"},
                {"?", "?"},
                {"%", "%"}
        };
    }

    @DataProvider(name = "invalidResourcesSearch")
    public Object[] getResourcesSearchInvalidInputs() {
        return new Object[] {
                "@", "$", "#", "~", "^", "*", "<script>alert(10)</script>", "3.14159", "Hello", "invalid input",
                RandomTextGenerator.getAlphaNumericString(7),
                RandomTextGenerator.getAlphaNumericString(15),
                RandomTextGenerator.getAlphaNumericString(20)
        };
    }

    @Test(dataProvider = "resourcesTypes")
    public void testFilteredSearchResults(String filterValue, String cardValue) throws InterruptedException {
        ResourcesPage resourcesPage = homePage.getResourcesPage();
        // set filter value to get desired search results
        resourcesPage.setSearchFilterDropdown(filterValue);
        // fill the search results into a list
        List<WebElement> cardsInfo = resourcesPage.getResourcesSearchResults();
        // check if search results correspond to filter value
        for (WebElement webElement : cardsInfo) {
            System.out.println("----------------------------");
            System.out.println("Actual result  : " + webElement.getText().substring(0, webElement.getText().indexOf('\n')));
            System.out.println("Value from card: " + cardValue);
            System.out.println("Testing result : " + webElement.getText().substring(0, filterValue.length()).contentEquals(cardValue));
            assertTrue(webElement.getText().substring(0, webElement.getText().indexOf('\n')).contentEquals(cardValue));
        }
    }

    @Test(dataProvider = "resourcesSearch")
    public void testValidInputSearchFunctionality(String inputValue, String searchCardValue) throws InterruptedException {
        ResourcesPage resourcesPage = homePage.getResourcesPage();
        // search resource via search bar
        resourcesPage.searchResourceByInput(inputValue);
        // fill the search results into a list
        List<WebElement> cardsInfo = resourcesPage.getResourcesSearchResults();
        // check if search results correspond to filter value
        for (WebElement webElement : cardsInfo) {
            System.out.println("----------------------------");
            System.out.println("Value from card: " + webElement.getText());
            System.out.println("Expected Result: " + searchCardValue);
            System.out.println("Testing result : " + webElement.getText().toLowerCase().contains(searchCardValue.toLowerCase()));
            assertTrue(webElement.getText().toLowerCase().contains(searchCardValue.toLowerCase()));
        }
    }

    @Test(dataProvider = "invalidResourcesSearch")
    public void testInvalidInputSearchFunctionality(String inputValue) throws InterruptedException {
        ResourcesPage resourcesPage = homePage.getResourcesPage();
        // search resources via search bar
        resourcesPage.searchResourceByInput(inputValue);
        // check search result correctness
        assertEquals(resourcesPage.getResourcesInvalidSearchResult(), "No Results Found");
    }

}