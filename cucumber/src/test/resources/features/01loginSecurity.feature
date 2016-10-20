Feature: Check security of login page
#here are some comments
  @test  
  Scenario: Check security of login page
    Given I am at the home page
    When Go to page "http://store.demoqa.com/products-page/your-account/?login=1"
    Then the page shows "You must be logged in to use this page."
    Then the page shows "Please use the form below to login to your account."
