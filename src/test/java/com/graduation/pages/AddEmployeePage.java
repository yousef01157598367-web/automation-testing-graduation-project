package com.graduation.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AddEmployeePage extends PimPage {

    private final By firstNameInput = By.name("firstName");
    private final By lastNameInput = By.name("lastName");
    private final By saveButton = By.xpath("//button[normalize-space()='Save']");
    private final By firstNameError = By.xpath(
            "//input[@name='firstName']/parent::div//span[contains(@class,'error-message')]"
            + " | //input[@name='firstName']/ancestor::div[contains(@class,'oxd-input-group')][1]//span[contains(@class,'error-message')]");
    private final By personalDetailsHeader = By.xpath("//h6[normalize-space()='Personal Details']");

    @Step("Check that First Name and Last Name fields are displayed")
    public boolean areNameFieldsDisplayed() {
        return isDisplayed(firstNameInput) && isDisplayed(lastNameInput);
    }

    @Step("Fill employee name: first='{0}', last='{1}'")
    public void fillName(String firstName, String lastName) {
        log.info("Filling employee name: '{}' '{}'", firstName, lastName);
        if (firstName != null && !firstName.isEmpty()) {
            type(firstNameInput, firstName);
        }
        if (lastName != null && !lastName.isEmpty()) {
            type(lastNameInput, lastName);
        }
    }

    @Step("Click Save")
    public void clickSave() {
        click(saveButton);
    }

    @Step("Read the First Name validation error")
    public String getFirstNameError() {
        String msg = getText(firstNameError);
        log.info("First Name error: {}", msg);
        return msg;
    }

    @Step("Wait for the Personal Details page of the new employee")
    public boolean isPersonalDetailsPageOpened() {
        return waitForUrlContains("/pim/viewPersonalDetails") && isDisplayed(personalDetailsHeader);
    }
}
