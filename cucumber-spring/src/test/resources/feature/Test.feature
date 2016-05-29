Feature: Example Test

  @test
  Scenario: Verify user is able to search on Google
    Given I am on Google homepage
    When I search for "Google"
    Then I verify the search results are displayed

  Scenario: Simple clicks
    Given I open url "file:///C:/Cloud/Dropbox/Public/FastTrackIT/simple-webapp/index.html"
    When I click on About menu item
    Then breadcrumb text should be changed to "asta sunt eu"

  Scenario: click on Product sub menu
    Given I open url "https://fuel-3d.com/"
    When I mouse over on element with text "Product"
    And I click on link with text "Compare"
    Then I should see an element with text "Sweeping laser line:"
    And I should be on url "https://fuel-3d.com/compare/"

  Scenario: click on sub menu
    Given I open url "http://dev:J4g^SqXtk%3@staging.fuel-3d.com/"
    When I mouse over on element with text "About"
    And I click on link with text "Meet the team"
    Then I should see an element with text "Meet The Team"
    And I should be on url "http://staging.fuel-3d.com/about-us/meet-the-team/"
 