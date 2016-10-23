@Angular
Feature: Angular Calculator Protractor Example
  
  @test
    Scenario: Simple Protractor page manipulation
    Given I open angular page url "http://juliemr.github.io/protractor-demo/"
    When I enter "40" into "first"
    And I enter "2" into "second"
    And I evaluate result
    Then I should get "42"