import java.util.HashMap;
import java.util.Map;

// Version 3.1 (Refactored)
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor
    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Add room type
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int change) {
        if (inventory.containsKey(roomType)) {
            int current = inventory.get(roomType);
            int updated = current + change;

            if (updated < 0) {
                System.out.println("Error: Not enough rooms available for " + roomType);
            } else {
                inventory.put(roomType, updated);
            }
        } else {
            System.out.println("Room type not found: " + roomType);
        }
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Main class renamed to ucs3
public class ucs3 {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        inventory.addRoomType("Single", 10);
        inventory.addRoomType("Double", 5);
        inventory.addRoomType("Suite", 2);

        inventory.displayInventory();

        System.out.println("\nBooking 2 Single rooms...");
        inventory.updateAvailability("Single", -2);

        System.out.println("Cancelling 1 Double room...");
        inventory.updateAvailability("Double", +1);

        System.out.println("\nAvailable Suites: " + inventory.getAvailability("Suite"));

        inventory.displayInventory();
    }
}