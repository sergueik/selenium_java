Feature: Login page

  @test
  Scenario: Check Security Warning of Login Page
    Given I go to the start page
    When I go to Account page
    Then The page shows "You must be logged in to use this page."
    Then The page shows "Please use the form below to login to your account."
    
  # The same step description is possible to use in both Given and When annotations
  @test
  Scenario: Check ability to login
    Given I go to Account page
    When I enter username "cat" and password "be123456"
    Then I am logged in
    # Then The page shows "Your Account"    