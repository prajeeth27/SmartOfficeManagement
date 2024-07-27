package hi.smartofficemanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OccupancySensor {

    public static void addOccupants(int roomId, int numberOfOccupants) {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            // Fetch current occupancy and max capacity
            String query = "SELECT occupied, maxCapacity, currentOccupants FROM rooms LEFT JOIN occupants ON rooms.roomId = occupants.roomId WHERE rooms.roomId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, roomId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                boolean isOccupied = rs.getBoolean("occupied");
                int maxCapacity = rs.getInt("maxCapacity");
                int currentOccupants = rs.getInt("currentOccupants");

                if (numberOfOccupants < 0 || numberOfOccupants > maxCapacity) {
                    System.out.println("Invalid number of occupants. Please enter a number between 0 and " + maxCapacity + ".");
                    return;
                }

                if (numberOfOccupants == 0) {
                    // Vacating the room
                    if (isOccupied && currentOccupants > 0) {
                        updateOccupancy(roomId, 0); // Set currentOccupants to 0 and update room status
                        System.out.println("Room " + roomId + " is now unoccupied. AC and lights turned off.");
                    } else {
                        System.out.println("Room " + roomId + " is already unoccupied.");
                    }
                } else {
                    if (isOccupied) {
                        // Room is already occupied; update occupants
                        query = "UPDATE occupants SET currentOccupants = currentOccupants + ? WHERE roomId = ?";
                        try (PreparedStatement updateStmt = conn.prepareStatement(query)) {
                            updateStmt.setInt(1, numberOfOccupants);
                            updateStmt.setInt(2, roomId);
                            updateStmt.executeUpdate();
                            System.out.println("Room " + roomId + " now occupied by " + (currentOccupants + numberOfOccupants) + " persons. AC and lights turned on.");
                        }
                    } else {
                        // Room is not currently occupied
                        System.out.println("Room " + roomId + " is not currently booked. Please book the room before adding occupants.");
                    }
                }
            } else {
                System.out.println("Room " + roomId + " does not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateOccupancy(int roomId, int numberOfOccupants) {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query;
        try {
            if (numberOfOccupants == 0) {
                // Turn off AC and lights
                query = "UPDATE rooms SET occupied = FALSE WHERE roomId = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(query)) {
                    updateStmt.setInt(1, roomId);
                    updateStmt.executeUpdate();
                }
            }

            // Update currentOccupants
            query = "INSERT INTO occupants (roomId, currentOccupants) VALUES (?, ?) ON DUPLICATE KEY UPDATE currentOccupants = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(query)) {
                updateStmt.setInt(1, roomId);
                updateStmt.setInt(2, numberOfOccupants);
                updateStmt.setInt(3, numberOfOccupants);
                updateStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
