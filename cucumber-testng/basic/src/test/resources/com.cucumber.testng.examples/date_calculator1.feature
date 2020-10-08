@calculator
Feature: Feature1
  This is the description for Feature1

  Scenario: Determine past date one day before today
    Given today is 2015-12-20
    When I ask if Dec 19, 2015 is in the past
    Then the result should be yes

  Scenario: Determine past date ten days before today
    Given today is 2015-12-20
    When I ask if Dec 10, 2015 is in the past
    Then the result should be yes
