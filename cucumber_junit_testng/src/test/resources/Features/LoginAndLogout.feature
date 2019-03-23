@userStory("U02") 
Feature: Login and Logout 

@scenarioID("U02-TS01") 
Scenario: Login and Logout 
	Given Open Application and Enter url 
	When enter username 
	And  enter password 
	And click on logout 
	Then verify Msg 
	
	