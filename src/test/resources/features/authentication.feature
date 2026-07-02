Feature: Authentication workflow

  Scenario: 1 - Register a new user successfully
    When the client sends a POST http request to register a new user
      | fullName     | email                | password  |
      | John Doe     | john.doe@test.com    | pass123   |
    Then return a auth response with status code 200
    And the user was saved in the database
      | fullName     | email                |
      | John Doe     | john.doe@test.com    |

  Scenario: 2 - Register a user with existing email should return bad request
    Given a user in database
      | fullName     | email                | password  |
      | Jane Doe     | jane.doe@test.com    | pass123   |
    When the client sends a POST http request to register a new user
      | fullName     | email                | password  |
      | Jane Doe 2   | jane.doe@test.com    | pass456   |
    Then return a auth response with status code 400

  Scenario: 3 - Login with valid credentials
    Given a user in database
      | fullName     | email                | password  |
      | Test User    | login@test.com       | pass123   |
    When the client sends a POST http request to login
      | email                | password  |
      | login@test.com       | pass123   |
    Then return a auth response with status code 200
    And return a jwt token in the response body

  Scenario: 4 - Login with invalid credentials should return unauthorized
    Given a user in database
      | fullName     | email                | password  |
      | Test User    | invalid@test.com     | pass123   |
    When the client sends a POST http request to login
      | email                | password  |
      | invalid@test.com     | wrongpass |
    Then return a auth response with status code 401

  Scenario: 5 - Login with non-existent email should return unauthorized
    When the client sends a POST http request to login
      | email                | password  |
      | nonexistent@test.com | pass123   |
    Then return a auth response with status code 401
