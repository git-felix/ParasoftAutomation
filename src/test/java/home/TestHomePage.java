package home;

import base.BaseTests;
import org.testng.annotations.Test;
import pages.particulars.HomePage;

import static org.testng.Assert.*;

public class TestHomePage extends BaseTests {

    @Test
    public void testHomePageURL() {
        assertEquals(homePage.getCurrentPageURL(), "https://www.parasoft.com/");
    }

}