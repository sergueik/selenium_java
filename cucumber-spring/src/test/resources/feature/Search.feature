Feature: Example Test

  @test
  Scenario: Verify user is able to search on Google
    Given I am on Google page
    When I search for "Google"
    Then I verify the search results are displayed
 
  @test 
   Scenario Outline: Search for keywords that yield results in Firefox
    Given I am on Google page
    When I search for "<queryText>"
    Then I should see "<queryResults>"

    Examples:
      |queryText     |queryResults                                |
      |cucumber bdd  |Cucumber is for Behaviour-Driven Development|
