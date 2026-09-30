import java.io.*;
import java.util.*;
class Room {
    int roomNumber;
    String category;
    double price;
    boolean available;
    Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.available = true;
    }
    void displayRoom() {
        System.out.println("Room No: " + roomNumber +
                " | Category: " + category +
                " | Price: Rs." + price +
                " | Available: " + (available ? "Yes" : "No"));
    }
}
class Reservation {
    int reservationId;
    String customerName;
    int roomNumber;
    String category;
    double amount;
    Reservation(int reservationId, String customerName,
                int roomNumber, String category, double amount) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.category = category;
        this.amount = amount;
    }
    void displayBooking() {
        System.out.println("\n----- Booking Details -----");
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Customer Name  : " + customerName);
        System.out.println("Room Number    : " + roomNumber);
        System.out.println("Room Category  : " + category);
        System.out.println("Amount Paid    : Rs." + amount);
        System.out.println("---------------------------");
    }
}
public class HotelReservationSystem {
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static int reservationCounter = 1001;
    // Add rooms
    static void initializeRooms() {
        rooms.add(new Room(101, "Standard", 1500));
        rooms.add(new Room(102, "Standard", 1500));
        rooms.add(new Room(201, "Deluxe", 2500));
        rooms.add(new Room(202, "Deluxe", 2500));
        rooms.add(new Room(301, "Suite", 4000));
        rooms.add(new Room(302, "Suite", 4000));
    }
    // Search available rooms
    static void searchRooms() {
        System.out.println("\nSelect Category:");
        System.out.println("1. Standard");
        System.out.println("2. Deluxe");
        System.out.println("3. Suite");
        int choice = sc.nextInt();
        String category;
        if (choice == 1)
            category = "Standard";
        else if (choice == 2)
            category = "Deluxe";
        else if (choice == 3)
            category = "Suite";
        else {
            System.out.println("Invalid choice!");
            return;
        }
        System.out.println("\nAvailable " + category + " Rooms:");
        boolean found = false;
        for (Room room : rooms) {
            if (room.category.equals(category) && room.available) {
                room.displayRoom();
                found = true;
            }
        }
        if (!found)
            System.out.println("No rooms available.");
    }
    // Book room
    static void bookRoom() {
        sc.nextLine();
        System.out.print("\nEnter Customer Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Room Number: ");
        int roomNumber = sc.nextInt();
        Room selectedRoom = null;
        for (Room room : rooms) {
            if (room.roomNumber == roomNumber) {
                selectedRoom = room;
                break;
            }
        }
        if (selectedRoom == null) {
            System.out.println("Room not found!");
            return;
        }
        if (!selectedRoom.available) {
            System.out.println("Room is already booked!");
            return;
        }
        System.out.println("\nRoom Details:");
        selectedRoom.displayRoom();
        System.out.print("\nConfirm booking? (yes/no): ");
        String confirm = sc.next();
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Booking cancelled.");
            return;
        }
        // Payment simulation
        System.out.println("\n----- Payment -----");
        System.out.println("Amount: Rs." + selectedRoom.price);
        System.out.print("Enter payment amount: Rs.");
        double payment = sc.nextDouble();
        if (payment < selectedRoom.price) {
            System.out.println("Payment failed! Insufficient amount.");
            return;
        }
        System.out.println("Payment successful!");
        Reservation reservation = new Reservation(
                reservationCounter++,
                name,
                selectedRoom.roomNumber,
                selectedRoom.category,
                selectedRoom.price
        );
        reservations.add(reservation);
        selectedRoom.available = false;
        saveBookings();
        System.out.println("\nRoom booked successfully!");
        reservation.displayBooking();
    }
    // Cancel reservation
    static void cancelReservation() {
        System.out.print("\nEnter Reservation ID: ");
        int id = sc.nextInt();
        Reservation found = null;
        for (Reservation reservation : reservations) {
            if (reservation.reservationId == id) {
                found = reservation;
                break;
            }
        }
        if (found == null) {
            System.out.println("Reservation not found!");
            return;
        }
        // Make room available again
        for (Room room : rooms) {
            if (room.roomNumber == found.roomNumber) {
                room.available = true;
                break;
            }
        }
        reservations.remove(found);
        saveBookings();
        System.out.println("Reservation cancelled successfully.");
    }
    // View all bookings
    static void viewBookings() {
        if (reservations.isEmpty()) {
            System.out.println("\nNo bookings available.");
            return;
        }
        System.out.println("\n===== ALL BOOKINGS =====");
        for (Reservation reservation : reservations) {
            reservation.displayBooking();
        }
    }
    // Save bookings into file
    static void saveBookings() {
        try {
            FileWriter writer = new FileWriter("bookings.txt");
            for (Reservation reservation : reservations) {
                writer.write(
                        reservation.reservationId + "," +
                        reservation.customerName + "," +
                        reservation.roomNumber + "," +
                        reservation.category + "," +
                        reservation.amount + "\n"
                );
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving bookings.");
        }
    }
    // Main method
    public static void main(String[] args) {
        initializeRooms();
        while (true) {
            System.out.println("\n==============================");
            System.out.println("   HOTEL RESERVATION SYSTEM");
            System.out.println("==============================");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");
            System.out.println("==============================");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    searchRooms();
                    break;
                case 2:
                    bookRoom();
                    break;
                case 3:
                    cancelReservation();
                    break;
                case 4:
                    viewBookings();
                    break;
                case 5:
                    System.out.println("Thank you for using the Hotel Reservation System!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}