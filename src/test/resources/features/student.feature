Feature: Student workflow
  Background:
    Given authentication was performed
    And login was performed
  Scenario: 1 - When the client request all students
    When the client sends a GET http request get all students
    Then return all students
      | studentId | studentName  | studentEmail           | classroomName | registration |
      | 1         | Diego Stretz | student1@fakeemail.com | 4°B           | m423af1      |
      | 2         | Bruno Silva  | student2@fakeemail.com | 4°B           | m34m1en      |

  Scenario: 2 - When the client request a student with a specific registration
    When the client sends a GET http request a student with registration "m34m1en"
    Then return a student
      | studentId | studentName | studentEmail           | classroomName | registration |
      | 2         | Bruno Silva | student2@fakeemail.com | 4°B           | m34m1en      |

  Scenario: 3 - the client request a student with a specific registration but no one is found
    When the client sends a GET http request a student with registration "m34m1ea"
    Then return a response with status code 404

  Scenario: 4 - When create a student without a classroom should return a url from the student created
    When create a single student
      | studentName | studentEmail           | registration |
      | Student 3   | student3@fakeemail.com | n3j1o2h      |
    Then return a url form the student created with registration "n3j1o2h"
    And the students were saved in the database
      | studentName | studentEmail           | registration |
      | Student 3   | student3@fakeemail.com | n3j1o2h      |

  Scenario: 5 - When create student with invalid data should return an response with bad request code
    When create a single student
      | studentName | studentEmail | registration |
      | Student 3   |              | n3j1o2h      |
    Then return a response with status code 400

  Scenario: 6 - When create existing student should return an exception
    When create a single student
      | studentId | studentName | studentEmail           | classRoomId | registration |
      | 1         | Student 1   | student1@fakeemail.com | 4°B         | m423af1      |
    Then return a response with status code 400

  Scenario: 7 - When client make a put request, then return a url from student updated
    When update student
      | studentId | studentName | studentEmail                  | classroomName | registration |
      | 3         | Student 3   | student3updated@fakeemail.com | 4°B           | m423af9      |
    Then return a url form the student updated with registration "m423af9"
    And the students were saved in the database
      | studentName | studentEmail                  | registration |
      | Student 3   | student3updated@fakeemail.com | m423af9      |

  Scenario: 8 - Delete student by registration
    Given student in database
      | studentName | studentEmail           | registration |
      | Student 4   | student4@fakeemail.com | 1n2h4n1      |
    When delete student by registration "1n2h4n1"
    Then there must be no students with registration "1n2h4n1" in database