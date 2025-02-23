Feature: Student workflow
  Scenario: 1 - When get find all students should return all students
    When find all student
    Then return all students in database
