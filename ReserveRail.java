import java.sql.*;
import java.util.Scanner;
import java.util.Random;

public class ReserveRail {
    private static final int min = 10000;
    private static final int max = 99999;

    public static class user {
        private String username;
        private String password;

        Scanner sc = new Scanner(System.in);

        public user() {
        }

        public String getUsername() {
            System.out.println("Enter Username : ");
            username = sc.nextLine();
            return username;
        }

        public String getPassword() {
            System.out.println("Enter Password : ");
            password = sc.nextLine();
            return password;
        }
    }

    public static class PnrRecord {
        private int PnrNumber;
        private String PassengerName;
        private int PassengerAge;
        private String PassengerGender;
        private String PassengerMobileNumber;
        private String TrainName;
        private String TrainNumber;
        private String ClassType;
        private String DateOfJourney;
        private String DepartureLocation;
        private String DestinationLocation;

        Scanner sc = new Scanner(System.in);

        public int getPnrNumber() {
            Random random = new Random();
            PnrNumber = random.nextInt(max) + min;
            return PnrNumber;
        }

        public String getPassengerName() {
            System.out.println("Enter the passenger name : ");
            PassengerName = sc.next();
            return PassengerName;
        }

        public int getPassengerAge() {
            System.out.println("Enter the passenger age : ");
            PassengerAge = sc.nextInt();
            return PassengerAge;
        }

        public String getPassengerGender() {
            System.out.println("Enter the passenger gender : ");
            PassengerGender = sc.next();
            return PassengerGender;
        }

        public String getPassengerMobileNumber() {
            System.out.println("Enter the passenger mobile number : ");
            PassengerMobileNumber = sc.next();
            return PassengerMobileNumber;
        }

        public String getTrainName() {
            System.out.println("Enter the train name : ");
            TrainName = sc.next();
            return TrainName;
        }

        public String getTrainNumber() {
            System.out.println("Enter the train number : ");
            TrainNumber = sc.next();
            return TrainNumber;
        }

        public String getClassType() {
            System.out.println("Enter the class type : ");
            ClassType = sc.next();
            return ClassType;
        }

        public String getDateOfJourney() {
            System.out.println("Enter the date of journey as 'YYYY-MM-DD' format");
            DateOfJourney = sc.next();
            return DateOfJourney;
        }

        public String getDepartureLocation() {
            System.out.println("Enter the departure location : ");
            DepartureLocation = sc.next();
            return DepartureLocation;
        }

        public String getDestinationLocation() {
            System.out.println("Enter the destination location :  ");
            DestinationLocation = sc.next();
            return DestinationLocation;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        user u1 = new user();
        String username = u1.getUsername();
        String password = u1.getPassword();

        String url = "jdbc:mysql://localhost:3306/railway"; // change the database as per your requirement
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                System.out.println("User Connection Granted.\n");
                while (true) {
                    String InsertQuery = "insert into track_book values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    String DeleteQuery = "DELETE FROM track_book WHERE Pnr_Number = ?";
                    String ShowQuery = "Select * from track_book";

                    System.out.println("Enter the choice : ");
                    System.out.println("1. Insert Record.\n");
                    System.out.println("2. Delete Record.\n");
                    System.out.println("3. Show All Records.\n");
                    System.out.println("4. Exit.\n");
                    int choice = sc.nextInt();

                    if (choice == 1) {

                        PnrRecord p1 = new PnrRecord();
                        int PnrNumber = p1.getPnrNumber();
                        String PassengerName = p1.getPassengerName();
                        int PassengerAge =  p1.getPassengerAge();
                        String PassengerGender =  p1.getPassengerGender();
                        String PassengerMobileNumber =  p1.getPassengerMobileNumber();
                        String TrainName = p1.getTrainName();
                        String TrainNumber = p1.getTrainNumber();
                        String ClassType = p1.getClassType();
                        String DateOfJourney = p1.getDateOfJourney();
                        String getDepartureLocation = p1.getDepartureLocation();
                        String getDestinationLocation = p1.getDestinationLocation();

                        try (PreparedStatement preparedStatement = connection.prepareStatement(InsertQuery)) {
                            preparedStatement.setInt(1, PnrNumber);
                            preparedStatement.setString(2, PassengerName);
                            preparedStatement.setInt(3, PassengerAge);
                            preparedStatement.setString(4, PassengerGender);
                            preparedStatement.setString(5, PassengerMobileNumber);
                            preparedStatement.setString(6, TrainName);
                            preparedStatement.setString(7, TrainNumber);
                            preparedStatement.setString(8, ClassType);
                            preparedStatement.setString(9, DateOfJourney);
                            preparedStatement.setString(10, getDepartureLocation);
                            preparedStatement.setString(11, getDestinationLocation);

                            int rowsAffected = preparedStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Record added successfully.");
                            }

                            else {
                                System.out.println("No records were added.");
                            }
                        }

                        catch (SQLException e) {
                            System.err.println("SQLException: " + e.getMessage());
                        }

                    }

                    else if (choice == 2) {
                        System.out.println("Enter the PNR number to delete the record : ");
                        int PnrNumber = sc.nextInt();
                        try (PreparedStatement preparedStatement = connection.prepareStatement(DeleteQuery)) {
                            preparedStatement.setInt(1, PnrNumber);
                            int rowsAffected = preparedStatement.executeUpdate();

                            if (rowsAffected > 0) {
                                System.out.println("Record deleted successfully.");
                            }

                            else {
                                System.out.println("No records were deleted.");
                            }
                        }

                        catch (SQLException e) {
                            System.err.println("SQLException: " + e.getMessage());
                        }
                    }

                    else if (choice == 3) {
                        try (PreparedStatement preparedStatement = connection.prepareStatement(ShowQuery);
                             ResultSet resultSet = preparedStatement.executeQuery()) {
                            System.out.println("\nAll records printing.\n");
                            while (resultSet.next()) {
                                int PnrNumber = resultSet.getInt("Pnr_Number");
                                String PassengerName = resultSet.getString("Passenger_Name");
                                int PassengerAge = resultSet.getInt("Passenger_Age");
                                String PassengerGender = resultSet.getString("Passenger_Gender");
                                String PassengerMobileNumber = resultSet.getString("Passenger_Mobile_Number");
                                String TrainName = resultSet.getString("Train_Name");
                                String TrainNumber = resultSet.getString("Train_Number");
                                String ClassType = resultSet.getString("Class_Type");
                                String DateOfJourney = resultSet.getString("Date_Of_journey");
                                String DepartureLocation = resultSet.getString("Departure_Location");
                                String DestinationLocation = resultSet.getString("Destination_Location");

                                System.out.println("PNR Number: " + PnrNumber);
                                System.out.println("Passenger Name: " + PassengerName);
                                System.out.println("Passenger Age: " + PassengerAge);
                                System.out.println("Passenger Gender: " + PassengerGender);
                                System.out.println("Passenger Mobile Number: " + PassengerMobileNumber);
                                System.out.println("Train Name: " + TrainName);
                                System.out.println("Train Number: " + TrainNumber);
                                System.out.println("Class Type: " + ClassType);
                                System.out.println("Date of Journey: " + DateOfJourney);
                                System.out.println("Departure Location: " + DepartureLocation);
                                System.out.println("Destination Location: " + DestinationLocation);
                                System.out.println("--------------");
                            }
                        } catch (SQLException e) {
                            System.err.println("SQLException: " + e.getMessage());
                        }
                    }

                    else if (choice == 4) {
                        System.out.println("Exiting the program.\n");
                        break;
                    }

                    else {
                        System.out.println("Invalid Choice Entered.\n");
                    }
                }

            }

            catch (SQLException e) {
                System.err.println("SQLException: " + e.getMessage());
            }
        }

        catch (ClassNotFoundException e) {
            System.err.println("Error loading JDBC driver: " + e.getMessage());
        }

        sc.close();
    }
}