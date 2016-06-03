Feature: Check security of login page
#here are some comments
  
	Scenario: Check security of login page
	  Given open en firefox browser
	  When Go to page "http://store.demoqa.com/products-page/your-account/?login=1"
	  Then the page shows "You must be logged in to use the page"