Feature: Student workflow
  Scenario: 1 - When get find all students should return all students
    When find all student
    Then return all students
      | studentId | studentName | studentEmail           | classroomName | registration |
      | 1         | Student 1   | student1@fakeemail.com | 4°B           | m423af1      |
      | 2         | Student 2   | student2@fakeemail.com | 4°B           | m34m1en      |

  Scenario: 2 - When find student by registration should return a student with that registration
    When find student by registration "m34m1en"
    Then return a student
      | studentId | studentName | studentEmail           | classroomName | registration |
      | 2         | Student 2   | student2@fakeemail.com | 4°B             | m34m1en      |

  Scenario: 3 - When find student by registration not found should throw an exception
    When find student by registration "m34m1ea"
    Then throw an student not found with registration "m34m1ea" not found

  Scenario: 4 - When create a student should return a url from the student created
    When create a student
      | studentId | studentName | studentEmail           | classroomName | registration |
      | 1         | Student 1   | student1@fakeemail.com | 4°B             | m423af2      |
    Then return a url form the student created

  Scenario: 5 - When create student with invalid data should throw an exception
    When create a student
      | studentId | studentName | studentEmail | classroomName | registration |
      | 1         | Student 1   |              | 4°B             | m423af2      |
    Then throw an data integrity violation exception

  Scenario: 6 - When create existing student should return an exception
    When create a student
      | studentId | studentName | studentEmail           | classRoomId | registration |
      | 1         | Student 1   | student1@fakeemail.com | 4°B           | m423af2      |
    And create a student with existing registration
      | studentId | studentName | studentEmail           | classRoomId | registration |
      | 2         | Student 2   | student2@fakeemail.com | 4°B           | m423af2      |
    Then return an student already exists exception

  Scenario: 7 - When update a student then return a url from student updated
    Given student in database
      | studentName | studentEmail                  | classroomId | registration |
      | Student 3   | student3updated@fakeemail.com | 1           | m423af9      |
    When update student
      | studentId | studentName | studentEmail                  | classroomName | registration |
      | 3         | Student 3   | student3updated@fakeemail.com | 4°B             | m423af9      |
    Then return a url form the student with fields updated
      | studentId | studentName | studentEmail                  | classroomName | registration |
      | 3         | Student 3   | student3updated@fakeemail.com | 4°B             | m423af9      |

  Scenario: 8 - Delete student by registration
    Given student in database
      | studentName | studentEmail           | classroomId | registration |
      | Student 4   | student4@fakeemail.com | 1           | 1n2h4n1      |
    When delete student by registration "1n2h4n1"
    Then there must be no students with registration "1n2h4n1"