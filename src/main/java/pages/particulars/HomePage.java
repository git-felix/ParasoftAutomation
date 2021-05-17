package pages.particulars;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ribbon.CommandBar;

import java.util.concurrent.ConcurrentMap;

public class HomePage extends CommandBar {
    // data-members
    private WebDriver driver;

    // constructor
    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    // utility functions
    public String getCurrentPageURL() {
        WebDriverWait wait = new WebDriverWait(driver, 6);
        wait.until(ExpectedConditions.urlContains(driver.getCurrentUrl().substring(0, 5)));

        return driver.getCurrentUrl();
    }

    public ResourcesPage getResourcesPage() {
        return CommandBar.openResourcesPage();
    }

    public ForumsPage getForumsPage() { return CommandBar.openSupportPage().pressGoToTheForumsBtn(); }

    public ForumSearchPage getQuickForumsSearchPage() { return CommandBar.quickNavigationToForumsSearchPage(); }

    public ParabankHomePage getQuickParabankHomePage() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        ParabankHomePage parabankHomePage = new ParabankHomePage(driver);
        return parabankHomePage;
    }
}
