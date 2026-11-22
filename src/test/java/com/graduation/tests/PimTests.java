package com.graduation.tests;

import com.graduation.base.BaseTest;
import com.graduation.pages.AddEmployeePage;
import com.graduation.pages.DashboardPage;
import com.graduation.pages.EmployeeListPage;
import com.graduation.utils.DataProviders;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

@Epic("OrangeHRM")
@Feature("PIM")
public class PimTests extends BaseTest {

    private EmployeeListPage openEmployeeList() {
        DashboardPage dashboard = loginAsAdmin();
        dashboard.openMenu("PIM");
        return new EmployeeListPage();
    }

    private AddEmployeePage openAddEmployee() {
        return openEmployeeList().goToAddEmployee();
    }

    /** Random letters so each run creates a unique employee. */
    private String randomLetters(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char) ('a' + ThreadLocalRandom.current().nextInt(26)));
        }
        return sb.toString();
    }

    @Test(description = "TC04 - Search for an existing employee",
            dataProvider = "knownEmployee", dataProviderClass = DataProviders.class)
    @Story("Search employee")
    @Severity(SeverityLevel.CRITICAL)
    public void searchExistingEmployee(String employeeName) {
        EmployeeListPage list = openEmployeeList();
        list.searchByName(employeeName);

        Assert.assertTrue(list.isNameInResults(employeeName),
                "Results table should contain: " + employeeName);
    }

    @Test(description = "TC05 - Search for a non-existing employee",
            dataProvider = "invalidEmployee", dataProviderClass = DataProviders.class)
    @Story("Search employee")
    @Severity(SeverityLevel.NORMAL)
    public void searchNonExistingEmployee(String invalidName) {
        EmployeeListPage list = openEmployeeList();
        list.searchByName(invalidName);

        Assert.assertTrue(list.isNoRecordsFoundDisplayed(), "'No Records Found' should be displayed");
    }

    @Test(description = "TC06 - Open Add Employee page")
    @Story("Add employee")
    @Severity(SeverityLevel.NORMAL)
    public void openAddEmployeePage() {
        AddEmployeePage addEmployee = openAddEmployee();

        Assert.assertTrue(addEmployee.waitForUrlContains("/pim/addEmployee"),
                "URL should contain /pim/addEmployee but was: " + addEmployee.getCurrentUrl());
        Assert.assertTrue(addEmployee.areNameFieldsDisplayed(),
                "First Name and Last Name fields should be displayed");
    }

    @Test(description = "TC07 - Add employee with an empty required field (First Name)")
    @Story("Add employee validation")
    @Severity(SeverityLevel.NORMAL)
    public void addEmployeeWithEmptyFirstName() {
        AddEmployeePage addEmployee = openAddEmployee();
        addEmployee.fillName("", "OnlyLastName");
        addEmployee.clickSave();

        Assert.assertEquals(addEmployee.getFirstNameError(), "Required",
                "First Name should show 'Required'");
    }

    @Test(description = "TC08 - End-to-end: add a new employee and find it in the list",
            dataProvider = "newEmployee", dataProviderClass = DataProviders.class)
    @Story("Add employee E2E")
    @Severity(SeverityLevel.BLOCKER)
    public void addNewEmployeeEndToEnd(String firstName, String lastName) {
        String uniqueLastName = lastName + randomLetters(5);

        AddEmployeePage addEmployee = openAddEmployee();
        addEmployee.fillName(firstName, uniqueLastName);
        addEmployee.clickSave();
        Assert.assertTrue(addEmployee.isPersonalDetailsPageOpened(),
                "Personal Details page should open for the new employee");

        EmployeeListPage list = addEmployee.goToEmployeeList();
        list.searchByName(firstName + " " + uniqueLastName);
        Assert.assertTrue(list.isNameInResults(uniqueLastName),
                "New employee should appear in the results: " + firstName + " " + uniqueLastName);
    }
}
