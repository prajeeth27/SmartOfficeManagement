package hi.smartofficemanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class BookingManagement {

    public static void bookRoom(String username) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Room ID to book: ");
        int roomId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter start time (yyyy-MM-dd HH:mm:ss): ");
        String startTimeStr = scanner.nextLine();
        System.out.print("Enter end time (yyyy-MM-dd HH:mm:ss): ");
        String endTimeStr = scanner.nextLine();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime;
        Date endTime;
        try {
            startTime = sdf.parse(startTimeStr);
            endTime = sdf.parse(endTimeStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format.");
            return;
        }

        if (isRoomBooked(roomId, startTime, endTime)) {
            System.out.println("Room " + roomId + " is already booked during this time. Cannot book.");
            return;
        }

        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "INSERT INTO bookings (roomId, username, startTime, endTime) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, roomId);
            stmt.setString(2, username);
            stmt.setTimestamp(3, new java.sql.Timestamp(startTime.getTime()));
            stmt.setTimestamp(4, new java.sql.Timestamp(endTime.getTime()));
            stmt.executeUpdate();
            RoomManagement.updateRoomOccupancy(roomId, true);
            System.out.println("Room booked successfully with Booking ID: " + getBookingId(roomId, startTime, endTime));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isRoomBooked(int roomId, Date startTime, Date endTime) {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM bookings WHERE roomId = ? AND ((startTime <= ? AND endTime >= ?) OR (startTime <= ? AND endTime >= ?))";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, roomId);
            stmt.setTimestamp(2, new java.sql.Timestamp(startTime.getTime()));
            stmt.setTimestamp(3, new java.sql.Timestamp(startTime.getTime()));
            stmt.setTimestamp(4, new java.sql.Timestamp(endTime.getTime()));
            stmt.setTimestamp(5, new java.sql.Timestamp(endTime.getTime()));
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void cancelBooking() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Booking ID to cancel: ");
        int bookingId = scanner.nextInt();

        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT roomId FROM bookings WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int roomId = rs.getInt("roomId");

                // Delete booking
                query = "DELETE FROM bookings WHERE id = ?";
                try (PreparedStatement deleteStmt = conn.prepareStatement(query)) {
                    deleteStmt.setInt(1, bookingId);
                    int affectedRows = deleteStmt.executeUpdate();
                    if (affectedRows > 0) {
                        RoomManagement.updateRoomOccupancy(roomId, false); // Update room occupancy
                        OccupancySensor.updateOccupancy(roomId, 0); // Reset occupants
                        System.out.println("Booking cancelled successfully.");
                    } else {
                        System.out.println("Booking ID not found. Cannot cancel booking.");
                    }
                }
            } else {
                System.out.println("Booking ID not found. Cannot cancel booking.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getBookingId(int roomId, Date startTime, Date endTime) {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT id FROM bookings WHERE roomId = ? AND startTime = ? AND endTime = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, roomId);
            stmt.setTimestamp(2, new java.sql.Timestamp(startTime.getTime()));
            stmt.setTimestamp(3, new java.sql.Timestamp(endTime.getTime()));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
