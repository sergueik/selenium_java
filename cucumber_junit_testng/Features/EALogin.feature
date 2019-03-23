@LoginAndLogout 
Feature: Execute Automation-Login and Logout 

Scenario: As a user i need to login to Execute Automation with valid credentials 
	Given by launching a browser 
	Given by navigating to the Execute Automation 
	When by providing username and password 
	Then We will be logged into Execute Automation 
	 Scenario: As a user,i need to perform logout operation 
	Given as already logged in 
	When by logging out
	Then Have to logout
	
	