@sample @netIncome
Feature: Net Income Projector
As a user
I want to enter my income and expenses
So that I can see my net income per month and per year

  @netPerMonth
  Scenario Outline: Calculate Net Income Per Month
    Given I Am At The Home Page
    When I Enter My Start Balance: <startBalance>
    And I Enter My Regular Income Sources
      | name   | amount | frequency     |
      | Salary | 25000  | every 2 weeks |
    And I Enter My Regular Expenses
      | name        | amount | frequency     |
      | Electricity | 5500   | Monthly       |
      | Water       | 900    | Weekly        |
      | Internet    | 1900   | Every 2 Weeks |
      | Cable TV    | 555    | Daily         |
    Then I Should See Net Income Per Month: <netPerMonth>

    Examples:
      | startBalance | netPerMonth |
      | 348000       | 23769       |

  @netPerYear
  Scenario Outline: Calculate Net Income Per Year
    Given I Am At The Home Page
    When I Enter My Start Balance: <startBalance>
    And I Enter My Regular Income Sources
      | name   | amount | frequency |
      | Salary | 2180   | work days |
    And I Enter My Regular Expenses
      | name        | amount | frequency     |
      | Electricity | 5500   | Monthly       |
      | Water       | 900    | Weekly        |
      | Internet    | 1900   | Every 2 Weeks |
      | Cable TV    | 555    | Daily         |
    Then I Should See Net Income Per Year: <netPerYear>

    Examples:
      | startBalance | netPerYear |
      | 353000       | 202025     |
