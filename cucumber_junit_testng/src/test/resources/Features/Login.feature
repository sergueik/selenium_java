@userStory("U01") 
Feature: Login Feature 
Background: Testing the login functionality (or) feature of an application 
	Given As an existing user 
	
@scenarioID("U01-TS01") 
Scenario: Authenication 
	Given Open Application and Enter url 
	When enter username 
	And  enter password 
	Then verify Msg 
	
	
 