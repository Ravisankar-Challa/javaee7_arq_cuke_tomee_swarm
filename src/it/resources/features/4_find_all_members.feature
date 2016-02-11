Feature: Find all members
  Scenario: Find all members
    When I make a GET call to find all members to  "api/members" endpoint
    Then response status code should be 200
    And response content type should be "application/json"
    And response should be json:
    """
    [{
	    "id": "${json-unit.ignore}",
	    "name": "Steven",
	    "age": 68
	 },
	{
	    "id": "${json-unit.ignore}",
	    "name": "Sha",
	    "age": 24
	}]
	"""