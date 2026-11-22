package com.graduation.base;

import com.graduation.pages.DashboardPage;
import com.graduation.pages.LoginPage;
import com.graduation.utils.ConfigReader;
import com.graduation.utils.TestData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.Dimension;
public class BaseTest {

    protected final Logger log = LogManager.getLogger(getClass());

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverManager.initDriver();
        WebDriver driver = DriverManager.getDriver();

        if (ConfigReader.isHeadless()) {
            driver.manage().window().setSize(new Dimension(1920, 1080));
        } else {
            driver.manage().window().maximize();
        }        driver.get(ConfigReader.getBaseUrl() + "auth/login");
        log.info("Browser started and login page opened");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
        log.info("Browser closed");
    }

    /** Logs in with the valid user from testdata.json and returns the Dashboard page. */
    protected DashboardPage loginAsAdmin() {
        new LoginPage().login(TestData.str("/validUser/username"), TestData.str("/validUser/password"));
        DashboardPage dashboard = new DashboardPage();
        dashboard.waitForUrlContains("/dashboard/index");
        return dashboard;
    }
}
