import java.util.*;

// Represents an Add-On Service
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

// Manages Add-On Services for Reservations
class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to a reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added service: " + service + " to Reservation ID: " + reservationId);
    }

    // View services for a reservation
    public void viewServices(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services for Reservation ID: " + reservationId);
            return;
        }

        System.out.println("\nServices for Reservation ID: " + reservationId);
        for (AddOnService service : services) {
            System.out.println(service);
        }
    }

    // Calculate total cost of services
    public double calculateTotalCost(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);

        double total = 0;
        if (services != null) {
            for (AddOnService service : services) {
                total += service.getCost();
            }
        }
        return total;
    }
}

// Main Class
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        // Sample reservation IDs (from Use Case 6)
        String res1 = "R101";
        String res2 = "R102";

        // Create services
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService spa = new AddOnService("Spa", 1500);

        // Add services to reservations
        manager.addService(res1, breakfast);
        manager.addService(res1, wifi);
        manager.addService(res2, spa);

        // View services
        manager.viewServices(res1);
        manager.viewServices(res2);

        // Calculate cost
        System.out.println("\nTotal Add-On Cost for " + res1 + ": ₹" + manager.calculateTotalCost(res1));
        System.out.println("Total Add-On Cost for " + res2 + ": ₹" + manager.calculateTotalCost(res2));
    }
}