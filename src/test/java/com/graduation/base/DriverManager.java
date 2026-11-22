package com.graduation.base;

import com.graduation.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/** Holds one independent WebDriver per thread (required for parallel execution). */
public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() { }

    public static void initDriver() {
        boolean headless = ConfigReader.isHeadless();
        WebDriver driver;
        switch (ConfigReader.getBrowser().toLowerCase()) {
            case "firefox":
                FirefoxOptions ff = new FirefoxOptions();
                if (headless) ff.addArguments("-headless");
                driver = new FirefoxDriver(ff);
                break;
            case "edge":
                EdgeOptions edge = new EdgeOptions();
                if (headless) edge.addArguments("--headless=new");
                driver = new EdgeDriver(edge);
                break;
            case "chrome":
            default:
                ChromeOptions chrome = new ChromeOptions();
                chrome.addArguments("--remote-allow-origins=*");
                if (headless) chrome.addArguments("--headless=new", "--window-size=1920,1080",
                        "--no-sandbox", "--disable-dev-shm-usage");
                driver = new ChromeDriver(chrome);
                break;
        }
        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
