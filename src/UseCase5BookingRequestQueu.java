import java.util.LinkedList;
import java.util.Queue;

// Reservation class representing a booking request
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

// Booking Request Queue Manager
class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Request added: " + reservation);
    }

    // View all requests (without removing)
    public void viewRequests() {
        if (requestQueue.isEmpty()) {
            System.out.println("No booking requests in queue.");
            return;
        }

        System.out.println("\nBooking Requests in Queue (FIFO Order):");
        for (Reservation r : requestQueue) {
            System.out.println(r);
        }
    }

    // Process next request (for future use case)
    public Reservation getNextRequest() {
        return requestQueue.poll(); // removes in FIFO order
    }
}

// Main class
public class UseCase5BookingRequestQueue {
    public static void main(String[] args) {

        BookingRequestQueue queue = new BookingRequestQueue();

        // Simulating multiple booking requests
        queue.addRequest(new Reservation("Alice", "Deluxe"));
        queue.addRequest(new Reservation("Bob", "Standard"));
        queue.addRequest(new Reservation("Charlie", "Suite"));

        // View queue (no allocation yet)
        queue.viewRequests();

        // Demonstrating FIFO behavior
        System.out.println("\nProcessing next request:");
        Reservation next = queue.getNextRequest();
        System.out.println("Processing: " + next);

        // View remaining queue
        queue.viewRequests();
    }
}