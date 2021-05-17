package admin;

import base.BaseTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.particulars.HomePage;
import pages.particulars.ParabankAdminPage;
import pages.particulars.ParabankHomePage;

import java.io.IOException;
import java.util.Random;

import static org.testng.Assert.*;

public class TestParabankAdminPage extends BaseTests {
    @Test
    public void createFilledApplication() throws IOException {
        ParabankHomePage parabankHomePageStart = homePage.getQuickParabankHomePage();
        ParabankAdminPage adminPage = parabankHomePageStart.clickAdminPage();
        adminPage.setDataAccessMode();
        adminPage.fillWebServiceEndpointREST("application/xml");
        adminPage.setInitialBalance(getRandomNumber());
        adminPage.setMinimalBalance(getRandomNumber());
        adminPage.setLoanProcessorType("combined");
        // check for mandatory fields to be filled in before pressing the 'Submit' button
        assertFalse(adminPage.getInitialBalanceValue().isEmpty());
        assertFalse(adminPage.getMinimalBalanceValue().isEmpty());
        // submit the application
        adminPage.pressSubmitBtn();
        // check for successful submission
        assertEquals(adminPage.getSubmissionStatus(), "Settings saved successfully.");
    }

    private double getRandomNumber() {
        Random rand = new Random();
        return Math.floor(rand.nextDouble() * 10000 * 10000) / 100;
    }

    // TODO: refactor the below 3 functions into 1
    //  to check the error message by one single method
    @Test
    public void createBlankInitBalanceApplication() throws IOException {
        ParabankHomePage parabankHomePageStart = homePage.getQuickParabankHomePage();
        ParabankAdminPage adminPage = parabankHomePageStart.clickAdminPage();
        adminPage.clearFieldValue(adminPage.getInitBalanceElement());
        adminPage.pressSubmitBtn(); // submit the application

        assertFalse(adminPage.checkSubmissionStatusAvailability());  // check for failed submission message absence
        assertEquals(adminPage.getBlankInitBalanceError(), "Initial balance is required."); // check for error message existence
    }

    @Test
    public void createBlankMinBalanceApplication() throws IOException {
        ParabankHomePage parabankHomePageStart = homePage.getQuickParabankHomePage();
        ParabankAdminPage adminPage = parabankHomePageStart.clickAdminPage();
        adminPage.clearFieldValue(adminPage.getMinBalanceElement());
        adminPage.pressSubmitBtn(); // submit the application

        assertFalse(adminPage.checkSubmissionStatusAvailability());     // check for failed submission message absence
        assertEquals(adminPage.getBlankMinBalanceError(), "Minimum balance is required."); // check for error message existence
    }
    @Test
    public void createBlankThresholdApplication() throws IOException {
        ParabankHomePage parabankHomePageStart = homePage.getQuickParabankHomePage();
        ParabankAdminPage adminPage = parabankHomePageStart.clickAdminPage();
        adminPage.clearFieldValue(adminPage.getThresholdElement());
        adminPage.pressSubmitBtn(); // submit the application

        assertFalse(adminPage.checkSubmissionStatusAvailability());     // check for failed submission message absence
        assertEquals(adminPage.getBlankThresholdError(), "Threshold is required."); // check for error message existence
    }

    @DataProvider(name = "applicationData")
    public Object[][] getAppDataInputs() {
        return new Object[][] {
                {"application1/xml", 512.25, 64.95, "funds", 20},
                {"application2/xml", 51.50, 58.35, "down", 45},
                {"application3/xml", 402.64, 97.75, "combined", 72},
        };
    }

    @Test(dataProvider = "applicationData")
    public void testApplicationDataSaving(
            String webService, double initBalance, double minBalance, String loanType, int threshold
    ) throws IOException {
        ParabankHomePage parabankHomePageStart = homePage.getQuickParabankHomePage();
        ParabankAdminPage adminPage = parabankHomePageStart.clickAdminPage();
        adminPage.setDataAccessMode();
        adminPage.fillWebServiceEndpointREST(webService);
        adminPage.setInitialBalance(initBalance);
        adminPage.setMinimalBalance(minBalance);
        adminPage.setLoanProcessorType(loanType);
        adminPage.setThreshold(threshold);
        // submit the application
        adminPage.pressSubmitBtn();
        // check for successful submission
        assertEquals(adminPage.getSubmissionStatus(), "Settings saved successfully.");
        // start checking the data being saved
        adminPage.refreshPage();
        parabankHomePageStart = homePage.getQuickParabankHomePage();
        adminPage = parabankHomePageStart.clickAdminPage();
        // assert saved data
        assertEquals(adminPage.getRESTEndpointValue(), webService);
        assertEquals(adminPage.getInitialBalanceValue(), String.valueOf(initBalance));
        assertEquals(adminPage.getMinimalBalanceValue(), String.valueOf(minBalance));
        assertEquals(adminPage.getThresholdValue(), String.valueOf(threshold));

    }
}