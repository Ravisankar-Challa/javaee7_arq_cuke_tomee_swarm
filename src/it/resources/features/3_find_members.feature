Feature: Find a member
  Scenario: Finding a member
    Given The member id is 1:
    When I make a GET call to "api/member/" endpoint
    Then response status code should be 200
    And response content type should be "application/json"
    And response should be json:
    """
    {
	    "id": "${json-unit.ignore}",
	    "name": "Steven",
	    "age": ​68
	}
	"""