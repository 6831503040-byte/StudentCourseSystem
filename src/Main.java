import java.util.*;
import java.io.FileWriter;
public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        RegistrationSystem system = new RegistrationSystem();

        // Fixed courses
        Course eng = new Course("English", 50);
        Course thai = new Course("Thai", 50);
        Course math = new Course("Math", 60);
        Course sci = new Course("Science", 80);
        Course com = new Course("Computer", 60);

        system.addCourse(eng);
        system.addCourse(thai);
        system.addCourse(math);
        system.addCourse(sci);
        system.addCourse(com);

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Add Student & Register Course");
            System.out.println("2. Show Course Status");
            System.out.println("3. Show Summary");
            System.out.println("4. Search Student");
            System.out.println("5. Save to File");
            System.out.println("6. Drop Course");
            System.out.println("7. Exit");
            System.out.print("Choose (number only): ");

            int choice = getValidInt(sc); // validate numeric input

            switch (choice) {

                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();

                    // Validate student ID (must be 10 digits)
                    String id;
                    while (true) {
                        System.out.print("Enter student ID (10 digits only): ");
                        id = sc.nextLine();

                        if (id.matches("\\d{10}")) {
                            break;
                        } else {
                            System.out.println("❌ Invalid ID! Must be exactly 10 digits.");
                        }
                    }

                    Student s = new Student(name, id);
                    system.addStudent(s);

                    System.out.println("Select 1-5 courses:");

                    for (int i = 0; i < system.getCourses().size(); i++) {
                        System.out.println(i + ": " + system.getCourses().get(i).getCourseName());
                    }

                    int n;
                    while (true) {
                        System.out.print("How many courses (1-5): ");
                        n = getValidInt(sc);

                        if (n >= 1 && n <= 5) break;
                        else System.out.println("❌ Please enter between 1-5.");
                    }

                    for (int i = 0; i < n; i++) {
                        while (true) {
                            try {
                                System.out.print("Choose course index: ");
                                int cIndex = getValidInt(sc);

                                Course c = system.getCourses().get(cIndex);
                                s.registerCourse(c);


                                break;

                            } catch (CourseFullException e) {
                                System.out.println("❌ " + e.getMessage());
                                break;
                            } catch (Exception e) {
                                System.out.println("❌ Invalid input, try again.");
                            }
                        }
                    }
                    break;

                case 2:
                    // show capacity
                    for (Course c : system.getCourses()) {
                        System.out.print(c.getCourseName() + " (" +
                                c.getStudentCount() + "/" + c.getMaxStudents() + ") ");

                        if (c.getStudentCount() >= c.getMaxStudents()) {
                            System.out.println("[FULL]");
                        } else {
                            System.out.println("[Available]");
                        }
                    }
                    break;

                case 3:
                    // summary
                    for (Student stu : system.getStudents()) {
                        System.out.print(stu + " -> ");
                        for (Course c : stu.getMyCourses()) {
                            System.out.print(c.getCourseName() + " ");
                        }
                        System.out.println();
                    }
                    break;

                case 4:
                    System.out.print("Enter student ID: ");
                    String searchId = sc.nextLine();
                    Student found = system.findStudentById(searchId);

                    if (found != null) {
                        System.out.print(found + " -> ");
                        for (Course c : found.getMyCourses()) {
                            System.out.print(c.getCourseName() + " ");
                        }
                        System.out.println();
                    } else {
                        System.out.println("❌ Student not found.");
                    }
                    break;

                case 5:
                    try {
                        FileWriter fw = new FileWriter("students.txt");

                        for (Student stu : system.getStudents()) {
                            fw.write(stu + " -> ");
                            for (Course c : stu.getMyCourses()) {
                                fw.write(c.getCourseName() + " ");
                            }
                            fw.write("\n");
                        }

                        fw.close();
                        System.out.println("✅ Saved to file.");

                    } catch (Exception e) {
                        System.out.println("❌ Error saving file.");
                    }
                    break;
                case 6:
                    System.out.print("Enter student ID: ");
                    String dropId = sc.nextLine();

                    Student stu = system.findStudentById(dropId);

                    if (stu != null) {
                        System.out.println("Your courses:");
                        for (int i = 0; i < stu.getMyCourses().size(); i++) {
                            System.out.println(i + ": " + stu.getMyCourses().get(i).getCourseName());
                        }

                        int idx;

                        while (true) {
                            System.out.print("Select index to drop: ");

                            try {
                                idx = Integer.parseInt(sc.nextLine());

                                if (idx >= 0 && idx < stu.getMyCourses().size()) {
                                    break;
                                } else {
                                    System.out.println("❌ Invalid index! Try again.");
                                }

                            } catch (Exception e) {
                                System.out.println("❌ Please enter numbers only!");
                            }
                        }
                        Course c = stu.getMyCourses().get(idx);
                        stu.dropCourse(c);
                        System.out.println("✅ Dropped successfully.");
                    } else {
                        System.out.println("❌ Student not found.");
                    }
                    break;
                case 7:
                   System.out.println("====== Bye! ======");
                   return;

                default:
                    System.out.println("❌ Invalid menu.");
            }
        }
    }

    // Method to ensure numeric input only
    public static int getValidInt(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                // ถ้า user พิมพ์ตัวอักษร จะเข้า catch
                System.out.print("❌ Error: Please enter numbers only: ");
            }
        }
    }

}