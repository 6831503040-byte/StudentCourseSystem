import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

// Core system
public class RegistrationSystem {

    private ArrayList<Student> students;
    private ArrayList<Course> courses;

    public RegistrationSystem() {
        students = new ArrayList<>();
        courses = new ArrayList<>();
    }

    public void addStudent(Student s) {
        students.add(s);
    }

    public void addCourse(Course c) {
        courses.add(c);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    // File Write
    public void saveStudentsToFile() {
        try {
            FileWriter writer = new FileWriter("students.txt");

            for (Student s : students) {
                writer.write(s.getName() + "," + s.getStudentId() + "\n");
            }

            writer.close();
            System.out.println("Saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    // File Read
    public void loadStudentsFromFile() {
        try {
            File file = new File("students.txt");

            if (!file.exists()) return;

            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(",");
                students.add(new Student(data[0], data[1]));
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Error loading file.");
        }
    }
}
