// Interface: defines a common behavior for objects that can register courses
// This is useful because it separates "what to do" from "how to do it"
// making the system more flexible and easier to extend.
public interface Registrable {
    void addCourse(Course c) throws CourseFullException;
}

