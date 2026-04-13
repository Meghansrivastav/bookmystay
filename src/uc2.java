/**
 * UseCase2RoomInitialization
 *
 * This class demonstrates basic room modeling using abstraction,
 * inheritance, and static availability representation.
 *
 * @author YourName
 * @version 2.1
 */

// Abstract Room class
abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    // Constructor
    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    // Method to display room details
    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price per night: ₹" + price);
    }
}

// Single Room class
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000);
    }
}

// Double Room class
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }
}

// Suite Room class
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }
}

// Main Application Class
public class uc2 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("      Book My Stay Application");
        System.out.println("      Hotel Booking System v2.1");
        System.out.println("=====================================");

        // Create room objects (Polymorphism)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability (simple variables)
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display details
        System.out.println("\n--- Room Details ---\n");

        single.displayDetails();
        System.out.println("Available Rooms: " + singleAvailable);
        System.out.println();

        doubleRoom.displayDetails();
        System.out.println("Available Rooms: " + doubleAvailable);
        System.out.println();

        suite.displayDetails();
        System.out.println("Available Rooms: " + suiteAvailable);
    }
}