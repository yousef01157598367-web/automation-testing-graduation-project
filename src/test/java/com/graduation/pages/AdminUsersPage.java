package com.graduation.pages;

import com.graduation.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/** Admin > User Management > Users, and the Add User form. */
public class AdminUsersPage extends BasePage {

    private final By addButton = By.xpath("//button[normalize-space()='Add']");

    @Step("Click 'Add' to open the Add User form")
    public void clickAdd() {
        click(addButton);
        waitForUrlContains("/admin/saveSystemUser");
    }

    @Step("Check that the Add User form contains the field '{0}'")
    public boolean isFieldDisplayed(String label) {
        boolean displayed = isDisplayed(By.xpath("//label[normalize-space()='" + label + "']"));
        log.info("Field '{}' displayed: {}", label, displayed);
        return displayed;
    }
}
