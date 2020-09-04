# weathercheck-kotlin

Using API `https://www.metaweather.com/api/`

Demo project setup with Cucumber with Kotlin & basic reporting.

# To Run the Test Suite
## Requirements
Java 8

From the gradle menu choose task `cucumber`

From the command line run `gradlew cucumber`

# To generate a cucumber report 
From the gradle menu choose task `generateCucumberReports`

From the command line run `gradlew generateCucumberReports`

This task will generate a html file that can be opened in a browser 
Report Location: file:///$projectDir/build/test-report/html/cucumber-html-reports/overview-features.html

# To generate an allure report 
From the gradle menu choose task `allureReport`

From the command line run `gradlew allureReport`

This task will generate a html file that can be opened in a browser 
Report Location: file:///$projectDir/build/allure-report/index.html


# UI Tests
## Requirements
[CodeconceptJs](https://codecept.io/basics/)

with [CodeconceptJS with Playwright](https://codecept.io/playwright/#writing-tests)

[Playwright](https://github.com/microsoft/playwright)

## To Run UI Tests
From the command line `npx codeceptjs run --steps`
