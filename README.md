# Hospital Management System
The **Hospital Management System** is a simple Java-based application that helps manage hospital operations, such as adding new patients, viewing existing patients and doctors, and booking appointments. The application connects to a MySQL database to store and retrieve patient and doctor records, providing a seamless way to handle basic hospital management tasks. This project demonstrates core Java concepts, JDBC for database interaction, and object-oriented programming practices.

## Project Structure


**`Patient.java`**: Handles patient-related functionalities like adding patients and viewing patient records.    
**`Doctor.java`**: Manages doctor-related features, including displaying doctor details and checking availability.    
**`Hospital.java`**: The main file for the system, handles user interaction and various hospital management tasks like booking appointments and managing data.



## Features

-   **Add Patient**: Allows users to input patient details and store them in the database.
-   **View Patients**: Displays all patient details stored in the system.
-   **View Doctors**: Lists all available doctors along with their specializations.
-   **Book Appointments**: Schedules an appointment between a patient and a doctor, checking availability.
-   **Menu-Based System**: Interactive menu that handles user options using a switch-case structure.



## Topics Used

-   **Java OOP**: Classes, objects, constructors, and encapsulation for patients and doctors.
-   **Database Connection (JDBC)**: Integrating MySQL database with Java using `PreparedStatement` for database interactions.
-   **SQL Queries**: Inserting, selecting, and checking data in the database.
-   **Exception Handling**: Error handling for SQL exceptions and user input validation.
-   **Switch-Case**: Simple and intuitive menu-driven user interface.


## Technologies Used

-   **Java**: Core Java concepts like OOP, control flow (if-else, switch), and exception handling.
-   **MySQL**: Database for storing patient, doctor, and appointment data.
-   **JDBC (Java Database Connectivity)**: Connecting and interacting with the MySQL database from the Java application.
-   **IntelliJ IDEA**: IDE for writing and testing the application.

## How to Run

1.  **Database Setup**:    
    - Create a MySQL database named `hospital`.    
    - Add the following tables:    
   ```sql
   CREATE TABLE patients (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  age INT,
  gender VARCHAR(10)
);

CREATE TABLE doctors (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  specialization VARCHAR(255)
);

CREATE TABLE appointments (
  id INT AUTO_INCREMENT PRIMARY KEY,
  patient_id INT,
  doctor_id INT,
  appointment_date DATE,
  FOREIGN KEY (patient_id) REFERENCES patients(id),
  FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);
   ```
2.  **Java Setup**:    
    - Install and configure [MySQL JDBC driver](https://dev.mysql.com/downloads/connector/j/).    
    - Update the database connection details (`url`, `username`, `password`) in `Hospital.java`.    
    - Compile and run the `Hospital.java` file.

## Screenshots
-  **Output:**    
  ![output](https://github.com/user-attachments/assets/dff4f794-0935-4cb0-b895-35ac6fa7668d)

- **Database Tables:**
    
   - Patients Table    
     ![patients table](https://github.com/user-attachments/assets/1e7736cd-56f9-48d3-a549-8668d44c1b22)
   
    -  Doctors Table    
      ![doctors table](https://github.com/user-attachments/assets/70254d9f-6f88-4ddc-a819-31ea566c9c22)
   
    -  Appointments Table    
      ![appointments table](https://github.com/user-attachments/assets/79363447-8563-4107-9a53-e6017a5b90fc)
