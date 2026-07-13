Feature: Classroom workflow
  Background:
    Given authentication was performed
    And login was performed

  Scenario: 1 - When the client request all classrooms
    Given a classroom in database
    When the client sends a GET http request get all classrooms
    Then return all classrooms in the body of the response
      | response                                                                                                                                                                                                       |
      | {"classrooms":[{"classroomName":"teste","teacher":"Professor Teste","capacity":0,"classNumber":0,"shift":0,"classroomReferenceCode":"a1b2c3d4-e5f6-7890-abcd-ef1234567890"}]} |

  Scenario: 2 - When the client request classroom students by classroom reference code
    Given a classroom in database
    And a student in this classroom
    When the client sends a GET http request get classroom students
    Then return all classroom students
      | studentName   | studentEmail    | classroomName | registration |
      | Test Student  | test@test.com   | teste         | abc1234      |
