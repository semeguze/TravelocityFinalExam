package com.globant.web.tests;

import com.globant.web.driver.Driver;
import com.globant.web.pages.HomePage;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

/**
 * Parent of the other classes of test.
 *
 * @author juan.montes
 */
@Slf4j
public class BaseTest {

    Driver driver;
    private HomePage home;

    @BeforeTest(alwaysRun = true)
    @Parameters({"browser", "url"})
    public void beforeTest(String browser, String url) {
        driver = new Driver(browser);
        home = new HomePage(driver.getDriver(), url);
    }

    @AfterTest(alwaysRun = true)
    public void afterTest() {
        home.dispose();
    }

    /**
     * Get the home page.
     *
     * @return HomePage
     */
    public HomePage getHomePage() {
        return home;
    }

}
