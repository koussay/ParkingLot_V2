package service;

import exceptions.InvalidVehicleNumberException;
import exceptions.ParkingFullException;
import model.Ticket;
import model.Vehicle;
import strategy.ParkingChargeStrategy;

public interface Parking {
    public Ticket park(Vehicle vehicle) throws ParkingFullException;
    public int unPack(Ticket ticket, ParkingChargeStrategy parkingCostStrategy) throws InvalidVehicleNumberException;

}
