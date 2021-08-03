# qa-skills-challenge-boilerplate

## Table of Contents

- [qa-skills-challenge-boilerplate](#qa-skills-challenge-boilerplate)
  - [Table of Contents](#table-of-contents)
  - [Running tests using Maven](#running-tests-using-maven)
    - [Required software to run tests using Maven](#required-software-to-run-tests-using-maven)
    - [How to run tests using Maven](#how-to-run-tests-using-maven)
      - [Examples](#examples)
      - [Screenshots for failed test cases](#screenshots-for-failed-test-cases)
      - [Generate Surefire HTML report](#generate-surefire-html-report)
      - [Generate Cucumber HTML report](#generate-cucumber-html-report)

## Running tests using Maven

### Required software to run tests using Maven

The following software needs to be installed:

- Chrome browser - <https://www.google.com/chrome/>
- OpenJDK - <https://jdk.java.net/>
- Maven - <https://maven.apache.org/>

### How to run tests using Maven

In order to run tests the following command should be performed in the folder with Maven Project Object Model
file `pom.xml`:

```bash
mvn clean test -D<PropertyKey>=<Value> -D<PropertyKey>=<Value> ... -D<PropertyKey>=<Value>
```

Properties:

| Property Key | Required | Description | Default value |
| --- | --- | --- | --- |
| `env` | mandatory | Environment name that represents a base file name of the property file `/src/test/resources/<env>.properties` for an environment under test. (Please see available values below) | - |
| `browser` | mandatory | Browser: `chrome`, `firexox` | - |
| `browser.width` | optional| Browser window width: `1024`, `1280`, etc. | Defined in `generic.properties`: `browser.default.width` |
| `browser.height` | optional | Browser window height: `768`, `800`, etc. | Defined in `generic.properties`: `browser.default.height` |
| `wdm.chromeDriverVersion` | optional | Chrome version: `73`, `74`, etc. Used if `browser=chrome`. If not specified, the latest driver version will be used | - |
| `wdm.geckoDriverVersion` | optional | Firefox version, for example, `0.23.0`, `0.24.0`, etc. Used if `browser=firefox`. If not specified, the latest driver version will be used | - |
| `browser.headless` | optional | Defines whether headless mode should be used: `true`, `false` | `false` |
| `use.selenium.grid` | optional | Defines whether Selenium Grid should be used: `true`, `false`| `false` |
| `selenium.grid.url` | mandatory if `use.selenium.grid=true` | Selenium Grid URL | - |
| `parallelTestCount` | optional | Number of tests/browser instances (JVM per test) running in parallel: `2`, `3`, `4`, etc.| `1` |
| `log4j.configuration` | optional | Log4j property file, for example: `file:src/main/resources/log4jdebug.properties` | `file:src/main/resources/log4j.properties` |
| `cucumber.filter.tags` | optional | Cucumber tags to run specific scenarios or features, for example: `@prodserver`, `@stageserver` | - |

Available values of `env` property:

- `devserver`
- `prodserver`

#### Examples

Chrome, dev environment:

```bash
mvn clean test -Denv=devserver -Dbrowser=chrome
```

Chrome headless, development environment, two browsers in parallel:

```bash
mvn clean test -Denv=devserver -Dbrowser=chrome -DparallelTestCount=2 -Dbrowser.headless=true
```

Chrome, development environment, two browsers in parallel, Cucumber features only:

```bash
mvn clean test -Denv=devserver -Dbrowser=chrome -DparallelTestCount=2 -Dtest=*RunnerTest*
```

Chrome, development environment, extended debug log

```bash
mvn clean test -Denv=devserver -Dbrowser=chrome  -Dtest=*RunnerTest* -Dlog4j.configuration=file:src/main/resources/log4jdebug.properties
```

Firefox, development environment, Cucumber features only:

```bash
mvn clean test -Denv=devserver -Dbrowser=firefox -Dtest=*RunnerTest*
```

Firefox, development environment, Selenium Grid:

```bash
mvn clean test -Denv=devserver -Dbrowser=firefox -Duse.selenium.grid=true -Dselenium.grid.url=http://127.0.0.1:4444/wd/hub
```

#### Screenshots for failed test cases

Test suite creates screenshots of a web-page when a regular assert or a selector related assert fails.

Screenshots are saved in the folder `/target/screenshots/`.

#### Generate Surefire HTML report

Use the following command after tests have been performed:

```bash
mvn surefire-report:report-only site
```

Surefire report plugin reads all XML-reports from the folder `/target/surefire-reports` and converts them to
HTML-format. Make sure the folder with XML-reports has relevant data (perform `mvn clean` before you run test cases
with `mvn test`).

Surefire HTML report can be found in the folder `/target/html-reports/surefire-html-report`.

If you want to generate report automatically after tests have been performed, use `mvn surefire-report:report site`
instead of `mvn test`, for example:

```bash
mvn clean surefire-report:report site -Dbrowser=chrome -Denv=devserver
```

Please note that if `mvn surefire-report:report site` is used instead of `mvn test`, the verdict of test cases will be
overridden with the verdict of Surefire report plugin. It means the verdict will always be `BUILD SUCCESS` even if there
are failed test cases.

#### Generate Cucumber HTML report

Use the following command after tests has been performed:

```bash
mvn cluecumber-report:reporting
```

Cluecumber report plugin reads all JSON-reports from the folder `/target/cucumber-report` and converts them to
HTML-format. Make sure the folder with JSON-reports has relevant data (perform `mvn clean` before you run test cases
with `mvn test`).

Cluecumber HTML report can be found in the folder `/target/html-reports/cluecumber-html-report`.
