import java.util.*;

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
            System.out.println("4. Exit");
            System.out.print("Choose (number only): ");

            int choice = getValidInt(sc); // ✅ กัน input ผิด

            switch (choice) {

                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();

                    // ✅ ตรวจสอบ Student ID
                    String id;
                    while (true) {
                        System.out.print("Enter student ID (10 digits only): ");
                        id = sc.nextLine();

                        if (id.matches("\\d{10}")) {
                            break; // ถูกต้อง
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
                        System.out.println(
                                c.getCourseName() + ": " +
                                        c.getStudentCount() + "/" +
                                        c.getMaxStudents()
                        );
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
                    System.out.println("=====Bye!=====");
                    return;

                default:
                    System.out.println("❌ Invalid menu.");
            }
        }
    }

    // ✅ Helper method: รับเฉพาะตัวเลข
    public static int getValidInt(Scanner sc) {
        while (true) {
            try {
                int value = Integer.parseInt(sc.nextLine());
                return value;
            } catch (Exception e) {
                System.out.print("❌ Please enter numbers only: ");
            }
        }
    }
}