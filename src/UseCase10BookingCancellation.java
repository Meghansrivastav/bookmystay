import java.util.*;

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    @Override
    public String toString() {
        return "Reservation [ID=" + reservationId +
                ", Guest=" + guestName +
                ", RoomType=" + roomType +
                ", RoomID=" + roomId + "]";
    }
}

// Inventory Service
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Standard", 1);
        inventory.put("Deluxe", 1);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

// Booking History
class BookingHistory {
    private Map<String, Reservation> confirmedBookings = new HashMap<>();

    public void addReservation(Reservation r) {
        confirmedBookings.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String reservationId) {
        return confirmedBookings.get(reservationId);
    }

    public void removeReservation(String reservationId) {
        confirmedBookings.remove(reservationId);
    }

    public void displayBookings() {
        System.out.println("\nActive Bookings:");
        for (Reservation r : confirmedBookings.values()) {
            System.out.println(r);
        }
    }
}

// Cancellation Service
class CancellationService {

    private InventoryService inventoryService;
    private BookingHistory bookingHistory;

    // Stack for rollback (LIFO)
    private Stack<String> releasedRoomIds = new Stack<>();

    public CancellationService(InventoryService inventoryService, BookingHistory bookingHistory) {
        this.inventoryService = inventoryService;
        this.bookingHistory = bookingHistory;
    }

    public void cancelBooking(String reservationId) {

        System.out.println("\nProcessing cancellation for ID: " + reservationId);

        // Validate existence
        Reservation reservation = bookingHistory.getReservation(reservationId);

        if (reservation == null) {
            System.out.println("Cancellation Failed: Reservation not found.");
            return;
        }

        // Step 1: Push room ID to rollback stack
        releasedRoomIds.push(reservation.getRoomId());

        // Step 2: Restore inventory
        inventoryService.increment(reservation.getRoomType());

        // Step 3: Remove booking from history
        bookingHistory.removeReservation(reservationId);

        System.out.println("Cancellation Successful for Reservation ID: " + reservationId);
        System.out.println("Released Room ID: " + reservation.getRoomId());
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (Recently Released Room IDs): " + releasedRoomIds);
    }
}

// Main Class
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings (from Use Case 6)
        history.addReservation(new Reservation("R101", "Alice", "Deluxe", "D101"));
        history.addReservation(new Reservation("R102", "Bob", "Standard", "S201"));

        history.displayBookings();
        inventory.displayInventory();

        // Cancellation service
        CancellationService cancellationService = new CancellationService(inventory, history);

        // Valid cancellation
        cancellationService.cancelBooking("R101");

        // Invalid cancellation
        cancellationService.cancelBooking("R999");

        // Display updated state
        history.displayBookings();
        inventory.displayInventory();
        cancellationService.showRollbackStack();
    }
}