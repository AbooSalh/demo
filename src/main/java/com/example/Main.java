package com.example;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String USERS_FILE = "users.json";
    private static List<User> loadedUsers = new ArrayList<>();

    public static void main(String[] args) {

        loadUsersFromJSON();
        // scanner
        Scanner scanner = new Scanner(System.in);
        System.out.println("hello world");
        while (true) {
            System.out.println("1. Log in");
            System.out.println("2. Sign up");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (option) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    signUp(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            break;
        }
    }

    private static void loadUsersFromJSON() {
        JSONObject loadedUsersData = JSONFileHandler.loadData(USERS_FILE);
        if (loadedUsersData != null) {
            System.out.println("Loaded JSON data: " + loadedUsersData.toString());
            // Debugging
            JSONArray loadedUsersArray = loadedUsersData.getJSONArray("users");
            for (int i = 0; i < loadedUsersArray.length(); i++) {
                JSONObject userObject = loadedUsersArray.getJSONObject(i);
                String name = userObject.getString("name");
                String id = userObject.getString("id");
                String type = userObject.getString("type");
                String password = userObject.getString("password");
                // System.out.println("Loaded user: " + name); // Debugging
                // Add logic to create appropriate User subclass instance based on type
                if (type.equals("passenger")) {
                    loadedUsers.add(new Passenger(name, id, password));
                } else if (type.equals("driver")) {
                    loadedUsers.add(new Driver(name, id, password));
                } else if (type.equals("manager")) {
                    loadedUsers.add(new Manager(name, id, password));
                }
            }
        } else {
            System.out.println("Failed to load users from file.");
        }
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim().toLowerCase();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        for (User user : loadedUsers) {
            if (user.getName().trim().toLowerCase().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Login successful!");
                // Access methods based on user type
                if (user instanceof Passenger) {
                    // Passenger methods
                    handlePassenger((Passenger) user, scanner);
                } else if (user instanceof Driver) {
                    // Driver methods
                    handleDriver((Driver) user);
                } else if (user instanceof Manager) {
                    // Manager methods
                    handleManager((Manager) user);
                }
                return; // Exit the loop after successful login
            }
        }
        System.out.println("Invalid username or password. Please try again.");
    }

    private static void signUp(Scanner scanner) {
        System.out.println("Choose user type:");
        System.out.println("1. Passenger");
        System.out.println("2. Employee");
        int userTypeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (userTypeChoice) {
            case 1:
                signUpPassenger(scanner);
                break;
            case 2:
                signUpEmployee(scanner);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                signUp(scanner); // Restart sign-up process
                break;
        }
    }

    private static void signUpPassenger(Scanner scanner) {
        while (true) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine().trim();
            System.out.print("Enter password: ");
            String password = scanner.nextLine().trim();

            // Check if username already exists
            boolean usernameExists = false;
            for (User user : loadedUsers) {
                if (user.getName().equals(username)) {
                    System.out.println("Username already exists. Please choose a different one.");
                    usernameExists = true;
                    break;
                }
            }

            if (!usernameExists) {
                User newUser = new Passenger(username, generateID(), password);
                loadedUsers.add(newUser);

                // Update JSON file
                updateUsersJSON();
                System.out.println("User registration successful!");
                handlePassenger((Passenger) newUser, scanner);
                break; // Exit the loop if username is unique
            }
        }
    }

    private static User signUpEmployee(Scanner scanner) {
        System.out.println("Choose employee type:");
        System.out.println("1. Manager");
        System.out.println("2. Driver");
        int employeeTypeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        while (true) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine().trim();
            System.out.print("Enter password: ");
            String password = scanner.nextLine().trim();

            // Check if username already exists
            boolean usernameExists = false;
            for (User user : loadedUsers) {
                if (user.getName().equals(username)) {
                    System.out.println("Username already exists. Please choose a different one.");
                    usernameExists = true;
                    break;
                }
            }

            if (!usernameExists) {
                User newUser;
                if (employeeTypeChoice == 1) {
                    newUser = new Manager(username, generateID(), password);
                    System.out.println("Manager registration successful!");
                    handleManager((Manager) newUser);
                } else if (employeeTypeChoice == 2) {
                    newUser = new Driver(username, generateID(), password);
                    System.out.println("Driver registration successful!");
                    handleDriver((Driver) newUser);
                } else {
                    System.out.println("Invalid choice. Please try again.");
                    continue;
                }

                loadedUsers.add(newUser);
                // Update JSON file
                updateUsersJSON();
                return newUser; // Exit the loop and return newUser if username is unique
            }
        }
    }

    private static void updateUsersJSON() {
        JSONObject usersData = new JSONObject();
        JSONArray usersArray = new JSONArray();
        for (User user : loadedUsers) {
            usersArray.put(user.toJSON());
        }
        usersData.put("users", usersArray);
        JSONFileHandler.saveData(usersData, USERS_FILE);
    }

    private static String generateID() {
        // Generate ID logic
        return ""; // Implement your ID generation logic here
    }

    private static void handlePassenger(Passenger passenger, Scanner scanner) {
        System.out.println("welcome back!");
        System.out.println("1. Show all Trips");
        System.out.println("2.Diplay booked trips");
        System.out.println("3.Display Profile");
        int option = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        switch (option) {
            case 1:
                passenger.viewAvailableTrips();
                break;
            case 2:
                // views the booked trips and gives the user the option to cancel the booking
                passenger.viewBookedTrips();
                break;
            case 3:
                // Display Profile
                System.out.println("UserName: " + passenger.getName());
                System.out.println("Password: " + passenger.getPassword());
                break;

            default:
                System.out.println("Invalid option. Please try again.");
        }

    }

    private static void handleManager(Manager manager) {
        System.out.println("Welcome back, what would you like to do?");
        System.out.println("1. Add Driver");
        System.out.println("2. Add Trip");
        System.out.println("3. Remove Trip");
        System.out.println("4. Add vehcile");
        System.out.println("5. Generate report");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        in.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                addDriver(manager);
                break;
            case 2:
                addTrip(manager);
                break;
            case 3:
                removeTrip(manager);
                break;
                case 4:
                addVehicle(manager);
                break;
            case 5:
                manager.reportDrivers();
                break;
            default:

                System.out.println("Invalid choice.");
        }
    }

    private static void addDriver(Manager manager) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter driver's name:");
        String name = in.nextLine();
        System.out.println("Enter driver's ID:");
        String id = in.nextLine();
        System.out.println("Enter driver's password:");
        String password = in.nextLine();

        // Create a new Driver object
        Driver newDriver = new Driver(name, id, password);

        // Add the new driver using the Manager's addDriver method
        manager.addDriver(newDriver);
        System.out.println("Driver added successfully.");

    }

    private static void addTrip(Manager manager) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter trip ID:");
        String id = in.nextLine();
        System.out.println("Enter trip type:");
        String type = in.nextLine();
        System.out.println("Enter trip source:");
        String source = in.nextLine();
        System.out.println("Enter trip destination:");
        String destination = in.nextLine();
        System.out.println("Is it a one-way trip? (true/false):");
        boolean oneWay = in.nextBoolean();
        System.out.println("Enter number of stops:");
        int numberOfStops = in.nextInt();
        System.out.println("Enter price:");
        double price = in.nextDouble();

        // Load the data of drivers from the JSON file
        JSONObject driversData = JSONFileHandler.loadData("drivers.json");
        JSONArray driversArray = (JSONArray) driversData.get("drivers");
        System.out.println(driversArray.toString());
        // Display the list of drivers to the user
        System.out.println("Choose the driver: ");
        for (int i = 0; i < driversArray.length(); i++) {
            JSONObject driverObj = driversArray.getJSONObject(i);
            System.out.println((i + 1) + ". " + driverObj.getString("name"));
        }

        // Allow the user to choose a driver
        int driverChoice = in.nextInt();
        JSONObject chosenDriver = driversArray.getJSONObject(driverChoice - 1);
        String driverName = chosenDriver.getString("name");
        // Now you can use this chosen driver in your Trip object creation
        // For example, you can pass it as a parameter to the Trip constructor
        Driver driver = new Driver(driverName, chosenDriver.getString("id"), chosenDriver.getString("password"));

        // Load the data of vehicles from the JSON file

        JSONObject vehiclesData = JSONFileHandler.loadData("vehicles.json");
        JSONArray vehiclesArray = vehiclesData.getJSONArray("vehicles");

        // Display the list of vehicles to the user
        System.out.println("Choose the vehicle:");
        for (int i = 0; i < vehiclesArray.length(); i++) {
            JSONObject vehicleObj = vehiclesArray.getJSONObject(i);
            System.out.println((i + 1) + ". " + vehicleObj.getString("id")+"/"+vehicleObj.getString("type"));
        }

        // Allow the user to choose a vehicle
        int vehicleChoice = in.nextInt();
        JSONObject chosenVehicle = vehiclesArray.getJSONObject(vehicleChoice - 1);
      String ID=chosenVehicle.getString("id");
        String vehicleType = chosenVehicle.getString("type");
        int vehicleCapacity = chosenVehicle.getInt("capacity");
        String vehicleLicensePlate = chosenVehicle.getString("licensePlate");

        // Create a new Vehicle object
        Vehicle vehicle = new Vehicle(ID,vehicleType, vehicleCapacity, vehicleLicensePlate);

        // Create a new Trip object
        Trip newTrip = new Trip(id, type, source, destination, oneWay, numberOfStops, vehicleCapacity, price, driver,
                vehicle);

        // Add the new trip using the Manager's addTrip method
        manager.addTrip(driver, vehicle, newTrip);
        System.out.println("Trip added successfully.");
    }

    private static void removeTrip(Manager manager) {
        // Load the trips from the JSON file
        JSONObject tripsArray = JSONFileHandler.loadData("trips.json");

        // Display the available trips
        System.out.println("Available Trips:");
        for (int i = 0; i < tripsArray.length(); i++) {
            JSONObject tripObj = tripsArray.getJSONObject("trips");
            System.out.println("Trip ID: " + tripObj.getString("id"));
            System.out.println("Source: " + tripObj.getString("source"));
            System.out.println("Destination: " + tripObj.getString("destination"));

        }

        // enter the ID of the trip to remove
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the ID of the trip to remove: ");
        String tripId = in.nextLine();

        // Remove the trip using the Manager's deleteTrip method
        manager.deleteTrip(tripId);
        System.out.println("Trip removed successfully.");
    }

    public static void addVehicle(Manager manager) {
      
        Scanner in=new Scanner(System.in);
        System.out.println("enter vehcile's id");
        String u=in.nextLine();
    
        System.out.println("enter the vehicle's type ");
        System.out.println("Bus, Minibus, Limousine");
        String x = in.nextLine(); // Consume newline
        System.out.println("enter vehicle's capacity");
        int z = in.nextInt();
        in.nextLine(); // Consume newline
        System.out.println("enter vehicle's licensePlate ");
        String q = in.nextLine();
        Vehicle v1 = new Vehicle(u,x, z, q);
        manager.addVehicle(v1);
        System.out.println("Vehcile added succesfully!");
    }
    
    private static void handleDriver(Driver driver) {

        System.out.println("Welcome back");
        driver.viewAssignedTrips();

    }

}
