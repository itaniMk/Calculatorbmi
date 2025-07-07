import java.util.Locale;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class calculatorbmi {

    private static final String USERNAME = "adminBafo";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        if (authenticateUser(scanner)) {
            char repeat;

            do {
                int weightUnit = getWeightUnitChoice(scanner);
                int heightUnit = getHeightUnitChoice(scanner);

                double weight = (weightUnit == 1)
                        ? getValidInput(scanner, "Enter your weight in kilograms:", 10, 600)
                        : getValidInput(scanner, "Enter your weight in pounds:", 22, 1300);

                double height = (heightUnit == 1)
                        ? getValidInput(scanner, "Enter your height in meters:", 0.5, 2.5)
                        : getValidInput(scanner, "Enter your height in inches:", 20, 100);

                double bmi = calculateBMI(weightUnit, heightUnit, weight, height);
                System.out.printf("Your BMI is %.2f%n", bmi);

                String category = getBMICategory(bmi);
                System.out.println("BMI Category: " + category);

                // Record current date and time
                LocalDateTime dateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDate = dateTime.format(formatter);

                // Display the BMI report
                String report = String.format(
                        "\nBMI REPORT\n------------------\n" +
                        "Date and Time: %s\n" +
                        "Weight: %.2f %s\n" +
                        "Height: %.2f %s\n" +
                        "BMI: %.2f\n" +
                        "Category: %s\n",
                        formattedDate,
                        weight, (weightUnit == 1 ? "kg" : "lbs"),
                        height, (heightUnit == 1 ? "m" : "in"),
                        bmi, category
                );

                System.out.println(report);

                repeat = askToRepeat(scanner);
                System.out.println();

            } while (repeat == 'Y' || repeat == 'y');

        } else {
            System.out.println("Authentication failed. Exiting program.");
        }

        scanner.close();
    }

    public static boolean authenticateUser(Scanner scanner) {
        System.out.println("=== BMI Calculator Login ===");
        System.out.print("Enter username: ");
        String inputUsername = scanner.nextLine();

        System.out.print("Enter password: ");
        String inputPassword = scanner.nextLine();

        return inputUsername.equals(USERNAME) && inputPassword.equals(PASSWORD);
    }

    public static int getWeightUnitChoice(Scanner scanner) {
        int choice;
        while (true) {
            System.out.println("\nSelect weight unit:");
            System.out.println("1. Kilograms (kg)");
            System.out.println("2. Pounds (lbs)");
            System.out.print("Enter 1 or 2: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 1 || choice == 2) {
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }
            } else {
                System.out.println("Invalid input. Please enter 1 or 2.");
                scanner.next();
            }
        }
        return choice;
    }

    public static int getHeightUnitChoice(Scanner scanner) {
        int choice;
        while (true) {
            System.out.println("\nSelect height unit:");
            System.out.println("1. Meters (m)");
            System.out.println("2. Inches (in)");
            System.out.print("Enter 1 or 2: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 1 || choice == 2) {
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }
            } else {
                System.out.println("Invalid input. Please enter 1 or 2.");
                scanner.next();
            }
        }
        return choice;
    }

    public static double getValidInput(Scanner scanner, String prompt, double min, double max) {
        double value;
        while (true) {
            System.out.println(prompt);
            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                scanner.nextLine();
                if (value >= min && value <= max) {
                    break;
                } else {
                    System.out.printf("Please enter a value between %.1f and %.1f.%n", min, max);
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
        return value;
    }

    public static double calculateBMI(int weightUnit, int heightUnit, double weight, double height) {
        if (weightUnit == 2) {
            weight = weight * 0.453592; // pounds to kg
        }
        if (heightUnit == 2) {
            height = height * 0.0254; // inches to meters
        }
        return weight / (height * height);
    }

    public static String getBMICategory(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25.0) {
            return "Normal weight";
        } else if (bmi < 30.0) {
            return "Overweight";
        } else if (bmi < 35.0) {
            return "Obesity Class I (Moderate)";
        } else if (bmi < 40.0) {
            return "Obesity Class II (Severe)";
        } else {
            return "Obesity Class III (Very Severe)";
        }
    }

    public static char askToRepeat(Scanner scanner) {
        System.out.print("Do you want to calculate again? (Y/N): ");
        String input = scanner.next();
        scanner.nextLine();
        return input.length() > 0 ? input.toUpperCase().charAt(0) : 'N';
    }
}
