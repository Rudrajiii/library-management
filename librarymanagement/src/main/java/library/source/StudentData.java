package library.source;

import library.model.Student;

public class StudentData {
    private Student[] students = {
        new Student("Rudra", "ENR1001", "pass123"),
        new Student("Siya", "ENR1002", "siya@321"),
        new Student("Arjun", "ENR1003", "arj!un99"),
        new Student("Meera", "ENR1004", "meera#45"),
        new Student("Kabir", "ENR1005", "kabir2025"),
        new Student("Aanya", "ENR1006", "aanya_pw"),
        new Student("Rohan", "ENR1007", "rohan$88"),
        new Student("Ishita", "ENR1008", "ishita007"),
        new Student("Kunal", "ENR1009", "kunal@java"),
        new Student("Neha", "ENR1010", "neha_456"),
        new Student("Dev", "ENR1011", "devpass!"),
    };

    public Student[] get_student_data(){
        return students;
    }
}

