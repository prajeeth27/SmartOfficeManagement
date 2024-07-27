package hi.smartofficemanagement;

import java.util.Scanner;

public class SmartOfficeManagement {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Smart Office Management System");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) {
            // Login
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (UserAuthentication.login(username, password)) {
                System.out.println("Login successful!");
                showMenu(scanner, username);
            } else {
                System.out.println("Invalid credentials!");
            }
        } else if (choice == 2) {
            // Register
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (UserAuthentication.register(username, password)) {
                System.out.println("Registration successful!");
            } else {
                System.out.println("Registration failed!");
            }
        } else {
            System.out.println("Invalid choice!");
        }

        scanner.close();
    }

    private static void showMenu(Scanner scanner, String username) {
        while (true) {
            System.out.println("1. Configure Rooms");
            System.out.println("2. Set Room Capacity");
            System.out.println("3. Add Room");
            System.out.println("4. List Rooms");
            System.out.println("5. Book Room");
            System.out.println("6. Cancel Booking");
            System.out.println("7. Add Occupants");
            System.out.println("8. Room UsageStatistics");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter the number of meeting rooms: ");
                    int numberOfRooms = scanner.nextInt();
                    RoomManagement.configureRooms(numberOfRooms);
                    break;
                case 2:
                    System.out.print("Enter Room ID to set capacity: ");
                    int roomId = scanner.nextInt();
                    System.out.print("Enter the new capacity: ");
                    int capacity = scanner.nextInt();
                    RoomManagement.setRoomCapacity(roomId, capacity);
                    break;
                case 3:
                    RoomManagement.addRoom();
                    break;
                case 4:
                    RoomManagement.listRooms();
                    break;
                case 5:
                    BookingManagement.bookRoom(username);
                    break;
                case 6:
                    BookingManagement.cancelBooking();
                    break;
                case 7:
                    System.out.print("Enter room number to add occupants: ");
                    int roomNumber = scanner.nextInt();
                    System.out.print("Enter the number of occupants: ");
                    int occupants = scanner.nextInt();
                    OccupancySensor.addOccupants(roomNumber, occupants);
                    break;
                case 8:
                    RoomUsageStatistics.displayRoomUsageStatistics();
                    break;
                  
                case 9:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
