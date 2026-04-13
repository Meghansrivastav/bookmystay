import java.util.*;

// Reservation class (same as Use Case 5)
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Reservation [Guest=" + guestName + ", RoomType=" + roomType + "]";
    }
}

// Booking Request Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Inventory Service
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void showInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

// Booking Service (Core Logic)
class BookingService {

    private InventoryService inventoryService;

    // Track ALL allocated room IDs (global uniqueness)
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Track room type -> assigned room IDs
    private Map<String, Set<String>> roomAllocationMap = new HashMap<>();

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void processRequest(Reservation reservation) {

        String roomType = reservation.getRoomType();

        System.out.println("\nProcessing: " + reservation);

        // Check availability
        if (!inventoryService.isAvailable(roomType)) {
            System.out.println("No rooms available for " + roomType);
            return;
        }

        // Generate unique room ID
        String roomId;
        do {
            roomId = roomType.substring(0, 1).toUpperCase() + (int)(Math.random() * 1000);
        } while (allocatedRoomIds.contains(roomId));

        // Allocate room (atomic logical step)
        allocatedRoomIds.add(roomId);

        roomAllocationMap.putIfAbsent(roomType, new HashSet<>());
        roomAllocationMap.get(roomType).add(roomId);

        // Update inventory immediately
        inventoryService.decrement(roomType);

        // Confirm booking
        System.out.println("Booking Confirmed!");
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Type: " + roomType);
        System.out.println("Allocated Room ID: " + roomId);
    }

    public void showAllocations() {
        System.out.println("\nRoom Allocations:");
        for (String type : roomAllocationMap.keySet()) {
            System.out.println(type + " -> " + roomAllocationMap.get(type));
        }
    }
}

// Main Class
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        BookingRequestQueue queue = new BookingRequestQueue();
        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        // Add booking requests (FIFO)
        queue.addRequest(new Reservation("Alice", "Deluxe"));
        queue.addRequest(new Reservation("Bob", "Standard"));
        queue.addRequest(new Reservation("Charlie", "Suite"));
        queue.addRequest(new Reservation("David", "Suite")); // should fail

        // Process queue
        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();
            bookingService.processRequest(r);
        }

        // Show results
        bookingService.showAllocations();
        inventory.showInventory();
    }
}