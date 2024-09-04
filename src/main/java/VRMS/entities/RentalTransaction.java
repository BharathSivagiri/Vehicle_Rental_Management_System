package VRMS.entities;

import java.io.Serializable;

public class RentalTransaction implements Serializable {
    private Vehicle vehicle;
    private Member member;
    private double rentalPrice;
    private int rentalDuration;

    public RentalTransaction(Vehicle vehicle, Member member, double rentalPrice, int rentalDuration) {
        this.vehicle = vehicle;
        this.member = member;
        this.rentalPrice = rentalPrice;
        this.rentalDuration = rentalDuration;
    }

    // Getters and Setters
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public int getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(int rentalDuration) {
        this.rentalDuration = rentalDuration;
    }
}