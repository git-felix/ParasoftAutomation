package base;

import com.google.common.io.Files;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.particulars.HomePage;
import pages.particulars.ParabankHomePage;
import pages.ribbon.CommandBar;
import pages.utils.EventReporter;
import pages.utils.WindowManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BaseTests {
    // data-members
    private EventFiringWebDriver driver;
    protected CommandBar commandBar;    // TO DELETE
    protected HomePage homePage;
    protected ParabankHomePage parabankHomePage;

    @BeforeClass
    protected void initializeAll() throws AWTException {
        System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver.exe");
        driver = new EventFiringWebDriver(new ChromeDriver(getChromeOptions()));
        driver.register(new EventReporter());
        driver.manage().window().maximize();
        //openHomePage();
    }

    @BeforeMethod
    protected void openHomePage() throws AWTException {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.parasoft.com/");
        homePage = new HomePage(driver);
    }

    @AfterMethod
    protected void recordFailure(ITestResult result){
        if(ITestResult.FAILURE == result.getStatus())
        {
            var camera = (TakesScreenshot)driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            try{
                Files.move(screenshot, new File("src/test/screenshots/"
                        + result.getName() + "_" + result.getEndMillis() + ".png"));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    @AfterClass
    protected void tearDown() { driver.quit(); }

    private WindowManager getWindowOptions() {
        return new WindowManager(driver);
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");
        return options;
    }
}
