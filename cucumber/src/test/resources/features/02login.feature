Feature: Login Feature

  @test    
   Scenario: Check login page
    Given I go to the start page
    When I enter username "cat" and password "be123456"
    Then I am logged in
    
  @test
    Scenario: Customer Login
    Given I go to the home page
    When I continue as "Customer Login"
    Then I should be able to choose customer
    
  @test
    Scenario: Bank Manager Login
    Given I go to the home page
    When I continue as "Bank Manager Login"
    Then I should see "Add Customer"
    And I should see "Open Account"
    And I should see "Customers"

  @positiveScenario
    Scenario Outline: Existing Customer Login 
    Given I go to the home page
    When I continue as "Customer Login"
    When I login as "<FirstName>", "<LastName>"
    Then I am greeted by "<FirstName>", "<LastName>"
    And I can choose my acccounts "<AccountNumbers>"
    Examples:
      |AccountNumbers|FirstName|LastName|
      |1001,1002,1003|Hermoine |Granger |
      |1004,1005,1006|Harry    |Potter  |
    
@negaviveScenario        
Scenario Outline: 01 Unsuccessful login 
  Given I go to the start page
  When I enter username "<userName>" and password "<Password>"
  Then error message should throw
  Examples:
    |userName         |Password    |
    |cattt            |be123456    |
    |cat              |be123456789 |

@negaviveScenario        
Scenario Outline: 02 Unsuccessful login with empty info
  Given I go to the start page
  When I enter username "<userName>" and password "<Password>"
  Then empty notice message should throw
  Examples:
    |userName         |Password    |
    |                 |    		   |
       
