Feature: Student workflow
  Scenario: 1 - When get find all students should return all students
    When find all student
    Then return all students in database
      | studentId | studentName | studentEmail           | classRoomId | registration |
      | 1         | Student 1   | student1@fakeemail.com | 1           | m423af1      |
      | 2         | Student 2   | student2@fakeemail.com | 1           | m34m1en      |


  Scenario: 2 - When find student by registration should return a student with that registration
    When find student by registration "m34m1en"
    Then return a student in database
      | studentId | studentName | studentEmail           | classRoomId | registration |
      | 2         | Student 2   | student2@fakeemail.com | 1           | m34m1en      |

  Scenario: 3 - When find student by registration not found should return an exception
    When find student by registration "m34m1ea"
    Then throw an student not found with registration "m34m1ea" not found

  Scenario: 4 - When create a student then return a url from the student created
    When create a student
      | studentId | studentName | studentEmail           | classRoomId | registration |
      | 1         | Student 1   | student1@fakeemail.com | 1           | m423af2      |
    Then return a url form the student created

  Scenario: 5 - When create student with invalid data should return an exception
    When create a student
      | studentId | studentName | studentEmail | classRoomId | registration |
      | 1 | Student 1 |  | 1 | m423af2 |
    Then throw an data integraty violation exception

  Scenario: 6 - When create existing student return an exception
    When create a student
      | studentId | studentName | studentEmail           | classRoomId | registration |
      | 1         | Student 1   | student1@fakeemail.com | 1           | m423af2      |
    And create a student with existing registration
      | studentId | studentName | studentEmail           | classRoomId | registration |
      | 2         | Student 2   | student2@fakeemail.com | 1           | m423af2      |
    Then return an student already exists exception