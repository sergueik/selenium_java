Feature: Search Example

  @test
  Scenario: Verify user is able to search
    Given I open url "http://www.google.com/ncr"
    When I search for "Google"
    Then I verify the search results are displayed
 
  @test 
   Scenario Outline: Validation of search results
    Given I open url "http://www.google.com/ncr"
    When I search for "<queryText>"
    Then I should see "<queryResults>"

    Examples:
      |queryText     |queryResults                                |
      |cucumber bdd  |Cucumber is for Behaviour-Driven Development|

