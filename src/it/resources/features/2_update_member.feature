Feature: update a member
  Scenario: update a member details
    Given The member details are as below:
    |   id   |      name      |    age    |
    |   1    |     Steven     |     68    |
    When I make a PUT call to "api/member" endpoint
    Then response status code should be 204