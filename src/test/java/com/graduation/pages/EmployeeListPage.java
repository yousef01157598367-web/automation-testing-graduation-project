package com.graduation.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class EmployeeListPage extends PimPage {

    private final By employeeNameInput = By.xpath(
            "//label[normalize-space()='Employee Name']/ancestor::div[contains(@class,'oxd-input-group')][1]//input");
    private final By searchButton = By.xpath("//button[normalize-space()='Search']");
    private final By tableBody = By.cssSelector(".oxd-table-body");
    private final By noRecordsFound = By.xpath("//*[normalize-space()='No Records Found']");

    @Step("Search for employee '{0}'")
    public void searchByName(String name) {
        log.info("Searching for employee: {}", name);
        type(employeeNameInput, name);
        click(searchButton);
    }

    @Step("Check that the results table contains '{0}'")
    public boolean isNameInResults(String name) {
        boolean found = waitForTextInElement(tableBody, name);
        log.info("Results table contains '{}': {}", name, found);
        return found;
    }

    @Step("Check that the 'No Records Found' message is displayed")
    public boolean isNoRecordsFoundDisplayed() {
        return isDisplayed(noRecordsFound);
    }
}
