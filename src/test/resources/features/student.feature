Feature: Student workflow
  Scenario: 1 - When get find all students should return all students
    When find all student
    Then return all students in database

  Scenario: 2 - When find student by registration should return a student with that registration
    When find student by registration "m34m1en"
    Then return a student in database
