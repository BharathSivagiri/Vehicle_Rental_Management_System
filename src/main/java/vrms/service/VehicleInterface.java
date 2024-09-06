package vrms.service;

import vrms.entities.Vehicle;
import vrms.exceptions.DuplicateVehicleException;
import vrms.exceptions.VehicleNotFoundException;

import java.util.List;

public interface VehicleInterface
{
    void addVehicle(Vehicle vehicle) throws DuplicateVehicleException;

    void removeVehicle(String vehicleNumber) throws VehicleNotFoundException;

    void updateVehicle(Vehicle vehicle) throws VehicleNotFoundException;

    List<Vehicle> getVehicles();

    List<Vehicle> filterVehiclesByType(String type);

}
