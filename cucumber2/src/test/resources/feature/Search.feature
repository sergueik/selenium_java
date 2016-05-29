Feature: Example Test

  @test
  Scenario: Verify user is able to search on Google
    Given I am on Google homepage
    When I search for "Google"
    Then I verify the search results are displayed
 