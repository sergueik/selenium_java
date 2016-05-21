Feature: test feature for Page Object Framework 

@Contact 
Scenario: This is 1st Scenario

	Given I navigate to zoo site 
	When I click on a "contact" button 
	Then I check I am on contact page 
	Then I fill in the Contact Form 
		|Fields 		| Value				|
		|Name   		| Rajeswar			|
		|Address		| Irvine			|
		|PostCode		| 92620				|
		|Email			| rajesh@gmail.com	|
		
	Then I submit the form 
	Then I check the confirmation message 
	Then I close the Browser 
	
#@Adoption 
#Scenario: this scenario is to verify the adoption page functionality 

	#Given I navigate to zoo site 
	#When I click on Adoption link 
	#Then I select start date 
	#Then I check animal availability 
	#Then I check I am on Adoption Form 
	#Then I close the Browser 
 