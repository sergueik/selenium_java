Feature: Local Page Test Example

    @test
    Scenario Outline: test local file
    Given I open url "file://${project.basedir}/src/test/resources/pages/ng_basic.htm"
    When I search for "Google"
    Then I verify the search results are displayed