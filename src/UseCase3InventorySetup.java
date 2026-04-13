import java.util.HashMap;
import java.util.Map;

// Centralized Inventory Class (Version 3.1 - Refactored)
class RoomInventory {

    // HashMap to store room type -> available count
    private Map<String, Integer> inventory;

    // Constructor to initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initial room setup
        inventory.put("Standard", 5);
        inventory.put("Deluxe", 3);
        inventory.put("Suite", 2);
    }

    // Get availability of a specific room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (controlled update)
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
            System.out.println("Updated " + roomType + " rooms to " + newCount);
        } else {
            System.out.println("Room type does not exist.");
        }
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// Main Class
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Retrieve availability
        System.out.println("\nChecking availability:");
        System.out.println("Deluxe rooms available: " + inventory.getAvailability("Deluxe"));

        // Update availability
        System.out.println("\nUpdating inventory:");
        inventory.updateAvailability("Deluxe", 2);

        // Display updated inventory
        inventory.displayInventory();
    }
}