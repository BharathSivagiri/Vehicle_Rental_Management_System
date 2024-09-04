package VRMS;

import VRMS.entities.Member;
import VRMS.entities.Vehicle;
import VRMS.exceptions.*;
import VRMS.functionalities.VehicleRentalSystem;

import java.util.*;
import java.util.Scanner;

public class VehicleRentalManagementSystem
{
    public static void main(String[] args)
    {
        VehicleRentalSystem vehicleRentalSystem = new VehicleRentalSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. Add Member");
            System.out.println("4. Remove Member");
            System.out.println("5. Rent Vehicle");
            System.out.println("6. View Vehicles");
            System.out.println("7. View Members");
            System.out.println("8. View Transactions");
            System.out.println("9. Save Transactions");
            System.out.println("10. Load Transactions");
            System.out.println("11. Filter Vehicles");
            System.out.println("12. Filter Members");
            System.out.println("13. Exit");


            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
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
                case 2:
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
                case 3:
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
                    System.out.print("Is member special? (true/false): ");
                    boolean isSpecialMember = scanner.nextBoolean();
                    Member member = new Member(memberName, memberId,memberPhone, memberEmail, isSpecialMember);
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
                case 4:
                    // Remove member
                    System.out.println("Enter member details:");
                    System.out.print("Enter member ID: ");
                    String memberIdToRemove = scanner.next();
                    Member memberToRemove = new Member(memberIdToRemove, "", "", "", false);
                    try
                    {
                        vehicleRentalSystem.removeMember(memberToRemove);
                    }
                    catch (MemberNotFoundException e)
                    {
                        System.out.println("Member specified not found");
                    }
                    break;
                case 5:
                    // Rent vehicle
                    System.out.println("Enter vehicle and member details:");
                    System.out.print("Enter vehicle number: ");
                    String vehicleNumberToRent = scanner.next();
                    Vehicle vehicleToRent = new Vehicle(vehicleNumberToRent, "", "", 0.0);
                    System.out.print("Enter member ID: ");
                    String memberIdToRent = scanner.next();
                    Member memberToRent = new Member(memberIdToRent, "", "", "", false);
                    System.out.print("Enter rental duration: ");
                    int rentalDuration = scanner.nextInt();
                    try
                    {
                        vehicleRentalSystem.rentVehicle(vehicleToRent, memberToRent, rentalDuration);
                        System.out.println("Vehicle rented successfully");
                    }
                    catch (VehicleNotAvailableException e)
                    {
                        System.out.println("Specified vehicle is not available for now please try again later");
                    }
                    break;
                case 6:
                    // View vehicles
                    vehicleRentalSystem.getVehicles();
                    break;
                case 7:
                    // View members
                    vehicleRentalSystem.getMembers();
                    break;
                case 8:
                    // View transactions
                    vehicleRentalSystem.viewRentalTransactions();
                    break;
                case 9:
                    // Save transactions
                    vehicleRentalSystem.saveRentalTransactions();
                    break;
                case 10:
                    // Load transactions
                    vehicleRentalSystem.loadRentalTransactions();
                    break;
                case 11:
                    // Filter vehicles
                    System.out.print("Enter vehicle type to filter: ");
                    String vehicleTypeToFilter = scanner.next();
                    vehicleRentalSystem.filterVehiclesByType(vehicleTypeToFilter);
                    break;
                case 12:
                    System.out.print(" Members based on the Subscriptions: ");
                    String memberType = scanner.next();
                    vehicleRentalSystem.filterMembers(memberType);
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

