package com.graduation.listeners;

import com.graduation.base.DriverManager;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

/** Logs test lifecycle and attaches screenshot / URL to Allure on failure. */
public class TestListener implements ITestListener {

    private static final Logger LOG = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        LOG.info("START: {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOG.info("PASSED: {}", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOG.error("FAILED: {}", result.getName(), result.getThrowable());
        attachEvidence();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOG.warn("SKIPPED / RETRYING: {}", result.getName());
        if (result.getThrowable() != null) {
            attachEvidence(); // retry attempts end up here
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        LOG.info("Suite '{}' finished | passed={} failed={} skipped={}",
                context.getName(),
                context.getPassedTests().size(),
                context.getFailedTests().size(),
                context.getSkippedTests().size());
    }

    private void attachEvidence() {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) {
            return;
        }
        try {
            byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot on failure", "image/png", new ByteArrayInputStream(png), "png");
            Allure.addAttachment("Page URL", "text/plain", driver.getCurrentUrl());
        } catch (Exception e) {
            LOG.error("Could not attach evidence: {}", e.getMessage());
        }
    }
}