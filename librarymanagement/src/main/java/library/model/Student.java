package library.model;

public class Student {
    private int id;
    private String name;
    private String enrollment;
    private String password;

    // student constructor
    public Student( String name, String enrollment, String password) {
        this.name = name;
        this.enrollment = enrollment;
        this.password = password;
    }

    public Student(int id, String name, String enrollment, String password) {
        this.id = id;
        this.name = name;
        this.enrollment = enrollment;
        this.password = password;
    }
    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', enrollment='" + enrollment + "'}";
    }
}
