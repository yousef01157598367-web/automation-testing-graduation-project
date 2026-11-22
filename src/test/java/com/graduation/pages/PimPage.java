package com.graduation.pages;

import com.graduation.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/** Shared top-bar navigation of the PIM module. */
public abstract class PimPage extends BasePage {

    private final By employeeListTab = By.xpath("//a[contains(@class,'oxd-topbar-body-nav-tab-item')][normalize-space()='Employee List']");
    private final By addEmployeeTab = By.xpath("//a[contains(@class,'oxd-topbar-body-nav-tab-item')][normalize-space()='Add Employee']");

    @Step("Open PIM > Employee List")
    public EmployeeListPage goToEmployeeList() {
        click(employeeListTab);
        return new EmployeeListPage();
    }

    @Step("Open PIM > Add Employee")
    public AddEmployeePage goToAddEmployee() {
        click(addEmployeeTab);
        return new AddEmployeePage();
    }
}
