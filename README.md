# Cucumber Selenium Java Web Automation Demo

Cucumber code example - A custom framework that runs automated tests written in behaviour driven development (BDD) style.

## Cucumber Introduction

Cucumber is a tool based on Behavior Driven Development (BDD) framework which is used to write acceptance tests for web application. It allows automation of functional validation in easily readable and understandable format (like plain English) to Business Analysts, Developers, Testers, etc. Cucumber feature files can serve as a good document for all. There are many other tools like JBehave which also support BDD framework. Initially Cucumber was implemented in Ruby and then extended to Java framework. Both the tools support native JUnit.

Behavior Driven Development is extension of Test Driven Development, and it's used to test the system rather than testing the particular piece of code.

Cucumber can be used along with Selenium, Watir, and Capybara etc. Cucumber supports many other languages like Perl, PHP, Python, .Net etc. In this tutorial I will concentrate on Cucumber with Java as a language.

**1) Feature Files:**

Feature files are essential part of cucumber which are used to write test automation steps or acceptance tests. This can be used as live document. The steps are the application specification. All the feature files ends with .feature extension.

**2) Scenario:**

Basically a scenario represents a particular functionality which is under test. By seeing the scenario user should be able to understand the intent behind the scenario and what the test is all about. Each scenario should follow given, when and then format. This language is called as “Gherkin”. Gherkin is designed to be easy to learn by non-programmers, yet structured enough to allow concise description of examples to illustrate business rules in most real-world domains.
In Gherkin, each line that isn't blank has to start with a Gherkin keyword, followed by any text you like. The main keywords are:

**Feature:** This gives information about the high level business functionality and the purpose of Application under test.

**Given:** As mentioned above, given specifies the pre-conditions. It is basically a known state.

**When:** This is used when some action is to be performed. As in above example we have seen when the user tries to log in using username and password, it becomes an action.

**Then:** The expected outcome or result should be placed here. For Instance: verify the login is successful, successful page navigation.
Background: Whenever any step is required to perform in each scenario then those steps needs to be placed in Background. For Instance: If user needs to clear database before each scenario then those steps can be put in background.

**And:** And is used to combine two or more same type of action.

**3) Runner:**

To run the specific feature file cucumber uses standard Junit Runner and specify tags in @Cucumber. Options. Multiple tags can be used, separated by comma. 

## Executing Cucumber Tests

**1) To run all the tests in Chrome browser against the QA environment**

   Execute ***`mvn clean test -Pchrome,qa`*** on the terminal.

**2) To run all the smoke tests in Firefox browser against the QA environment**
   
   Execute ***`mvn clean test -Pfirefox,qa -Dcucumber.filter.tags="@smoke"`*** on the terminal.

## Test Reporting

After the execution is completed, you will be able to see the report at "***target/cucumber-reports/cucumber.html***".

## Test Coverage Report

This project automatically tracks and reports E2E automation coverage per feature using a CSV file, Python, and GitHub Actions.

### Setup

**1. Register your features in the CSV**

Add a row for every feature file in `src/test/resources/features/planned-automation-tests.csv`:

```csv
Feature,Planned Automation Test Count
Login,6
Navigation Bar,12
```

Each `Feature` value must match the name of its corresponding `Feature:` name in the `.feature` file. The `Planned Automation Test Count` value is the planned scenario count — how many E2E scenarios you intend to automate for that feature.

**2. Keep it updated**

Whenever you add a new `.feature` file, add a matching row to the CSV in the same PR. If you forget, the report will show a warning banner at the top of the dashboard and flag that feature row in the table.

### Prerequisites

The following must be in place for the pipeline to generate the report:

| Requirement | Details |
|---|---|
| `planned-automation-tests.csv` | Populated with all feature names and planned counts |
| `GITHUB_TOKEN` | Automatically provided by GitHub Actions — no manual setup needed |
| `GITHUB_REPOSITORY` | Automatically provided by GitHub Actions — no manual setup needed |
| `.feature` files | Present under `src/test/resources/features/` |

### How It Works

The CI pipeline handles everything automatically.

**On a pull request** — if any `.feature` file or `planned-automation-tests.csv` changed, the pipeline generates a coverage report and posts it as a comment directly on the PR. No feature file changes? All coverage steps are silently skipped. Build and test still run as normal.

**On merge to master** — if the merged PR contained feature file or `planned-automation-tests.csv` changes, the pipeline regenerates the report from the latest codebase and publishes the updated dashboard to GitHub Pages automatically.

### What You Get

**1. PR Comment**

A Markdown coverage table posted directly on the pull request showing per-feature planned, automated, and missing counts — so the team gets visibility without leaving GitHub.

**2. Build Artifact**

The generated `feature-coverage.html` is uploaded as a downloadable artifact on every qualifying PR run. You can find it under the **Actions** tab → select the run → **Artifacts** section.

**3. Live Dashboard on GitHub Pages**

Hosted at `https://<your-org>.github.io/<repo-name>/`, the dashboard updates automatically on every qualifying merge and includes:

- **Summary cards** — Overall Coverage %, Total Planned, Total Automated, and Total Missing at a glance
- **Search** — filter the feature table by name in real time
- **Sortable columns** — click any column header to sort; sort by Coverage % ascending to instantly surface the lowest covered features
- **Coverage tier filter** — filter by ≥ 90% 🟢, 80–89% 🟡, or < 80% 🔴
- **CSV warning** — a banner and inline row flag highlight any feature file not registered in the CSV

Read more: [Medium Article](https://medium.com/automationmaster/my-engineering-manager-asked-for-a-test-coverage-dashboard-heres-what-i-built-55b81b789327)

## License
<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/0b/License_icon-mit-2.svg/2000px-License_icon-mit-2.svg.png" alt="MIT License" width="100" height="100"/> [MIT License](https://opensource.org/licenses/MIT)

## Copyright
Copyright 2023 [MaxSoft](https://maxsoftlk.github.io/).
