package pages.ribbon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;
import pages.particulars.ForumSearchPage;
import pages.particulars.ForumsPage;
import pages.particulars.ResourcesPage;
import pages.particulars.SupportPage;
import pages.utils.WaitUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommandBar {
    // private data-members
    private static WebDriver driver;
    private static final By ribbon = By.xpath("//ul[@id='mega-menu-max_mega_menu_1']/li");
    private static By learnMoreBtn;
    private static final By resourcesRibbonBtn = By.xpath(".//a[@title='Resources']");
    private static final By supportRibbonBtn = By.xpath("//a[@title='Support']");

    protected CommandBar(WebDriver driver) {
        this.driver = driver;
        learnMoreBtn = WaitUtils.waitForByElementToBePresent(
                this.driver, By.xpath(".//li[@id='mega-menu-item-custom_html-9']//div//div//ul//li"), 7);
    }

    public static ResourcesPage openResourcesPage() {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        clickPageByName("RESOURCES", resourcesRibbonBtn);
        return new ResourcesPage(driver);
    }

    public static SupportPage openSupportPage() {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        clickPageByName("SUPPORT", supportRibbonBtn);
        return new SupportPage(driver);
    }

    private static void clickPageByName(String pageName, By element) {
        // initializing the list of ribbons bar items
        WaitUtils.waitForElementToBeClickable(driver, ribbon, 8);
        List<WebElement> ribbonItemsList = (driver.findElements(ribbon));

        boolean elementFound = false;
        // finding element from list to click
        for (WebElement li : ribbonItemsList) {
            // find element with appropriate name
            if(li.getText().contentEquals(pageName) && pageName.contentEquals("SUPPORT")) {
                new Actions(driver).moveToElement(li.findElement(element)).click().perform();
                elementFound = true;
                break;
            }
            if(li.getText().contentEquals(pageName)) {
                // navigate to element to expand and then click
                new Actions(driver).moveToElement(li.findElement(element))/*.click()*/.perform();
                li./*findElement(element).*/findElement(By.xpath(".//li[@id='mega-menu-item-custom_html-9']//div//div//ul//li")).click();
                elementFound = true;
                break;
            }
        }
        if(!elementFound) { System.out.println("Error: element from ribbon was not found/pressed"); }
    }

    public static ForumSearchPage quickNavigationToForumsSearchPage() {
        driver.get("https://forums.parasoft.com/search?Search=");
        return new ForumSearchPage(driver);
    }

}
