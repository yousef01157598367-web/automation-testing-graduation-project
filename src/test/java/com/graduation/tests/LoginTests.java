package com.graduation.tests;

import com.graduation.base.BaseTest;
import com.graduation.pages.DashboardPage;
import com.graduation.pages.LoginPage;
import com.graduation.utils.DataProviders;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@Epic("OrangeHRM")
@Feature("Login")
public class LoginTests extends BaseTest {

    @Test(description = "TC01 - Login with valid credentials",
            dataProvider = "validUser", dataProviderClass = DataProviders.class)
    @Story("Valid login")
    @Severity(SeverityLevel.BLOCKER)
    public void validLogin(String username, String password) {
        new LoginPage().login(username, password);
        DashboardPage dashboard = new DashboardPage();

        Assert.assertTrue(dashboard.waitForUrlContains("/dashboard/index"),
                "URL should contain /dashboard/index but was: " + dashboard.getCurrentUrl());
        Assert.assertTrue(dashboard.isHeaderDisplayed(), "Dashboard header should be displayed");
        Assert.assertEquals(dashboard.getHeaderText(), "Dashboard", "Header text mismatch");
    }

    @Test(description = "TC02 - Login with invalid credentials",
            dataProvider = "invalidLogins", dataProviderClass = DataProviders.class)
    @Story("Invalid login")
    @Severity(SeverityLevel.CRITICAL)
    public void invalidLogin(String username, String password) {
        LoginPage login = new LoginPage();
        login.login(username, password);

        Assert.assertEquals(login.getErrorMessage(), "Invalid credentials",
                "Error message mismatch for user: " + username);
    }

    @Test(description = "TC03 - Login with empty fields")
    @Story("Empty fields validation")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithEmptyFields() {
        LoginPage login = new LoginPage();
        login.clickLoginWithEmptyFields();

        List<String> messages = login.getRequiredMessages();
        Assert.assertEquals(messages.size(), 2, "Both fields should show a validation message");
        Assert.assertEquals(messages, List.of("Required", "Required"),
                "Both fields should show 'Required'");
    }
}
