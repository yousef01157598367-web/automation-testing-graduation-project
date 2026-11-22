package com.graduation.pages;

import com.graduation.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class DashboardPage extends BasePage {

    private final By header = By.cssSelector("h6.oxd-topbar-header-breadcrumb-module");
    private final By sidebarItems = By.cssSelector("ul.oxd-main-menu span.oxd-main-menu-item--name");
    private final By footerText = By.xpath("//p[contains(normalize-space(.),'OrangeHRM, Inc')]");
    private final By footerLink = By.xpath("//a[normalize-space()='OrangeHRM, Inc']");

    @Step("Check that the page header is displayed")
    public boolean isHeaderDisplayed() {
        return isDisplayed(header);
    }

    @Step("Read the page header")
    public String getHeaderText() {
        String text = getText(header);
        log.info("Page header: {}", text);
        return text;
    }

    @Step("Open sidebar menu '{0}'")
    public void openMenu(String menuName) {
        log.info("Opening menu: {}", menuName);
        click(By.xpath("//a[contains(@class,'oxd-main-menu-item')][.//span[normalize-space()='" + menuName + "']]"));
    }

    @Step("Read all sidebar menu items")
    public List<String> getSidebarMenuItems() {
        List<String> items = getTexts(sidebarItems);
        log.info("Sidebar items: {}", items);
        return items;
    }

    @Step("Scroll to the footer and read its text")
    public String getFooterText() {
        scrollIntoView(footerText);
        String text = getText(footerText);
        log.info("Footer text: {}", text);
        return text;
    }

    @Step("Click the 'OrangeHRM, Inc' footer link and read the new tab URL")
    public String clickFooterLinkAndGetNewTabUrl() {
        String originalTab = driver.getWindowHandle();
        click(footerLink);
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalTab)) {
                driver.switchTo().window(handle);
            }
        }
        try {
            wait.until(ExpectedConditions.urlContains("orangehrm.com"));
        } catch (TimeoutException ignored) {
            // the caller asserts on the URL we return
        }
        String url = driver.getCurrentUrl();
        log.info("New tab URL: {}", url);
        driver.close();
        driver.switchTo().window(originalTab);
        return url;
    }
}
