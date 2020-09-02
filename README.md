# weathercheck-kotlin

Using API `https://www.metaweather.com/api/`

Demo project setup with Cucumber with Kotlin & basic reporting.

# To Run the Test Suite
## Requirements
Java 8

From the gradle menu choose task `cucumber`

From the command line run `gradlew cucumber`

# To generate a report 
From the gradle menu choose task `generateCucumberReports`

From the command line run `gradlew generateCucumberReports`

This task will generate a html file that can be opened in a browser 
Report Location: file:///$projectDir/build/test-report/html/cucumber-html-reports/overview-features.html
