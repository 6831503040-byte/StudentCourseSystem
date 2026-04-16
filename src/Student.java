import java.util.ArrayList;

public class Student extends Person implements Registrable {
    private String studentId;
    private ArrayList<Course> myCourses;

    public Student(String name, String studentId) {
        super(name);
        this.studentId = studentId;
        myCourses = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    @Override
    public void registerCourse(Course c) throws CourseFullException {
        c.addStudent(this);
        myCourses.add(c);
    }

    public ArrayList<Course> getMyCourses() {
        return myCourses;
    }

    @Override
    public String toString() {
        return name + " (" + studentId + ")";
    }
}