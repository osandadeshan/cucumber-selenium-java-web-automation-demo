@login
Feature: User Login

  @smoke
  Scenario Outline: User logins with valid credentials
    Given User is on the Login page
    When "<user>" user logins with valid credentials
    Then The user should see the greeting message with the "<user>"'s username

    Examples:
      | user        |
      | WORKER      |
      | ADMIN       |
      | SUPER_ADMIN |

  @regression
  Scenario Outline: User cannot login with invalid credentials
    Given User is on the Login page
    When User logins with username "<username>" and password "<password>"
    Then The user should see the alert for incorrect login credentials

    Examples:
      | username | password |
      | dev-test | NoShits  |
      | dev-test | NoShits@ |
      | dev-test | NoShits# |
