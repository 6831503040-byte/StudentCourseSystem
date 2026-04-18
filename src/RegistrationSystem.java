import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
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

                // เขียน id และ name
                writer.write(s.getStudentId() + "," + s.getName() + ",");

                // เขียน course
                ArrayList<Course> courses = s.getMyCourses();

                for (int i = 0; i < courses.size(); i++) {
                    writer.write(courses.get(i).getCourseName());

                    if (i < courses.size() - 1) {
                        writer.write("|"); // คั่น |
                    }
                }

                writer.write("\n");
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
            if (sc.hasNextLine()) sc.nextLine(); // skip header

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] data = line.split(",", 3); // limit 3 ช่อง

                if (data.length < 3) continue;

                String studentId = data[0];
                String name = data[1];

                // create student
                Student student = new Student(name, studentId);
                //สร้าง key map value
                //Map<String, Course> courseMap = new HashMap<>();
                // parse courses
                String[] courseArr = data[2].split("\\|");

                for (String cStr : courseArr) {
                    //String[] parts = cStr.split(":");

                    //String cName = parts[0];
                    //int max = Integer.parseInt(parts[1]);

                    //ใช้ shared object
                    //Course c = courseMap.computeIfAbsent(
                            //cName,
                            //k -> new Course(cName, max)
                    //);

                    //try {
                        //student.registerCourse(c);
                    //} catch (CourseFullException e) {
                       // System.out.println(e.getMessage());
                   // }
                    String cName = cStr.trim();

                    // หา course จาก list หลัก
                    Course c = null;
                    for (Course existing : courses) {
                        if (existing.getCourseName().equals(cName)) {
                            c = existing;
                            break;
                        }
                    }

                    // ถ้าเจอ → register
                    if (c != null) {
                        try {
                            student.registerCourse(c);
                        } catch (CourseFullException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }

                students.add(student);
            }
            sc.close();

        } catch (Exception e) {
            System.out.println("Error loading file.");
        }
    }
    //loadcourse
    public void loadCourseFromFile() {
        try {
            File file = new File("course.txt");

            if (!file.exists()) return;

            Scanner sc = new Scanner(file);
            if (sc.hasNextLine()) sc.nextLine(); // skip header

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] data = line.split(",", 2); // limit 2 ช่อง

                if (data.length < 2) continue;

                String name = data[0];
                //convert string to int
                int max = Integer.parseInt(data[1]);

                // create student
                Course course = new Course(name,max);
                courses.add(course);
            }
            sc.close();

        } catch (Exception e) {
            System.out.println("Error loading file.");
        }
    }
    // ค้นหานักเรียนจาก ID
    public Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getStudentId().equals(id)) {
                return s;
            }
        }
        return null;
    }
    public boolean isDuplicateCourse(Student stu, String courseName){
        boolean isDupplicate = false;
        for (int i = 0; i < stu.getMyCourses().size(); i++) {
            String studentCourseName = stu.getMyCourses().get(i).getCourseName();
            if(studentCourseName.equals(courseName)){
                isDupplicate = true;
                break;
            }
        }
        return isDupplicate;
    }

}
