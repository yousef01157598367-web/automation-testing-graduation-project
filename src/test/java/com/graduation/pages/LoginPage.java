package com.graduation.pages;

import com.graduation.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class LoginPage extends BasePage {

    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By errorAlert = By.cssSelector(".oxd-alert-content-text");
    private final By requiredMessages = By.cssSelector(".oxd-input-field-error-message");

    @Step("Login with username '{0}' and password '{1}'")
    public void login(String username, String password) {
        log.info("Logging in with username: {}", username);
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
    }

    @Step("Click the Login button without entering any data")
    public void clickLoginWithEmptyFields() {
        log.info("Clicking Login with empty fields");
        waitVisible(usernameInput);
        click(loginButton);
    }

    @Step("Read the login error message")
    public String getErrorMessage() {
        String msg = getText(errorAlert);
        log.info("Error message displayed: {}", msg);
        return msg;
    }

    @Step("Read the 'Required' validation messages")
    public List<String> getRequiredMessages() {
        wait.until(d -> d.findElements(requiredMessages).size() == 2);
        List<String> messages = getTexts(requiredMessages);
        log.info("Validation messages: {}", messages);
        return messages;
    }
}
