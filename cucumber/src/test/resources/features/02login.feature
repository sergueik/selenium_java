Feature: Login Functionality Feature

In order to ensure Login Functionality works,
I want to run the cucumber test to verify it is working

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
       
@positiveScenario
Scenario Outline: 03 Successful login 
  Given I go to the start page
  When I enter username "<userName>" and password "<Password>"
  Then I am logged in
  Examples:
    |userName       |Password |
    |cat            |be123456 |                