package VRMS;

import VRMS.entities.Member;
import VRMS.entities.Vehicle;
import VRMS.exceptions.*;
import VRMS.functionalities.VehicleRentalSystem;

import java.util.Scanner;

public class VehicleRentalManagementSystem
{
    public static void main(String[] args) throws MemberNotFoundException
    {
        VehicleRentalSystem vehicleRentalSystem = new VehicleRentalSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n" +
                    "╔══════════════════════════════════════╗\n" +
                    "║   Vehicle Rental Management System   ║\n" +
                    "╠══════════════════════════════════════╣\n" +
                    "║ Customer Operations:                 ║\n" +
                    "║  1. Add Member     2. Remove Member  ║\n" +
                    "║  3. View Members   4. Filter Members ║\n" +
                    "║                                      ║\n" +
                    "║ Vehicle Operations:                  ║\n" +
                    "║  5. Add Vehicle   6. Remove Vehicle  ║\n" +
                    "║  7. View Vehicles 8. Filter Vehicles ║\n" +
                    "║                                      ║\n" +
                    "║ Rental Operations:                   ║\n" +
                    "║  9. Rent Vehicle                     ║\n" +
                    "║  10. View Transactions               ║\n" +
                    "║  11. Save Transactions               ║\n" +
                    "║  12. Load Transactions               ║\n" +
                    "║                                      ║\n" +
                    "║  13. Exit                            ║\n" +
                    "╚══════════════════════════════════════╝\n");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                // Customer Operations
                case 1:
                    // Add member
                    System.out.println("Enter member details:");
                    System.out.print("Enter member name: ");
                    String memberName = scanner.next();
                    System.out.print("Enter member ID: ");
                    String memberId = scanner.next();
                    System.out.print("Enter member phone: ");
                    String memberPhone = scanner.next();
                    System.out.print("Enter member email: ");
                    String memberEmail = scanner.next();
                    System.out.print("Premium Member? (true/false): ");
                    boolean isSpecialMember = scanner.nextBoolean();
                    Member member = new Member(memberName, memberId, memberEmail, memberPhone, isSpecialMember);
                    try
                    {
                        vehicleRentalSystem.addMember(member);
                        System.out.println("Member added successfully");
                    }
                    catch (DuplicateMemberException e)
                    {
                        System.out.println("Duplicate member found");
                    }
                    break;
                case 2:
                    // Remove member
                    System.out.println("Enter member details:");
                    System.out.print("Enter member ID: ");
                    String memberIdToRemove = scanner.next();
                    try {
                        vehicleRentalSystem.removeMember(memberIdToRemove);
                        System.out.println("Member removed successfully");
                    } catch (MemberNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    // View members
                    vehicleRentalSystem.getMembers();
                    break;
                case 4:
                    System.out.print(" Members based on the Subscriptions: ");
                    String memberType = scanner.next();
                    vehicleRentalSystem.filterMembers(memberType);
                    break;

                // Vehicle Operations
                case 5:
                    // Add vehicle
                    System.out.println("Enter vehicle details:");
                    System.out.print("Enter vehicle number: ");
                    String vehicleNumber = scanner.next();
                    System.out.print("Enter vehicle type: ");
                    String vehicleType = scanner.next();
                    System.out.print("Enter vehicle model: ");
                    String vehicleModel = scanner.next();
                    System.out.print("Enter vehicle rental price: ");
                    double vehicleRentalPrice = scanner.nextDouble();
                    Vehicle vehicle = new Vehicle(vehicleNumber, vehicleType, vehicleModel, vehicleRentalPrice);
                    try
                    {
                        vehicleRentalSystem.addVehicle(vehicle);
                        System.out.println("Vehicle added successfully.");
                    }
                    catch (DuplicateVehicleException e)
                    {
                        System.out.println("Error adding vehicle. Try again");
                    }
                    break;
                case 6:
                    // Remove vehicle
                    System.out.println("Enter vehicle details:");
                    System.out.print("Enter vehicle number: ");
                    String vehicleNumberToRemove = scanner.next();
                    Vehicle vehicleToRemove = new Vehicle(vehicleNumberToRemove, "", "", 0.0);
                    try {
                        vehicleRentalSystem.removeVehicle(vehicleToRemove);
                    } catch (VehicleNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 7:
                    // View vehicles
                    vehicleRentalSystem.getVehicles();
                    break;
                case 8:
                    // Filter vehicles
                    System.out.print("Enter vehicle type to filter: ");
                    String vehicleTypeToFilter = scanner.next();
                    vehicleRentalSystem.filterVehiclesByType(vehicleTypeToFilter);
                    break;

                // Rental Operations
                case 9:
                    // Rent vehicle
                    System.out.print("Enter member ID: ");
                    String memberIdToRent = scanner.next();

                    try {
                        vehicleRentalSystem.displayAvailableVehicles(memberIdToRent);

                        System.out.print("Enter vehicle number to rent: ");
                        String vehicleNumberToRent = scanner.next();

                        System.out.print("Enter rental duration (in days): ");
                        int rentalDuration = scanner.nextInt();

                        vehicleRentalSystem.rentVehicle(memberIdToRent, vehicleNumberToRent, rentalDuration);
                    } catch (MemberNotFoundException | VehicleNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (VehicleNotAvailableException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 10:
                    // View transactions
                    vehicleRentalSystem.viewRentalTransactions();
                    break;
                case 11:
                    // Save transactions
                    vehicleRentalSystem.saveRentalTransactions();
                    break;
                case 12:
                    // Load transactions
                    vehicleRentalSystem.loadRentalTransactions();
                    break;
                case 13:
                    // Exit
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
