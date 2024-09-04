package VRMS.service;

import VRMS.entities.Vehicle;
import VRMS.exceptions.DuplicateVehicleException;
import VRMS.exceptions.VehicleNotFoundException;

import java.util.List;

public interface VehicleInterface
{
    void addVehicle(Vehicle vehicle) throws DuplicateVehicleException;

    void removeVehicle(Vehicle vehicle) throws VehicleNotFoundException;

    void updateVehicle(Vehicle vehicle) throws VehicleNotFoundException;

    List<Vehicle> getVehicles();

    List<Vehicle> filterVehiclesByType(String type);

}
