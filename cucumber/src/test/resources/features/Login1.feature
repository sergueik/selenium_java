Feature: Check security of login page
  #here are some comments
  @test
  Scenario: Check security of login page
    Given I go to the start page
    When I go to Account page
    Then The page shows "You must be logged in to use this page."
    Then The page shows "Please use the form below to login to your account."