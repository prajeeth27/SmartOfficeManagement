package hi.smartofficemanagement;

import java.util.Scanner;

public class RoomConfig {

    public static void configureRooms() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of meeting rooms: ");
        int roomCount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 1; i <= roomCount; i++) {
            RoomManagement.addRoom();
            System.out.println("Room " + i + " added.");
        }
        System.out.println("Office configured with " + roomCount + " meeting rooms: " + "Room 1, Room 2, Room " + roomCount + ".");
    }

    public static void setRoomCapacity() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter room number to set capacity: ");
        int roomId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter the maximum capacity for Room " + roomId + ": ");
        int capacity = scanner.nextInt();

        if (capacity > 0) {
            RoomManagement.setRoomCapacity(roomId, capacity);
            System.out.println("Room " + roomId + " maximum capacity set to " + capacity + ".");
        } else {
            System.out.println("Invalid capacity. Please enter a valid positive number.");
        }
    }
}
