package HMS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {
    // Instance variable for the database connection
    private Connection connection;

    // Constructor: Initializes the connection
    public Doctor(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method to view all doctors stored in the database.
     * It fetches and displays all records from the 'doctors' table
     * in a formatted table, showing doctor ID, name, and specialization.
     */
    public void viewDoctors() {
        // SQL query to retrieve all doctors from the 'doctors' table
        String query = "SELECT * FROM doctors";
        try {
            // Prepare and execute the SQL statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Displaying table headers
            System.out.println("Doctors: ");
            System.out.println("+-------------+----------------------+-------------------+");
            System.out.println("| Doctor ID   | Name                 | Specialization    |");
            System.out.println("+-------------+----------------------+-------------------+");

            // Iterate through the result set and display each doctor's details
            while (resultSet.next()) {
                int id = resultSet.getInt("id");  // Get doctor ID
                String name = resultSet.getString("name");  // Get doctor name
                String specialization = resultSet.getString("specialization");  // Get doctor specialization

                // Print doctor details in a formatted table
                System.out.printf("| %-11s | %-20s | %-17s |\n", id, name, specialization);
                System.out.println("+-------------+----------------------+-------------------+");
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions (such as connection issues or query errors)
            e.printStackTrace();
        }
    }

    /**
     * Method to check if a doctor with a given ID exists in the database.
     *
     * @param id The doctor ID to search for.
     * @return true if the doctor exists, false otherwise.
     */
    public boolean getDoctorByID(int id) {
        // SQL query to find a doctor by their ID
        String query = "SELECT * FROM doctors WHERE id=?";
        try {
            // Prepare the SQL statement and set the doctor ID parameter
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);  // Set the 'id' value

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Return true if a doctor record is found
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions (such as connection issues or query errors)
            e.printStackTrace();
        }
        return false;  // Return false if an error occurs or no record is found
    }
}