package HMS;

import java.sql.*;
import java.util.Scanner;

public class Hospital {
    // Database connection details (URL, username, password)
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "123456";

    public static void main(String[] args) {
        // Load MySQL JDBC Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Create a Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Establish a database connection and initialize Patient and Doctor objects
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);

            // Main loop for interacting with the hospital management system
            while (true) {
                // Display the menu options to the user
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                // Get user choice
                int choice = scanner.nextInt();

                // Handle user choices using a switch-case structure
                switch (choice) {
                    case 1:
                        // Add a new patient
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        // View all patients
                        patient.viewPatient();
                        System.out.println();
                        break;
                    case 3:
                        // View all doctors
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        // Book an appointment between a patient and a doctor
                        bookAppointment(patient, doctor, connection, scanner);
                        System.out.println();
                        break;
                    case 5:
                        // Exit the program
                        System.out.println("THANK YOU FOR USING HOSPITAL MANAGEMENT SYSTEM!!");
                        return;
                    default:
                        // Handle invalid menu options
                        System.out.println("Please enter a valid choice!!");
                        System.out.println();
                        break;
                }
            }

        } catch (SQLException e) {
            // Handle any SQL exceptions
            e.printStackTrace();
        }
    }

    /**
     * Method to book an appointment for a patient with a doctor.
     *
     * @param patient    Patient object to validate patient ID
     * @param doctor     Doctor object to validate doctor ID
     * @param connection Database connection object
     * @param scanner    Scanner object to get user input
     */
    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
        // Get appointment details from user
        System.out.println("Enter Patient ID: ");
        int patientId = scanner.nextInt();
        System.out.println("Enter Doctor ID: ");
        int doctorId = scanner.nextInt();
        System.out.println("Enter appointment date (YYYY-MM-DD): ");
        String appointmentDate = scanner.next();

        // Check if both the patient and doctor exist before booking the appointment
        if (patient.getPatientByID(patientId) && doctor.getDoctorByID(doctorId)) {
            // Check if the doctor is available on the specified date
            if (checkDoctorAvailability(doctorId, appointmentDate, connection)) {
                String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?,?,?)";
                try {
                    // Prepare the SQL statement and set parameters for patient ID, doctor ID, and appointment date
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointmentDate);

                    // Execute the update to book the appointment
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Appointment Booked!!");
                    } else {
                        System.out.println("Failed to Book Appointment!!");
                    }

                } catch (SQLException e) {
                    // Handle any SQL exceptions during the appointment booking
                    e.printStackTrace();
                }

            } else {
                System.out.println("Doctor isn't available on this date!!");
            }

        } else {
            System.out.println("Sorry, either the doctor or patient doesn't exist!!");
        }
    }

    /**
     * Method to check if a doctor is available on a specific date.
     *
     * @param doctorId        Doctor ID to check availability
     * @param appointmentDate Appointment date to check
     * @param connection      Database connection object
     * @return true if the doctor is available, false otherwise
     */
    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
        // SQL query to check if the doctor has any appointments on the specified date
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";
        try {
            // Prepare the SQL statement and set parameters
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);

            // Execute the query and check the result
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);  // Get the count of appointments
                if (count == 0) {
                    return true;  // Doctor is available if there are no appointments on that date
                } else {
                    return false;  // Doctor is not available if there is already an appointment
                }
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            e.printStackTrace();
        }
        return false;
    }

}