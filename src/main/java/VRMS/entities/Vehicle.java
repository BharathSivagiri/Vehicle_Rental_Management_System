package VRMS.entities;

import java.io.Serializable;

public class Vehicle implements Serializable {
    private String vehicleNumber;
    private String vehicleType;
    private String vehicleModel;
    private double rentalPrice;
    private boolean isAvailable;

    public Vehicle(String vehicleNumber, String vehicleType, String vehicleModel, double rentalPrice) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.vehicleModel = vehicleModel;
        this.rentalPrice = rentalPrice;
        this.isAvailable = true;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Vehicle[" +
                "vehicleNumber='" + vehicleNumber + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", rentalPrice=" + rentalPrice +
                ", isAvailable=" + isAvailable +
                ']';
    }
}