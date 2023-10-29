@navigation
Feature: Navigation Bar

  @smoke
  Scenario Outline: User can see the Navigation Bar options
    Given The Navigation Bar is available
    When I check the Navigation Bar option "<navigationBarOption>"
    Then The Navigation Bar option should visible

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
    Given The Navigation Bar is available
    When I check the Navigation Bar option "<navigationBarOption>"
    Then The Navigation Bar option should route to "<navigationBarOptionUrl>"

    Examples:
      | navigationBarOption | navigationBarOptionUrl |
      | HOME                | index.html             |
      | CONTACT             | #                      |
      | ABOUT_US            | #                      |
      | CART                | cart.html              |
      | LOG_IN              | #                      |
      | SIGN_UP             | #                      |