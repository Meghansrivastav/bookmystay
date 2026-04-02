internal class RoomInventory {
    // Centralized inventory storage
    private val inventory: MutableMap<String?, Int?>

    // Constructor: initialize inventory
    init {
        inventory = HashMap<String?, Int?>()
    }

    // Register a room type with initial count
    fun addRoomType(roomType: String?, count: Int) {
        inventory.put(roomType, count)
    }

    // Get availability of a specific room type
    fun getAvailability(roomType: String?): Int {
        return inventory.getOrDefault(roomType, 0)!!
    }

    // Update availability (increase or decrease)
    fun updateAvailability(roomType: String?, change: Int) {
        val current: Int = inventory.getOrDefault(roomType, 0)!!
        val updated = current + change

        if (updated < 0) {
            println("Error: Not enough rooms available for " + roomType)
        } else {
            inventory.put(roomType, updated)
        }
    }

    // Display full inventory
    fun displayInventory() {
        println("Current Room Inventory:")
        for (entry in inventory.entries) {
            println(entry.key + " : " + entry.value)
        }
    }
}