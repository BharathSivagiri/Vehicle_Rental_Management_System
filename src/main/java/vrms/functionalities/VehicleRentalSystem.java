package vrms.functionalities;

import vrms.entities.Member;
import vrms.entities.RentalTransaction;
import vrms.entities.Vehicle;
import vrms.entities.VehicleStatus;
import vrms.exceptions.*;
import vrms.service.MemberInterface;
import vrms.service.VehicleInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static vrms.entities.VehicleStatus.AVAILABLE;
import static vrms.entities.VehicleStatus.RENTED;


public class VehicleRentalSystem implements VehicleInterface, MemberInterface, Serializable {
    private final List<Vehicle> vehicles;
    private final List<Member> members;
    private List<RentalTransaction> rentalTransactions;
    int lastTransactionID = 0;
    private static final String TRANSAC_PATH = "D:\\Aaludra Technology Solutions\\Training\\Tasks\\Vehicle_Rental_Management_System\\src\\main\\java\\vrms\\rental_transactions.txt";

    @Serial
    private static final long serialVersionUID = 1L;

    public VehicleRentalSystem() {
        this.vehicles = new ArrayList<>();
        this.members = new ArrayList<>();
        this.rentalTransactions = new ArrayList<>();
    }

    public void initialData() {
        vehicles.add(new Vehicle("TN-33-AS-2452", "Car", "Sedan", 1000, AVAILABLE));
        vehicles.add(new Vehicle("TN-33-BK-7891", "Bike", "Sports", 500, AVAILABLE));
        vehicles.add(new Vehicle("TN-33-CL-4567", "Truck", "Pickup", 1500, AVAILABLE));
        vehicles.add(new Vehicle("TN-33-DM-2345", "Car", "SUV", 1200, AVAILABLE));
        vehicles.add(new Vehicle("TN-33-GH-6789", "Bike", "Cruiser", 600, AVAILABLE));

        members.add(new Member("SM001", "John", "john@example.com", "9876543210", true));
        members.add(new Member("NM002", "Smith", "jane@example.com", "9762899934", false));
        members.add(new Member("SM003", "Bob", "bob@example.com", "8524133625", true));
        members.add(new Member("NM004", "Alice", "alice@example.com", "7741522581", false));
        members.add(new Member("SM005", "Charlie", "charlie@example.com", "9952144589", true));
    }

    // CRUD operations for vehicles

    @Override
    public void addVehicle(Vehicle vehicle) throws DuplicateVehicleException {
        if (vehicles.stream()
                .anyMatch(v -> v.getVehicleNumber()
                        .equals(vehicle.getVehicleNumber()))) {
            throw new DuplicateVehicleException("Vehicle already exists");
        }
        vehicles.add(vehicle);
    }

    @Override
    public void removeVehicle(String vehicleNumber) throws VehicleNotFoundException {
        if (!vehicles.removeIf(v -> v.getVehicleNumber().equals(vehicleNumber)))
            System.out.println("Removed successfully");
        {
            throw new VehicleNotFoundException("Vehicle with number " + vehicleNumber + " not found.");
        }
    }

    @Override
    public void updateVehicle(Vehicle updatedVehicle) throws VehicleNotFoundException {
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getVehicleNumber().equals(updatedVehicle.getVehicleNumber())) {
                vehicles.set(i, updatedVehicle);
                System.out.println("Vehicle updated successfully.");
                return;
            }
        }
        throw new VehicleNotFoundException("Vehicle with number " + updatedVehicle.getVehicleNumber() + " not found.");
    }

    @Override
    public List<Vehicle> getVehicles() {
        try {
            boolean vehicleFound = false;
            for (Vehicle v : vehicles) {
                System.out.println(v);

                vehicleFound = true;
            }
            if (!vehicleFound) {
                throw new VehicleNotFoundException("No vehicles found in the system.");
            }
        } catch (VehicleNotFoundException e) {
            System.out.println("Exception: " + e.getMessage());
        }
        System.out.println("Vehicle Details fetched successfully");
        return vehicles;
    }

    @Override
    public List<Vehicle> filterVehiclesByType(String vehicleType) {
        List<Vehicle> filteredVehicles = vehicles.stream()
                .filter(v -> v.getVehicleType().equalsIgnoreCase(vehicleType))
                .collect(Collectors.toList());
        if (filteredVehicles.isEmpty()) {
            System.out.println("No vehicles found of type: " + vehicleType);
        } else {
            System.out.println("Vehicles of type " + vehicleType + ":");
            filteredVehicles.forEach(System.out::println);
        }
        return filteredVehicles;
    }

    // CRUD operations for members
    @Override
    public void addMember(Member member) throws DuplicateMemberException, InputException {
        if (members.stream().anyMatch(m -> m.getMemberId()
                .equals(member.getMemberId()))) {
            throw new DuplicateMemberException("Member already exists");
        }
        members.add(member);
    }

    @Override
    public void removeMember(String memberId) throws MemberNotFoundException {
        boolean removed = members.removeIf(member -> member.getMemberId().equals(memberId));
        if (removed) {
            System.out.println("Member with ID " + memberId + " has been removed successfully.");
        } else {
            throw new MemberNotFoundException("Member with ID " + memberId + " not found");
        }
    }

    @Override
    public void updateMember(String memberId, Member updatedMember) throws MemberNotFoundException {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getMemberId().equals(memberId)) {
                members.set(i, updatedMember);
                System.out.println("Member updated successfully.");
                return;
            }
        }
        throw new MemberNotFoundException("Member with ID " + memberId + " not found.");

    }

    @Override
    public List<Member> getMembers() {
        try {
            boolean memberFound = false;
            for (Member m : members) {
                System.out.println(m);
                memberFound = true;
            }
            if (!memberFound) {
                throw new MemberNotFoundException("No members found in the system.");
            }
        } catch (MemberNotFoundException e) {
            System.out.println("Exception: " + e.getMessage());
        }
        System.out.println("Member Details fetched successfully");
        return members;

    }

    @Override
    public List<Member> filterMembers(String memberType) throws MemberNotFoundException {
        List<Member> filteredMembers;

        if (members.isEmpty()) {
            throw new MemberNotFoundException("No members found in the system.");
        }

        if (memberType.equalsIgnoreCase("Premium")) {
            filteredMembers = members.stream()
                    .filter(Member::isSpecialMember)
                    .collect(Collectors.toList());
            if (filteredMembers.isEmpty()) {
                throw new MemberNotFoundException("No special members found.");
            }
            System.out.println("Premium Members:");
        } else if (memberType.equalsIgnoreCase("regular")) {
            filteredMembers = members.stream()
                    .filter(member -> !member.isSpecialMember())
                    .collect(Collectors.toList());
            if (filteredMembers.isEmpty()) {
                throw new MemberNotFoundException("No regular members found.");
            }
            System.out.println("Regular Members:");
        } else {
            filteredMembers = new ArrayList<>(members);
            System.out.println("All Members:");
        }

        filteredMembers.forEach(System.out::println);
        return filteredMembers;
    }


    // Rental process
    public void displayAvailableVehicles(String memberId) throws MemberNotFoundException {
        Member member = members.stream()
                .filter(m -> m.getMemberId().equals(memberId))
                .findFirst()
                .orElseThrow(() -> new MemberNotFoundException("Member not found"));

        boolean isSpecialMember = member.isSpecialMember();
        System.out.println("Available vehicles for " + (isSpecialMember ? "premium (10% Discount applied)" : "regular") + " member:");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the vehicle type you're looking for: ");
        String desiredVehicleType = scanner.nextLine().trim().toLowerCase();

        vehicles.stream()
                .filter(v -> v.getStatus() == AVAILABLE)
                .filter(v -> v.getVehicleType().toLowerCase().equals(desiredVehicleType))
                .forEach(v -> {
                    double price = v.getRentalPrice();
                    if (isSpecialMember) {
                        price *= 0.9;
                    }
                    System.out.printf("%s - Type: %s, Model: %s, Price: Rs.%.2f per day%n",
                            v.getVehicleNumber(), v.getVehicleType(), v.getVehicleModel(), price);
                });
    }

    public void rentVehicle(String memberId, String vehicleNumber, int rentalDuration) throws VehicleNotAvailableException, MemberNotFoundException, VehicleNotFoundException {
        Member member = members.stream()
                .filter(m -> m.getMemberId().equals(memberId))
                .findFirst()
                .orElseThrow(() -> new MemberNotFoundException("Member not found"));

        Vehicle vehicle = vehicles.stream()
                .filter(v -> v.getVehicleNumber().equals(vehicleNumber) && v.getStatus() == AVAILABLE)
                .findFirst()
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));

        if (vehicle.getStatus() != AVAILABLE) {
            throw new VehicleNotAvailableException("Vehicle is not available for rent");
        }
        vehicle.setStatus(RENTED);

        members.stream()
                .filter(m -> m.getMemberId().equals(memberId))
                .findFirst()
                .orElseThrow(() -> new MemberNotFoundException("Member not found"));


        double rentalPrice = vehicle.getRentalPrice();
        if (member.isSpecialMember()) {
            rentalPrice *= 0.9;
        }


        double totalRentalCost = rentalPrice * rentalDuration;

        RentalTransaction transaction = new RentalTransaction(vehicle, member, rentalPrice, rentalDuration, generateTransactionID());
        rentalTransactions.add(transaction);
        vehicle.setAvailable(false);

        System.out.printf("Vehicle %s rented to member %s for %d days at Rs.%.2f per day. Total amount: Rs.%.2f%n Transaction ID: %s%n",
                vehicleNumber, memberId, rentalDuration, rentalPrice, totalRentalCost, transaction.getTransactionId());
    }

    public String generateTransactionID() {
        lastTransactionID++;
        return "TRANS" + String.format("%04d", lastTransactionID);
    }

    public void viewRentalTransactions() {
        if (rentalTransactions.isEmpty()) {
            throw new IllegalStateException("No transactions available. The transaction list is empty.");
        }
        for (RentalTransaction transaction : rentalTransactions)
        {
            System.out.println("Transaction Details:");
            System.out.println(" ");
            System.out.println("Transaction ID: " + transaction.getTransactionId());
            System.out.println("Vehicle: " + transaction.getVehicle().getVehicleNumber());
            System.out.println("Member: " + transaction.getMember().getMemberId());
            System.out.println("Rental Price per day: " + transaction.getRentalPrice());
            System.out.println("Total Rental Cost: " + transaction.getTotalRentalCost());
            System.out.println("Rental Duration: " + transaction.getRentalDuration());
            System.out.println();
            System.out.println("Fetched successfully");
        }
    }

    public void saveRentalTransactions() {
        boolean saveSuccessful = false;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TRANSAC_PATH))) {
            oos.writeLong(serialVersionUID);
            oos.writeObject(rentalTransactions);
            saveSuccessful = true;
        } catch (IOException e) {
            System.out.println("Error saving rental transactions: " + e.getMessage());
        }

        if (!saveSuccessful) {
            System.out.println("Failed to save rental transactions");
        } else {
            System.out.println("Rental transactions saved successfully");
        }
    }

    public void loadRentalTransactions() {
        boolean loadSuccessful = false;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TRANSAC_PATH))) {
            long savedSerialVersionUID = ois.readLong();
            if (savedSerialVersionUID != serialVersionUID) {
                throw new InvalidClassException("Incompatible class version");
            }
            rentalTransactions = (List<RentalTransaction>) ois.readObject();
            loadSuccessful = true;
            for (RentalTransaction transaction : rentalTransactions) {
                System.out.println(transaction);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading rental transactions: " + e.getMessage());
        }

        if (!loadSuccessful) {
            System.out.println("Failed to load rental transactions");
        } else {
            System.out.println("Rental transactions loaded successfully");
        }
    }

}
