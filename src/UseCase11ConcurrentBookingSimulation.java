import java.util.*;

// Reservation class
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
}

// Shared Booking Queue (Thread-Safe)
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
        System.out.println(Thread.currentThread().getName() +
                " added booking for " + r.getGuestName());
    }

    public synchronized Reservation getRequest() {
        return queue.poll();
    }
}

// Inventory Service (Shared Resource)
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Standard", 2);
    }

    // Critical Section (Thread-safe)
    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available <= 0) {
            return false;
        }

        // Simulate delay (to expose race condition if unsynchronized)
        try { Thread.sleep(100); } catch (InterruptedException e) {}

        inventory.put(roomType, available - 1);

        return true;
    }

    public void showInventory() {
        System.out.println("\nFinal Inventory: " + inventory);
    }
}

// Booking Processor (Thread)
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private InventoryService inventory;

    public BookingProcessor(String name, BookingQueue queue, InventoryService inventory) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation r;

            // synchronized retrieval
            synchronized (queue) {
                r = queue.getRequest();
            }

            if (r == null) break;

            // critical section for allocation
            boolean success = inventory.allocateRoom(r.getRoomType());

            if (success) {
                System.out.println(getName() + " CONFIRMED booking for " + r.getGuestName());
            } else {
                System.out.println(getName() + " FAILED booking for " + r.getGuestName());
            }
        }
    }
}

// Main Class
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        BookingQueue queue = new BookingQueue();
        InventoryService inventory = new InventoryService();

        // Simulate multiple guest requests
        queue.addRequest(new Reservation("Alice", "Standard"));
        queue.addRequest(new Reservation("Bob", "Standard"));
        queue.addRequest(new Reservation("Charlie", "Standard")); // extra request

        // Multiple threads (simulating concurrent users)
        Thread t1 = new BookingProcessor("Thread-1", queue, inventory);
        Thread t2 = new BookingProcessor("Thread-2", queue, inventory);

        // Start threads
        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {}

        // Final state
        inventory.showInventory();
    }
}