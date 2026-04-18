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

    // File Write (Save File)
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

            if (!file.exists())
                return;

            Scanner sc = new Scanner(file);
            if (sc.hasNextLine()) sc.nextLine(); // skip header

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) //ข้ามบรรทัดว่าง
                    continue;
                //แยกข้อมูล
                String[] data = line.split(",", 3); // limit 3 ช่อง

                if (data.length < 3)
                    continue;

                String studentId = data[0];
                String name = data[1];

                // create student
                Student student = new Student(name, studentId);

                // แยกข้อมูล
                String[] courseArr = data[2].split("\\|");
                Map<String, Course> courseMap = new HashMap<>();

                for (Course c : courses) {
                    courseMap.put(c.getCourseName(), c);
                }
                for (String cStr : courseArr) {
                    String cName = cStr.trim();

                    Course c = courseMap.get(cName);

                    if (c != null) {
                        try {
                            student.addCourse(c);
                        } catch (CourseFullException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                /*
                for (String cStr : courseArr) { // cStr คือ String ที่มาจากไฟล์

                    String cName = cStr.trim();// .trim ใช้ลบช่องว่างหน้าหลัง

                    // หา course จาก list หลัก
                    Course c = null;
                    for (Course course : courses) {
                        if (course.getCourseName().equals(cName)) {
                            c = course;
                            break;
                        }
                    }

                    // ถ้าเจอ ให้ register
                    if (c != null) {
                        try {
                            student.addCourse(c);
                        } catch (CourseFullException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }

                 */

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

            if (!file.exists())
                return;

            Scanner sc = new Scanner(file);
            if (sc.hasNextLine()) sc.nextLine(); // skip header

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty())
                    continue;

                String[] data = line.split(",", 2); // limit 2 ช่อง

                if (data.length < 2)
                    continue;

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
    //หาวิชาที่ซ้ำ
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
