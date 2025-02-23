# College Management System
```
CollegeManagementSystem/
│
├── src/
│   ├── models/
│   │   ├── User.java
│   │   ├── Manager.java
│   │   ├── Student.java
│   │   ├── Teacher.java
│   │
│   ├── repository/
│   │   ├── UserRepository.java
│   │   ├── AttendanceRepository.java
│   │   ├── StudentsMarksRepo.java
│   │   ├── FileHandler.java
│   │
│   ├── UI/
│   │   ├── ANSIColors.java
│   │
│   ├── Main.java
│
├── data/
│   ├── attendance.txt
│   ├── studentsMarks.txt
│   ├── users.txt
│
└── README.md (optional)
```




---

### UML Diagram for the Project


#### UML Class Diagram

```plantuml
@startuml

' Define classes
class User {
    - String id
    - String password
    - String role
    - String name
    + String getId()
    + String getPassword()
    + String getRole()
    + String getName()
    + String getMenu()
    + String viewProfile()
    + boolean authenticate(String inputPassword)
}

class Manager {
    + Manager(String id, String password, String name)
    + String getMenu()
    + void handleMenu(UserRepository userRepository, Scanner scanner)
    + boolean addNewUser(UserRepository userRepository)
    + void viewAllUsers(UserRepository userRepository)
    + boolean deleteUser(UserRepository userRepository, Scanner scanner)
    + String viewProfile()
}

class Student {
    - String gpa
    - List<String> courses
    - AttendanceRepository attendanceRepo
    - StudentsMarksRepo marksRepo
    + Student(String id, String password, String name, String gpa, List<String> courses)
    + String getGpa()
    + List<String> getCourses()
    + String getMenu()
    + void handleMenu(Scanner scanner)
    + void viewCourses()
    + void viewAttendance()
    + void viewMarks()
    + void reportToTeacher(Scanner scanner)
    + void getGPA()
    + String viewProfile()
}

class Teacher {
    - List<String> courses
    - AttendanceRepository attendanceRepo
    - StudentsMarksRepo marksRepo
    + Teacher(String id, String password, String name, List<String> courses)
    + List<String> getCourses()
    + String getMenu()
    + void handleMenu(Scanner scanner)
    + void viewCourses()
    + void viewAttendance()
    + void editStudentMarks(Scanner scanner)
    + void addCoursesForStudent(Scanner scanner)
    + String viewProfile()
}

class UserRepository {
    + User authenticateUser(String username, String password)
    + boolean addUser(User user)
    + boolean deleteUser(String id, String password)
    + List<String> getAllUsers()
    + boolean isUserExist(String id)
    + List<String> getStudentCourses(String studentId)
    + void updateStudentCourses(String studentId, List<String> courses)
}

class AttendanceRepository {
    - FileHandler fileHandle
    + AttendanceRepository()
    + void recordAbsence(String userId, String date)
    + List<String> getAbsenceRecords(String userId)
    + boolean isAbsentOnDate(String userId, String date)
    + void removeAbsence(String userId, String date)
}

class StudentsMarksRepo {
    - FileHandler fileHandle
    + StudentsMarksRepo()
    + List<String> getCoursesForStudent(String studentId)
    + List<String> getMarksForStudent(String studentId)
    + void addOrUpdateMarks(String studentId, String course, String marks)
}

class FileHandler {
    + void initializeFile(String fileName)
    + List<String> readLines(String fileName)
    + void writeFile(String fileName, String content)
}

class Main {
    + static void main(String[] args)
}

' Define relationships
User <|-- Manager
User <|-- Student
User <|-- Teacher

UserRepository --> User
AttendanceRepository --> FileHandler
StudentsMarksRepo --> FileHandler
Main --> UserRepository
Main --> Manager
Main --> Student
Main --> Teacher

@enduml
