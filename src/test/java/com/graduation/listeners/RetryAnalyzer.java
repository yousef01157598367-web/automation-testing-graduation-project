package com.graduation.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/** Retries a failed test up to 2 times before marking it as failed. */
public class RetryAnalyzer implements IRetryAnalyzer {

    private static final int MAX_RETRY = 2;
    private static final Logger LOG = LogManager.getLogger(RetryAnalyzer.class);
    private int attempt = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (attempt < MAX_RETRY) {
            attempt++;
            LOG.warn("Retrying '{}' (attempt {} of {})", result.getName(), attempt, MAX_RETRY);
            return true;
        }
        return false;
    }
}
