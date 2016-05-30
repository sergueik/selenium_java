Feature: Search Example

  @test
  Scenario: Verify user is able to search
    Given I open url "http://www.google.com/ncr"
    When I search for "Google"
    Then I verify the search results are displayed
 
