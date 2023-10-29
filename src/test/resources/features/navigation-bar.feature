@navigation
Feature: Navigation Bar

  @smoke
  Scenario Outline: User can see the Navigation Bar options
    Given The Navigation Bar is on the top of the page
    When User checks the Navigation Bar option "<navigationBarOption>"
    Then The user should see the Navigation Bar option

    Examples:
      | navigationBarOption |
      | HOME                |
      | CONTACT             |
      | ABOUT_US            |
      | CART                |
      | LOG_IN              |
      | SIGN_UP             |

  @regression
  Scenario Outline: User can see the Navigation Bar option routes
    Given The Navigation Bar is on the top of the page
    When User checks the Navigation Bar option "<navigationBarOption>"
    Then The user should see the Navigation Bar option routes to "<navigationBarOptionUrl>"

    Examples:
      | navigationBarOption | navigationBarOptionUrl |
      | HOME                | index.html             |
      | CONTACT             | #                      |
      | ABOUT_US            | #                      |
      | CART                | cart.html              |
      | LOG_IN              | #                      |
      | SIGN_UP             | #                      |