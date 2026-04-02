// Domain Model: Room
internal class Room(val type: String?, val price: Double, val amenities: MutableList<String?>?)

// Inventory (State Holder)
internal class Inventory {
    private val availability: MutableMap<String?, Int?> = HashMap<String?, Int?>()

    fun addRoom(type: String?, count: Int) {
        availability.put(type, count)
    }

    // Read-only access
    fun getAvailability(type: String?): Int {
        return availability.getOrDefault(type, 0)!!
    }

    val allRoomTypes: MutableSet<String?>
        get() = availability.keys
}

// Search Service (Read-only logic)
internal class SearchService(private val inventory: Inventory, private val roomCatalog: MutableMap<String?, Room?>) {
    fun searchAvailableRooms() {
        println("\nAvailable Rooms:\n")

        for (type in inventory.allRoomTypes) {
            val count = inventory.getAvailability(type)

            // Validation Logic: Filter unavailable rooms
            if (count > 0) {
                val room = roomCatalog.get(type)

                // Defensive Programming: null check
                if (room != null) {
                    println("Room Type: " + room.type)
                    println("Price: ₹" + room.price)
                    println("Amenities: " + room.amenities)
                    println("Available Units: " + count)
                    println("-----------------------------------")
                }
            }
        }
    }
}

// Main Class
object UseCase4RoomSearch {
    @JvmStatic
    fun main(args: Array<String>) {
        // Step 1: Setup Inventory

        val inventory = Inventory()
        inventory.addRoom("Single", 5)
        inventory.addRoom("Double", 0) // unavailable
        inventory.addRoom("Suite", 2)

        // Step 2: Setup Room Catalog (Domain Model)
        val roomCatalog: MutableMap<String?, Room?> = HashMap<String?, Room?>()

        roomCatalog.put(
            "Single", Room(
                "Single",
                2000.0,
                mutableListOf<String?>("WiFi", "TV", "AC")
            )
        )

        roomCatalog.put(
            "Double", Room(
                "Double",
                3500.0,
                mutableListOf<String?>("WiFi", "TV", "AC", "Mini Bar")
            )
        )

        roomCatalog.put(
            "Suite", Room(
                "Suite",
                6000.0,
                mutableListOf<String?>("WiFi", "TV", "AC", "Mini Bar", "Jacuzzi")
            )
        )

        // Step 3: Search Service (Read-only)
        val searchService = SearchService(inventory, roomCatalog)

        // Step 4: Guest triggers search
        searchService.searchAvailableRooms()
    }
}
