@login
Feature: User Login

  @smoke
  Scenario Outline: User logins with valid credentials
    Given I am on the Login page
    When I login with "<user>" user
    Then The greeting message should visible with the "<user>"'s username

    Examples:
      | user        |
      | WORKER      |
      | ADMIN       |
      | SUPER_ADMIN |

  @regression
  Scenario Outline: User cannot login with invalid credentials
    Given I am on the Login page
    When I login with username "<username>" and password "<password>"
    Then An alert for incorrect login credentials should visible

    Examples:
      | username | password |
      | dev-test | NoShits  |
      | dev-test | NoShits@ |
      | dev-test | NoShits# |
