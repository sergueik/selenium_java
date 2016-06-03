Feature: shoppingCart function tests

Background:
  Given I am not logged in
    
Scenario: 01 Check out with empty shopping cart
  Given open start page
  When I check the shopping cart
  Then I get empty notice msg    

Scenario: 02 By one item in shopping cart
  Given open start page
  When I buy one item 
  Then I check out 
  Then I log in with my accout
  Then I complete en shopping action
 
Scenario: 03 check item counter 
  Given open start page
  When I buy one item for 3 times
  Then I get item counter will be 3
