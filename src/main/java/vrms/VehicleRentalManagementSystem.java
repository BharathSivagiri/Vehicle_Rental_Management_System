package vrms;

import vrms.entities.Member;
import vrms.entities.Vehicle;
import vrms.exceptions.*;
import vrms.functionalities.VehicleRentalSystem;

import java.util.Scanner;
import java.util.regex.Pattern;

import static vrms.entities.VehicleStatus.AVAILABLE;

public class VehicleRentalManagementSystem
{
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");
    private static final Pattern MEMBERID_PATTERN = Pattern.compile("^[SN]M\\d{3}$");

    public static void main(String[] args) {
        VehicleRentalSystem vehicleRentalSystem = new VehicleRentalSystem();
        vehicleRentalSystem.initialData();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n" +
                    "╔══════════════════════════════════════╗\n" +
                    "║   Vehicle Rental Management System   ║\n" +
                    "╠══════════════════════════════════════╣\n" +
                    "║ Customer Operations:                 ║\n" +
                    "║ 1. Add Member      2. Remove Member  ║\n" +
                    "║ 3. View Members    4. Filter Members ║\n" +
                    "║ 5. Update Member                     ║\n" +
                    "║                                      ║\n" +
                    "║ Vehicle Operations:                  ║\n" +
                    "║ 6. Add Vehicle    7. Remove Vehicle  ║\n" +
                    "║ 8. View Vehicles  9. Filter Vehicles ║\n" +
                    "║ 10. Update Vehicle                   ║\n" +
                    "║                                      ║\n" +
                    "║ Rental Operations:                   ║\n" +
                    "║  11. Rent Vehicle                    ║\n" +
                    "║  12. View Transactions               ║\n" +
                    "║  13. Save Transactions               ║\n" +
                    "║  14. Load Transactions               ║\n" +
                    "║                                      ║\n" +
                    "║  15. Exit                            ║\n" +
                    "╚══════════════════════════════════════╝\n");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                // Customer Operations
                case 1:
                    // Add member

                    System.out.println("Enter member details");

                    String memberName;
                    do {
                        System.out.print("Enter member name: ");
                        memberName = scanner.next();
                        if (!NAME_PATTERN.matcher(memberName).matches()) {
                            System.out.println("Invalid name format");
                        }
                    } while(!NAME_PATTERN.matcher(memberName).matches());

                    String memberId;
                    do {
                        System.out.print("Enter member ID: ");
                        memberId = scanner.next();
                        if (!MEMBERID_PATTERN.matcher(memberId).matches()) {
                            System.out.println("Invalid member ID format");
                        }
                    } while(!MEMBERID_PATTERN.matcher(memberId).matches());

                    String memberPhone;
                    do {
                        System.out.print("Enter member phone: ");
                        memberPhone = scanner.next();
                        if (!PHONE_PATTERN.matcher(memberPhone).matches()) {
                            System.out.println("Invalid phone number format");
                        }
                    } while(!PHONE_PATTERN.matcher(memberPhone).matches());

                    String memberEmail;
                    do {
                        System.out.print("Enter member email: ");
                        memberEmail = scanner.next();
                        if (!EMAIL_PATTERN.matcher(memberEmail).matches()) {
                            System.out.println("Invalid email format");
                        }
                    } while (!EMAIL_PATTERN.matcher(memberEmail).matches());

                    System.out.print("Premium Member? (true/false): ");
                    boolean isSpecialMember = scanner.nextBoolean();
                    Member member = new Member(memberId,memberName, memberEmail, memberPhone, isSpecialMember);
                    try
                    {
                        vehicleRentalSystem.addMember(member);
                        System.out.println("Member added successfully");
                    }
                    catch (DuplicateMemberException e)
                    {
                        System.out.println("Duplicate member found");
                    }
                    catch (InputException e)
                    {
                        System.out.println("Error: ");
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
                    System.out.print("Members based on the Subscriptions: ");
                    String memberType = scanner.next();
                    try {
                        vehicleRentalSystem.filterMembers(memberType);
                    } catch (MemberNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 5:
                    // Update member
                    System.out.println("Enter member details to update:");
                    System.out.print("Enter member ID: ");
                    String memberIdToUpdate = scanner.next();
                    System.out.print("Enter new member name: ");
                    String newMemberName = scanner.next();
                    System.out.print("Enter new member phone: ");
                    String newMemberPhone = scanner.next();
                    System.out.print("Enter new member email: ");
                    String newMemberEmail = scanner.next();
                    System.out.print("New Premium Member status? (true/false): ");
                    boolean newIsSpecialMember = scanner.nextBoolean();
                    Member updatedMember = new Member(newMemberName, memberIdToUpdate, newMemberEmail, newMemberPhone, newIsSpecialMember);
                    try {
                        vehicleRentalSystem.updateMember(memberIdToUpdate, updatedMember);
                    } catch (MemberNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                // Vehicle Operations
                case 6:
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
                    Vehicle vehicle = new Vehicle(vehicleNumber, vehicleType, vehicleModel, vehicleRentalPrice, AVAILABLE);
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
                case 7:
                    // Remove vehicle
                    System.out.println("Enter vehicle details:");
                    System.out.print("Enter vehicle number: ");
                    String vehicleNumberToRemove = scanner.next();
                    Vehicle vehicleToRemove = new Vehicle(vehicleNumberToRemove, "", "", 0.0, AVAILABLE);
                    try {
                        vehicleRentalSystem.removeVehicle(vehicleToRemove);
                    } catch (VehicleNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 8:
                    // View vehicles
                    vehicleRentalSystem.getVehicles();
                    break;
                case 9:
                    // Filter vehicles
                    System.out.print("Enter vehicle type to filter: ");
                    String vehicleTypeToFilter = scanner.next();
                    vehicleRentalSystem.filterVehiclesByType(vehicleTypeToFilter);
                    break;
                case 10:
                    // Update vehicle
                    System.out.println("Enter vehicle details to update:");
                    System.out.print("Enter vehicle number: ");
                    String vehicleNumberToUpdate = scanner.next();
                    System.out.print("Enter new vehicle type: ");
                    String newVehicleType = scanner.next();
                    System.out.print("Enter new vehicle model: ");
                    String newVehicleModel = scanner.next();
                    System.out.print("Enter new vehicle rental price: ");
                    double newVehicleRentalPrice = scanner.nextDouble();
                    Vehicle updatedVehicle = new Vehicle(vehicleNumberToUpdate, newVehicleType, newVehicleModel, newVehicleRentalPrice, AVAILABLE);
                    try {
                        vehicleRentalSystem.updateVehicle(updatedVehicle);
                    } catch (VehicleNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                // Rental Operations
                case 11:
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
                    } catch (MemberNotFoundException e) {
                        System.out.println(e.getMessage());
                    } catch (VehicleNotFoundException e) {
                        System.out.println(e.getMessage());
                    } catch (VehicleNotAvailableException e) {
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Error: An unexpected error occurred");
                    }
                    break;
                case 12:
                    // View transactions
                    try {
                        vehicleRentalSystem.viewRentalTransactions();
                    } catch (IllegalStateException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 13:
                    // Save transactions
                    vehicleRentalSystem.saveRentalTransactions();
                    break;
                case 14:
                    // Load transactions
                    vehicleRentalSystem.loadRentalTransactions();
                    break;
                case 15:
                    // Exit
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
