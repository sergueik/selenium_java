# comment to get ignored
@calculator
@data
Feature: Feature2
  This is the description for Feature2

  Scenario: Determine future date one day after today with input data table
    Given today is the following date
    |2015-12-20|
    When I ask if Dec 21, 2015 is in the past
    Then the result should be no

  Scenario: Determine future date ten days after today
    Given today is 2015-12-20
    When I ask if Dec 30, 2015 is in the past
    Then the result should be no