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

