Feature: Find a member
  Scenario: Finding a member
    Given The member id is 2:
    When I make a DELETE call to "api/member/" endpoint
    Then response status code should be 204