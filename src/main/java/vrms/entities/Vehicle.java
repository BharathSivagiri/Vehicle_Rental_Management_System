package vrms.entities;

import java.io.Serializable;

public class Vehicle implements Serializable {
    private String vehicleNumber;
    private String vehicleType;
    private String vehicleModel;
    private double rentalPrice;
    private VehicleStatus status;

    public Vehicle(String vehicleNumber, String vehicleType, String vehicleModel, double rentalPrice, VehicleStatus status) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.vehicleModel = vehicleModel;
        this.rentalPrice = rentalPrice;
        this.status = status;
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

    public void setAvailable(boolean available) {
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public boolean isAvailable() {
        return status == VehicleStatus.AVAILABLE;
    }

    @Override
    public String toString()
    {
        return "Vehicle Number = '" + vehicleNumber + '\'' +
                ", Type = '" + vehicleType + '\'' +
                ", Model = '" + vehicleModel + '\'' +
                ", Rental price per day = " + rentalPrice +
                ", Available or Not = " + status;
    }
}