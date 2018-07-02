@ignore
@Regular
Feature: Login page
  @ignore
  @test
  Scenario: Login Page Security Warning
    Given I go to the start page
    When I go to Account page
    Then The page shows "You must be logged in to use this page."
    Then The page shows "Please use the form below to login to your account."

  # The same step description annotated as When will be found through Given
  @ignore    
  @test
  Scenario: Ability to Login
    Given I go to Account page
    When I enter username "cat" and password "be123456"
    Then I am logged in
    # Then The page shows "Your Account"
  @ignore
  @test
  Scenario Outline: Failed Login
    Given I go to Account page
    When I enter username "<userName>" and password "<Password>"
    Then error message should throw
    Examples:
      |userName         |Password    |
      |cattt            |be123456    |
      |cat              |be123456789 |
      
      
  Scenario Outline: Incomplete Login with empty info
    Given I go to Account page
    When I enter username "<userName>" and password "<Password>"
    Then empty notice message should throw
    Examples:
      |userName         |Password    |
      |                 |    		   |
      
