// Interface: defines behavior for classes that can register courses
public interface Registrable {
    void registerCourse(Course c) throws CourseFullException;
}
