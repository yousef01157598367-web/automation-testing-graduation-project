# OrangeHRM Automation Testing – Graduation Project

UI automation framework for the [OrangeHRM Open Source Demo](https://opensource-demo.orangehrmlive.com/web/index.php/auth/login)
built with **Java 17, Selenium WebDriver 4, TestNG, Maven** and the **Page Object Model**.

## Features
- Page Object Model + explicit waits (no `Thread.sleep()`)
- Data-driven tests: data in `testdata.json`, read through TestNG `@DataProvider` (Jackson)
- Parallel execution: `parallel="tests"`, `thread-count="3"`, with `ThreadLocal<WebDriver>`
- Environment settings in `config.properties` (read with `java.util.Properties`)
- Log4j2 logging to console and `logs/test-execution.log`
- Retry mechanism (`IRetryAnalyzer`, up to 2 retries)
- Allure report with steps and failure screenshots

## Test cases
| # | Scenario |
|---|----------|
| 1 | Login with valid credentials |
| 2 | Login with invalid credentials (data-driven) |
| 3 | Login with empty fields |
| 4 | Search for an existing employee |
| 5 | Search for a non-existing employee |
| 6 | Open Add Employee page |
| 7 | Add employee with empty First Name |
| 8 | E2E: add a new employee and find it in the list |
| 9 | Admin > Add User form fields |
| 10 | Footer branding link (new tab) |
| 11 | Sidebar menu items |

## Project structure
```
src/test/java/com/graduation
├── base        BaseTest, BasePage, DriverManager (ThreadLocal)
├── pages       LoginPage, DashboardPage, PimPage, EmployeeListPage, AddEmployeePage, AdminUsersPage
├── tests       LoginTests, PimTests, AdminAndUiTests
├── listeners   TestListener, RetryAnalyzer, RetryTransformer
└── utils       ConfigReader, TestData, DataProviders
src/test/resources  config.properties, testdata.json, log4j2.xml, allure.properties
```

## Prerequisites
- JDK 17+, Maven 3.8+, Google Chrome
- Allure CLI (for viewing the report)

## Run the tests
```bash
mvn clean test
# override settings from the command line
mvn clean test -Dbrowser=firefox -Dheadless=true
```

## Allure report
```bash
mvn clean test
allure generate allure-results --clean -o allure-report
allure open allure-report
```
The generated `allure-report/` folder is committed; `allure-results/` is ignored.
