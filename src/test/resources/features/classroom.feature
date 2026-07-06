Feature: Classroom workflow
  Background:
    Given authentication was performed
    And login was performed
  Scenario: 1 - When the client request all classrooms
    When the client sends a GET http request get all classrooms
    Then return all classrooms in the body of the response
      | response                                                                                                                                                                       |
      | {"classrooms":[{"classroomName":"teste","capacity":0,"teacher":null,"shift":0,"classNumber":0,"classroomReferenceCode":null}]} |
