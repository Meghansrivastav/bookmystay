import java.util.*;

// Room class (Domain Model)
class Room {
    private String type;
    private double price;
    private List<String> amenities;

    public Room(String type, double price, List<String> amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Price: ₹" + price);
        System.out.println("Amenities: " + amenities);
        System.out.println("---------------------------");
    }
}

// Inventory class (State Holder)
class Inventory {
    private Map<String, Integer> roomAvailability;

    public Inventory() {
        roomAvailability = new HashMap<>();
    }

    public void addRoom(String type, int count) {
        roomAvailability.put(type, count);
    }

    // READ-ONLY access
    public int getAvailability(String type) {
        return roomAvailability.getOrDefault(type, 0);
    }

    public Set<String> getRoomTypes() {
        return roomAvailability.keySet();
    }
}

// Search Service (Read-only logic)
class SearchService {

    public static void searchAvailableRooms(Inventory inventory, Map<String, Room> roomData) {

        System.out.println("\nAvailable Rooms:\n");

        for (String type : inventory.getRoomTypes()) {

            int available = inventory.getAvailability(type);

            // Validation: only show rooms with availability > 0
            if (available > 0) {

                Room room = roomData.get(type);

                if (room != null) { // Defensive check
                    room.displayRoomDetails();
                    System.out.println("Available Count: " + available);
                    System.out.println("===========================");
                }
            }
        }
    }
}

// Main Class
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        // Step 1: Create Inventory
        Inventory inventory = new Inventory();
        inventory.addRoom("Single", 5);
        inventory.addRoom("Double", 0);
        inventory.addRoom("Suite", 3);

        // Step 2: Create Room Data (Domain Objects)
        Map<String, Room> roomData = new HashMap<>();

        roomData.put("Single", new Room(
                "Single", 2000,
                Arrays.asList("WiFi", "TV")));

        roomData.put("Double", new Room(
                "Double", 3500,
                Arrays.asList("WiFi", "TV", "AC")));

        roomData.put("Suite", new Room(
                "Suite", 5000,
                Arrays.asList("WiFi", "TV", "AC", "Mini Bar")));

        // Step 3: Guest searches rooms
        System.out.println("Guest searching for rooms...");
        SearchService.searchAvailableRooms(inventory, roomData);
    }
}
