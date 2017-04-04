Feature: Entries

  @logout
  Scenario: Fetch empty list of entries
    When I log in
    And I "GET" the path "entry/user/1"
    Then the response is 200
    And the body is
    """
    []
    """