package HMS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    // Instance variables for database connection and scanner for user input
    private Connection connection;
    private Scanner scanner;

    // Constructor: Initializes the connection and scanner
    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    /**
     * Method to add a new patient to the database.
     * The user is prompted to enter patient details (name, age, and gender).
     * These details are then inserted into the 'patients' table in the database.
     */
    public void addPatient() {
        System.out.println("Enter Patient Name: ");
        String name = scanner.next(); // Get patient name from user input
        System.out.println("Enter Patient Age: ");
        int age = scanner.nextInt();  // Get patient age from user input
        System.out.println("Enter Patient Gender: ");
        String gender = scanner.next();  // Get patient gender from user input

        try {
            // SQL query to insert the patient data into the 'patients' table
            String query = "INSERT INTO patients(name, age, gender) VALUES(?,?,?)";

            // Prepare the SQL statement and set the parameter values
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);    // Set the 'name' value
            preparedStatement.setInt(2, age);        // Set the 'age' value
            preparedStatement.setString(3, gender);  // Set the 'gender' value

            // Execute the update (insertion) and check if it affected any rows
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient Added Successfully!!");
            } else {
                System.out.println("Failed to add Patient!!");
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions (such as connection issues or query errors)
            e.printStackTrace();
        }
    }

    /**
     * Method to view all patients stored in the database.
     * The method fetches all records from the 'patients' table and displays
     * them in a formatted table.
     */
    public void viewPatient() {
        // SQL query to select all data from the 'patients' table
        String query = "select * from patients";
        try {
            // Prepare and execute the SQL statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Displaying table headers
            System.out.println("Patients: ");
            System.out.println("+-------------+----------------+------------+-------------+");
            System.out.println("| Patient ID  | Name           | Age        | Gender      |");
            System.out.println("+-------------+----------------+------------+-------------+");

            // Iterate through the result set and display each patient's data
            while (resultSet.next()) {
                int id = resultSet.getInt("id");       // Get patient ID
                String name = resultSet.getString("name"); // Get patient name
                int age = resultSet.getInt("age");     // Get patient age
                String gender = resultSet.getString("gender"); // Get patient gender

                // Format and print patient data
                System.out.printf("| %-11s | %-14s | %-10s | %-11s |\n", id, name, age, gender);
                System.out.println("+-------------+----------------+------------+-------------+");
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions (such as connection issues or query errors)
            e.printStackTrace();
        }
    }

    /**
     * Method to check if a patient with the given ID exists in the database.
     *
     * @param id The patient ID to search for.
     * @return true if the patient exists, false otherwise.
     */
    public boolean getPatientByID(int id) {
        // SQL query to find a patient by their ID
        String query = "SELECT * FROM patients WHERE id=?";
        try {
            // Prepare the SQL statement and set the patient ID parameter
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id); // Set the 'id' value

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Return true if a patient record is found
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions (such as connection issues or query errors)
            e.printStackTrace();
        }
        return false; // Return false if an error occurs or no record is found
    }
}