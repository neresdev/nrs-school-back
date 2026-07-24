Feature: Subject workflow
  Background:
    Given authentication was performed
    And login was performed

  Scenario: 1 - When the client request all subjects and there are subjects in database
    Given subject in database
      | subjectDescription |
      | Math               |
      | Science            |
    When the client sends a GET http request get all subjects
    Then return all subjects
      | subjectDescription |
      | Math               |
      | Science            |

  Scenario: 2 - When the client request all subjects but there are no subjects
    When the client sends a GET http request get all subjects
    Then return an empty list of subjects
