/**
 * UseCase2RoomInitialization
 *
 * This class demonstrates basic room modeling using abstraction,
 * inheritance, and polymorphism in a Hotel Booking System.
 * It initializes different room types and displays their availability.
 *
 * @author YourName
 * @version 2.1
 */
// Abstract class
internal abstract class Room // Constructor
    (protected var roomType: String?, protected var numberOfBeds: Int, protected var price: Double) {
    // Method to display room details
    fun displayRoomDetails() {
        println("Room Type: " + roomType)
        println("Number of Beds: " + numberOfBeds)
        println("Price per Night: ₹" + price)
    }
}

// Single Room class
internal class SingleRoom : Room("Single Room", 1, 2000.0)

// Double Room class
internal class DoubleRoom : Room("Double Room", 2, 3500.0)

// Suite Room class
internal class SuiteRoom : Room("Suite Room", 3, 6000.0)

// Main application class
object UseCase2RoomInitialization {
    @JvmStatic
    fun main(args: Array<String>) {
        println("======================================")
        println("   Welcome to Book My Stay App")
        println("   Hotel Booking System v2.1")
        println("======================================\n")

        // Creating room objects (Polymorphism)
        val singleRoom: Room = SingleRoom()
        val doubleRoom: Room = DoubleRoom()
        val suiteRoom: Room = SuiteRoom()

        // Static availability variables
        val singleRoomAvailability = 5
        val doubleRoomAvailability = 3
        val suiteRoomAvailability = 2

        // Display details
        println("----- Room Details & Availability -----\n")

        singleRoom.displayRoomDetails()
        println("Available Rooms: " + singleRoomAvailability)
        println("--------------------------------------")

        doubleRoom.displayRoomDetails()
        println("Available Rooms: " + doubleRoomAvailability)
        println("--------------------------------------")

        suiteRoom.displayRoomDetails()
        println("Available Rooms: " + suiteRoomAvailability)
        println("--------------------------------------")

        println("\nApplication terminated successfully.")
    }
}