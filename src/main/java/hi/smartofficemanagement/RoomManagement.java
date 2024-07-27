package hi.smartofficemanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RoomManagement {

    public static void configureRooms(int numberOfRooms) {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "INSERT INTO rooms (occupied, maxCapacity) VALUES (FALSE, 0)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < numberOfRooms; i++) {
                stmt.executeUpdate();
            }
            System.out.println("Office configured with " + numberOfRooms + " meeting rooms.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setRoomCapacity(int roomId, int capacity) {
        if (capacity < 0) {
            System.out.println("Invalid capacity. Please enter a valid positive number.");
            return;
        }

        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "UPDATE rooms SET maxCapacity = ? WHERE roomId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, capacity);
            stmt.setInt(2, roomId);
            stmt.executeUpdate();
            System.out.println("Room " + roomId + " maximum capacity set to " + capacity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the maximum capacity for the new room: ");
        int maxCapacity = scanner.nextInt();

        if (maxCapacity < 0) {
            System.out.println("Invalid capacity. Please enter a valid positive number.");
            return;
        }

        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "INSERT INTO rooms (occupied, maxCapacity) VALUES (FALSE, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, maxCapacity);
            stmt.executeUpdate();
            System.out.println("Room added with maximum capacity " + maxCapacity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listRooms() {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM rooms";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int roomId = rs.getInt("roomId");
                boolean occupied = rs.getBoolean("occupied");
                int maxCapacity = rs.getInt("maxCapacity");
                System.out.println("Room ID: " + roomId + ", Occupied: " + occupied + ", Max Capacity: " + maxCapacity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isRoomOccupied(int roomId) {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT occupied FROM rooms WHERE roomId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, roomId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("occupied");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

     public static void updateRoomOccupancy(int roomId, boolean occupied) {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "UPDATE rooms SET occupied = ? WHERE roomId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, occupied);
            stmt.setInt(2, roomId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
