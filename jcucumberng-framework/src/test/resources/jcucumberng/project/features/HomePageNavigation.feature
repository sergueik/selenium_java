@sample @homePage
Feature: Home Page Navigation
	As a user
	I want to visit the home page
	So that I can start using the application

	Scenario: Verify Page Title
		Given I Am At The Home Page
		Then I Should See Page Title: 'Simply Do - Balance Projector'