import java.io.*;
import java.util.*;

// Reservation class (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
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

// Wrapper class to persist entire system state
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    List<Reservation> bookingHistory;
    Map<String, Integer> inventory;

    public SystemState(List<Reservation> bookingHistory, Map<String, Integer> inventory) {
        this.bookingHistory = bookingHistory;
        this.inventory = inventory;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    // Save state to file
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    // Load state from file
    public static SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            System.out.println("System state loaded successfully.");
            return state;

        } catch (FileNotFoundException e) {
            System.out.println("No saved state found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading state. Starting with safe defaults.");
        }

        return null;
    }
}

// Main Class
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        // Attempt to restore state
        SystemState state = PersistenceService.load();

        List<Reservation> bookingHistory;
        Map<String, Integer> inventory;

        if (state != null) {
            // Restore previous state
            bookingHistory = state.bookingHistory;
            inventory = state.inventory;
        } else {
            // Fresh start
            bookingHistory = new ArrayList<>();
            inventory = new HashMap<>();

            inventory.put("Standard", 2);
            inventory.put("Deluxe", 1);
        }

        // Simulate system usage
        bookingHistory.add(new Reservation("R101", "Alice", "Deluxe"));
        bookingHistory.add(new Reservation("R102", "Bob", "Standard"));

        inventory.put("Standard", inventory.get("Standard") - 1);

        // Display current state
        System.out.println("\nCurrent Booking History:");
        for (Reservation r : bookingHistory) {
            System.out.println(r);
        }

        System.out.println("\nCurrent Inventory:");
        System.out.println(inventory);

        // Save state before shutdown
        SystemState newState = new SystemState(bookingHistory, inventory);
        PersistenceService.save(newState);
    }
}