import java.util.ArrayList;

public class Course {
    private String courseName;
    private int maxStudents;
    private ArrayList<Student> students;

    public Course(String courseName, int maxStudents) {
        this.courseName = courseName;
        this.maxStudents = maxStudents;
        students = new ArrayList<>();
    }

    public void addStudent(Student s) throws CourseFullException {
        if (students.size() >= maxStudents) {
            throw new CourseFullException(courseName + " is full!");
        }

        if (!students.contains(s)) {
            students.add(s);
        }
    }

    public String getCourseName() {
        return courseName;
    }

    public int getStudentCount() {
        return students.size();
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
}