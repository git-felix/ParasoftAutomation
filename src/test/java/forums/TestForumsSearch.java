package forums;

import base.BaseTests;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.particulars.ForumSearchPage;
import pages.particulars.ForumsPage;
import pages.particulars.ResourcesPage;
import pages.utils.RandomTextGenerator;

import java.util.List;

import static org.testng.Assert.*;

public class TestForumsSearch extends BaseTests {

    @Test
    public void testCorrectPage() {
        ForumsPage forumsPage = homePage.getForumsPage();
        assertEquals(forumsPage.getCurrentPageURL(), "https://forums.parasoft.com/");
        System.out.println("hell");
    }

    @DataProvider(name = "forumsSearch")
    public Object[][] getForumsSearchInputs() {
        return new Object[][] {
                {"C++", "C++"},
                {"test", "test"},
                {"selenium", "selenium"},
                {"automation", "automat"}   // 'automat' because results can include 'automation' or 'automate' values
        };
    }

    @DataProvider(name = "forumsSearchDirect")
    public Object[][] getForumsSearchInputsDirect() {
        return new Object[][] {
                {"C++", "C++"},
                {"test", "test"},
                {"selenium", "selenium"},
                {"selenium webdriver", "selenium"}, // Attention: gives assertion False on one search record (and gives fairly...)
                {"how to debug selenium", "debug"}, // Attention: gives assertion False on one search record (and gives fairly...)
                {"how to debug", "debug"},
                {"parasoft", "parasoft"},
                {"SQL server", "SQL"}
        };
    }

    @DataProvider(name = "invalidForumsSearch")
    public Object[] getForumsSearchInvalidInputs() {
        return new Object[] {
                "@", "$", "#", "~", "^", "*", "%", "3.1415926",
                RandomTextGenerator.getAlphaNumericString(8),
                RandomTextGenerator.getAlphaNumericString(10),
                RandomTextGenerator.getAlphaNumericString(20),
                RandomTextGenerator.getAlphaNumericString(30)
        };
    }

    @Test(dataProvider = "forumsSearch")
    public void testValidInputSearchFromForum(String searchValue, String expectedSearchResult) {
        // navigate to forums search page
        ForumsPage forumsPage = homePage.getForumsPage();
        forumsPage.inputSearchText(searchValue);
        ForumSearchPage forumSearchPage = forumsPage.pressSearchButton();
        // assert you are in search page
        assertTrue(forumSearchPage.getCurrentPageURL().contains("https://forums.parasoft.com/search?"));
        // collect list of search result records
        List<WebElement> searchResultList = forumSearchPage.getSearchResults();
        // check for valid search results (check for expected results)
        for(WebElement element : searchResultList) {
            System.out.println("----------------------------");
            System.out.println("Actual Result  : " + element.getText());
            System.out.println("Expected Result: " + expectedSearchResult);
            System.out.println("Test Run Passed: " + element.getText().toLowerCase().contains(expectedSearchResult.toLowerCase()));
            assertTrue(element.getText().toLowerCase().contains(expectedSearchResult.toLowerCase()));
        }
    }

    @Test(dataProvider = "forumsSearchDirect")
    public void testValidInputsSearchFromForumSearch(String searchValue, String expectedSearchResult) {
        ForumSearchPage forumSearchPage = homePage.getQuickForumsSearchPage();
        // search functionality
        forumSearchPage.performSearchForInput(searchValue);
        List<WebElement> searchResultList = forumSearchPage.getSearchResults();
        // check if search results correspond to input value
        for(WebElement element : searchResultList) {
            System.out.println("----------------------------");
            System.out.println("Actual Result  : " + element.getText());
            System.out.println("Expected Result: " + expectedSearchResult);
            System.out.println("Test Run Passed: " + element.getText().toLowerCase().contains(expectedSearchResult.toLowerCase()));
            assertTrue(element.getText().toLowerCase().contains(expectedSearchResult.toLowerCase()));
        }
    }

    @Test(dataProvider = "invalidForumsSearch")
    public void testNonExistingInformationSearch(String inputValue) throws InterruptedException {
        ForumSearchPage forumSearchPage = homePage.getQuickForumsSearchPage();
        // search resource via search bar
        forumSearchPage.performSearchForInput(inputValue);
        // check search result correctness
        assertEquals(forumSearchPage.getForumsInvalidSearchResult(), "No results found");
    }

}