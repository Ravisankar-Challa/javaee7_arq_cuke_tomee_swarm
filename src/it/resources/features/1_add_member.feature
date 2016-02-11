Feature: Add a member
  Scenario: Adding a new member
    Given The member details are as below:
    |     name     |    age    |
    |     Sha     |     24    |
    When I make a POST call to "api/member" endpoint
    Then response status code should be 201