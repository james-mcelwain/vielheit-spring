Feature: Users IT

  Scenario: Unauthorized
    When I "GET" the path "users/1"
    Then the error response is 401
    And the error body is
    """
     "Authentication failed"
    """
    And the errorCode is 10

  Scenario: Log in
    When I log in
    And I "GET" the path "users/1"
    Then the response is 200
    And the body is
    """
    {
      "id": 1,
      "active": true,
      "firstName": "admin",
      "lastName": "admin",
      "emailAddress": "admin@vielhe.it",
      "roles": [ { "role": "ADMIN" }, { "role": "REFRESH_TOKEN" } ] }
    """

  Scenario: Get user incorrectly
    When I "GET" the path "users/2"
    Then the error response is 401

  Scenario: Register A User
    When I "POST" to "auth/register" with
    """
    {
      "firstName": "cucmber",
      "lastName": "int",
      "emailAddress": "test1@vielhe.it",
      "password": "cucumber_testing"
    }
    """
    Then the response is 200
    And the body is
    """
    {
      "active": true,
      "emailAddress": "test1@vielhe.it",
      "firstName": "cucmber",
      "id": 2,
      "lastName": "int",
      "roles": [ { "role": "USER" } ]
    }
    """

  Scenario: Log in new user
    When I "POST" to "auth/login" with
    """
    {
      "emailAddress": "test1@vielhe.it",
      "password": "cucumber_testing"
    }
    """
    Then the response is 200