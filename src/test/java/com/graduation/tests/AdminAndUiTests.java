package com.graduation.tests;

import com.graduation.base.BaseTest;
import com.graduation.pages.AdminUsersPage;
import com.graduation.pages.DashboardPage;
import com.graduation.utils.DataProviders;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.stream.Collectors;

@Epic("OrangeHRM")
@Feature("Admin and UI")
public class AdminAndUiTests extends BaseTest {

    @Test(description = "TC09 - Verify Admin > Add User page",
            dataProvider = "addUserFields", dataProviderClass = DataProviders.class)
    @Story("Add user form")
    @Severity(SeverityLevel.NORMAL)
    public void verifyAddUserForm(List<String> expectedFields) {
        DashboardPage dashboard = loginAsAdmin();
        dashboard.openMenu("Admin");
        AdminUsersPage users = new AdminUsersPage();
        users.clickAdd();

        SoftAssert soft = new SoftAssert();
        for (String field : expectedFields) {
            soft.assertTrue(users.isFieldDisplayed(field), "Field should be displayed: " + field);
        }
        soft.assertAll();
    }

    @Test(description = "TC10 - Verify the OrangeHRM footer / branding link")
    @Story("Footer link")
    @Severity(SeverityLevel.MINOR)
    public void verifyFooterBrandingLink() {
        DashboardPage dashboard = loginAsAdmin();

        Assert.assertTrue(dashboard.getFooterText().contains("OrangeHRM, Inc"),
                "Footer should contain 'OrangeHRM, Inc'");
        String newTabUrl = dashboard.clickFooterLinkAndGetNewTabUrl();
        Assert.assertTrue(newTabUrl.contains("orangehrm.com"),
                "New tab URL should contain orangehrm.com but was: " + newTabUrl);
    }

    @Test(description = "TC11 - Verify sidebar menu UI",
            dataProvider = "sidebarItems", dataProviderClass = DataProviders.class)
    @Story("Sidebar menu")
    @Severity(SeverityLevel.NORMAL)
    public void verifySidebarMenu(List<String> expectedItems) {
        DashboardPage dashboard = loginAsAdmin();
        List<String> actual = dashboard.getSidebarMenuItems();

        List<String> missing = expectedItems.stream()
                .filter(item -> !actual.contains(item))
                .collect(Collectors.toList());
        Assert.assertTrue(missing.isEmpty(), "Missing sidebar items: " + missing + " | actual: " + actual);
    }
}
