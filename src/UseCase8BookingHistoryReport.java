import java.util.*;

// Reservation class (confirmed booking)
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Reservation [ID=" + reservationId +
                ", Guest=" + guestName +
                ", RoomType=" + roomType + "]";
    }
}

// Booking History (stores confirmed bookings)
class BookingHistory {

    // List preserves insertion (chronological) order
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Added to history: " + reservation);
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Reporting Service
class BookingReportService {

    // Display all bookings
    public void showAllBookings(List<Reservation> reservations) {
        System.out.println("\n=== Booking History ===");

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {

        System.out.println("\n=== Booking Summary Report ===");

        Map<String, Integer> roomTypeCount = new HashMap<>();

        for (Reservation r : reservations) {
            String type = r.getRoomType();
            roomTypeCount.put(type, roomTypeCount.getOrDefault(type, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : roomTypeCount.entrySet()) {
            System.out.println(entry.getKey() + " bookings: " + entry.getValue());
        }

        System.out.println("Total bookings: " + reservations.size());
    }
}

// Main Class
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating confirmed bookings (from Use Case 6)
        history.addReservation(new Reservation("R101", "Alice", "Deluxe"));
        history.addReservation(new Reservation("R102", "Bob", "Standard"));
        history.addReservation(new Reservation("R103", "Charlie", "Suite"));
        history.addReservation(new Reservation("R104", "David", "Deluxe"));

        // Admin views booking history
        reportService.showAllBookings(history.getAllReservations());

        // Admin generates report
        reportService.generateSummary(history.getAllReservations());
    }
}