package VRMS.functionalities;

import VRMS.entities.Member;
import VRMS.entities.RentalTransaction;
import VRMS.entities.Vehicle;
import VRMS.exceptions.*;
import VRMS.service.MemberInterface;
import VRMS.service.VehicleInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class VehicleRentalSystem implements VehicleInterface, MemberInterface
{
    private List<Vehicle> vehicles;
    private List<Member> members;
    private List<RentalTransaction> rentalTransactions;

    public VehicleRentalSystem() {
        this.vehicles = new ArrayList<>();
        this.members = new ArrayList<>();
        this.rentalTransactions = new ArrayList<>();
    }

    // CRUD operations for vehicles
    @Override
    public void addVehicle(Vehicle vehicle) throws DuplicateVehicleException
    {
        if(vehicles.stream()
                .anyMatch(v -> v.getVehicleNumber()
                        .equals(vehicle.getVehicleNumber())))
        {
            throw new DuplicateVehicleException("Vehicle already exists");
        }
        vehicles.add(vehicle);
    }

    @Override
    public void removeVehicle(Vehicle vehicle) throws VehicleNotFoundException
    {
        if (!vehicles.contains(vehicle))
        {
            throw new VehicleNotFoundException("Vehicle not found");
        }
        vehicles.remove(vehicle);
    }

    @Override
    public List<Vehicle> getVehicles()
    {
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
    public void addMember(Member member) throws DuplicateMemberException
    {
        if (members.stream().anyMatch(m -> m.getMemberId()
                .equals(member.getMemberId())))
        {
            throw new DuplicateMemberException("Member already exists");
        }
        members.add(member);
    }

    @Override
    public void removeMember(String memberId) throws MemberNotFoundException
    {
        boolean removed = members.removeIf(member -> member.getMemberId().equals(memberId));
        if (removed) {
            System.out.println("Member with ID " + memberId + " has been removed successfully.");
        } else {
            throw new MemberNotFoundException("Member with ID " + memberId + " not found");
        }
    }


    @Override
    public List<Member> getMembers()
    {
        try {
            boolean memberFound = false;
            for (Member m : members) {
                System.out.println(m);
                memberFound = true;
            }
            if (!memberFound) {
                throw new MemberNotFoundException("No members found in the system.");
            }
        }
        catch (MemberNotFoundException e)
        {
            System.out.println("Exception: " + e.getMessage());
        }
        return members;
    }

    @Override
    public List<Member> filterMembers(String memberType) throws MemberNotFoundException
    {
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
        System.out.println("Available vehicles for " + (isSpecialMember ? "special" : "regular") + " member:");

        vehicles.stream()
                .filter(Vehicle::isAvailable)
                .forEach(v -> {
                    double price = v.getRentalPrice();
                    if (isSpecialMember) {
                        price *= 0.9;
                    }
                    System.out.printf("%s - Type: %s, Price: $%.2f per day%n",
                            v.getVehicleNumber(), v.getVehicleType(), price);
                });
    }

    public void rentVehicle(String memberId, String vehicleNumber, int rentalDuration) throws VehicleNotAvailableException, MemberNotFoundException, VehicleNotFoundException {
        Member member = members.stream()
                .filter(m -> m.getMemberId().equals(memberId))
                .findFirst()
                .orElseThrow(() -> new MemberNotFoundException("Member not found"));

        displayAvailableVehicles(memberId);

        Vehicle vehicle = vehicles.stream()
                .filter(v -> v.getVehicleNumber().equals(vehicleNumber) && v.isAvailable())
                .findFirst()
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found or not available"));

        double rentalPrice = vehicle.getRentalPrice();
        if (member.isSpecialMember()) {
            rentalPrice *= 0.9;
        }

        RentalTransaction transaction = new RentalTransaction(vehicle, member, rentalPrice, rentalDuration);
        rentalTransactions.add(transaction);
        vehicle.setAvailable(false);

        System.out.printf("Vehicle %s rented to member %s for %d days at Rs.%.2f per day%n",
                vehicleNumber, memberId, rentalDuration, rentalPrice);
    }

    public String generateTransactionID() {
        int lastTransactionID = 0;
        lastTransactionID++;
        return "TRANS" + String.format("%04d", lastTransactionID);
    }

    public void viewRentalTransactions()
    {
        if (rentalTransactions.isEmpty()) {
            throw new IllegalStateException("No transactions available. The transaction list is empty.");
        }
            for (RentalTransaction transaction : rentalTransactions) {
                System.out.println("Transaction ID: " + generateTransactionID());
                System.out.println("Vehicle: " + transaction.getVehicle().getVehicleNumber());
                System.out.println("Member: " + transaction.getMember().getMemberId());
                System.out.println("Rental Price: " + transaction.getRentalPrice());
                System.out.println("Rental Duration: " + transaction.getRentalDuration());
                System.out.println();
            }
        }

    public void saveRentalTransactions() {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("rental_transactions.dat"))) {
                oos.writeObject(rentalTransactions);
                System.out.println("Rental transactions saved successfully");
            } catch (IOException e) {
                System.out.println("Error saving rental transactions: " + e.getMessage());
            }
        }

        public void loadRentalTransactions() {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("rental_transactions.dat"))) {
                rentalTransactions = (List<RentalTransaction>) ois.readObject();
                System.out.println("Rental transactions loaded successfully");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading rental transactions: " + e.getMessage());
            }
        }
}
