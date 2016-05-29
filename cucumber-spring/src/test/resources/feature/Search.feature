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

    @test
    Scenario Outline: test local file
    Given I open url "file:///c/developer/sergueik/selenium_java/cucumber-spring.BACKUP/src/test/resources/pages/ng_basic.htm"
    When I search for "Google"
    Then I verify the search results are displayed
    
    @test
    Scenario Outline: test local file
    Given I open url "file://${project.basedir}/src/test/resources/pages/ng_basic.htm"
    When I search for "Google"
    Then I verify the search results are displayed    