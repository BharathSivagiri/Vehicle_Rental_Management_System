package VRMS.service;

import VRMS.entities.Member;
import VRMS.entities.Vehicle;
import VRMS.exceptions.DuplicateMemberException;
import VRMS.exceptions.DuplicateVehicleException;
import VRMS.exceptions.MemberNotFoundException;
import VRMS.exceptions.VehicleNotFoundException;

import java.util.List;

public interface VehicleInterface
{
    void addVehicle(Vehicle vehicle) throws DuplicateVehicleException;

    void removeVehicle(Vehicle vehicle) throws VehicleNotFoundException;

    List<Vehicle> getVehicles();

    List<Vehicle> filterVehiclesByType(String type);

}
