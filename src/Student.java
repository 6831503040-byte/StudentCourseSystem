import java.util.ArrayList;
// Inheritance: Student extends Person
// This is a good design because it allows code reuse.
// Both Student and other possible classes (e.g., Teacher) can share common attributes like 'name'.
// It reduces duplication and makes the system easier to maintain.
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
    // Interface implementation: Student implements Registrable
// Using an interface is a good idea because it defines a contract (behavior) that can be reused.
// It allows flexibility, so other classes can also implement this behavior in the future.
// This improves scalability and supports polymorphism.
    // Prevent duplicate registration (กันลงซ้ำ)
    @Override
    public void registerCourse(Course c) throws CourseFullException {

        // เช็คว่าลงวิชานี้ไปแล้วหรือยัง
        if (!myCourses.contains(c)) {

            // เพิ่มนักเรียนเข้า course
            c.addStudent(this);

            // เพิ่ม course เข้า list
            myCourses.add(c);

        } else {
            // ถ้าลงซ้ำ
            System.out.println("⚠️ Already registered this course.");
        }
    }
    public ArrayList<Course> getMyCourses() {
        return myCourses;
    }

    @Override
    public String toString() {
        return name + " (" + studentId + ")";
    }
    // Drop course (ลบวิชา)
    public void dropCourse(Course c) {
        myCourses.remove(c);
        c.removeStudent(this);
    }
    @Override
    public void addCourse(Course c) throws CourseFullException{
        c.addStudent(this);
        myCourses.add(c);
    }
}