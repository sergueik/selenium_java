Feature: Login Feature
  # Combining tags does not work  when the same step description is used in both Given and When annotations
  @ignore
  Scenario: Check ability to login
    Given I go to Account page
    When I enter username "cat" and password "be123456"
    Then I am logged in
    Then The page shows "Your Account"