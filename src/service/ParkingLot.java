package service;

import exceptions.InvalidVehicleNumberException;
import exceptions.ParkingFullException;
import model.Slot;
import model.Ticket;
import model.Vehicle;
import model.VehicleSize;
import strategy.ParkingChargeStrategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParkingLot implements Parking {
    private static ParkingLot parkingLot;
    private final List<Slot> twoWheelerSlots;
    private final List<Slot> fourWheelerSlots;

    private ParkingLot(){
        this.twoWheelerSlots= new ArrayList<>();
        this.fourWheelerSlots= new ArrayList<>();
    }
    public static ParkingLot getParkingLot(){
        if (parkingLot==null)
            parkingLot= new ParkingLot();
        return parkingLot;
    }

    public boolean initializeParkingSlots(int nbreTwoWeeler, int nbreFourWeeler){
        for (int i=1; i<= nbreTwoWeeler;i++){
            twoWheelerSlots.add(new Slot(i));
        }
        System.out.printf("Created 2 wheeler parking lot with %s slots %n", nbreTwoWeeler);
        for (int i=1; i<= nbreFourWeeler;i++){
            fourWheelerSlots.add(new Slot(i));
        }
        System.out.printf("Created 4 wheeler parking lot with %s slots %n", nbreTwoWeeler);
    return true;
    }
    @Override
    public Ticket park(Vehicle vehicle) throws ParkingFullException{

        Slot nextAvailableSlot;
        VehicleSize vehicleSize= vehicle.getVehicleSize();
        if (vehicleSize.equals(VehicleSize.FOURWHEELER)){
            nextAvailableSlot = getNextAvailableFourWheelerSlot();
        }else {
            nextAvailableSlot= getNextAvailableTwoWheelerSlot();
        }
        nextAvailableSlot.occupySlot(vehicle);
        System.out.printf("Allocated slot nbre: %d \n", nextAvailableSlot.getSlotNumber());
        Ticket ticket= new Ticket(nextAvailableSlot.getSlotNumber(), vehicle.getVehicleNumber(), new Date(),vehicle.getVehicleSize());
        return ticket;
    }

    private Slot getNextAvailableFourWheelerSlot() throws ParkingFullException{
        for (Slot slot: fourWheelerSlots){
            if (slot.isEmpty()){
                return slot;
            }
        }
        throw new ParkingFullException("No Empty slot available!");
    }
    private Slot getNextAvailableTwoWheelerSlot() throws ParkingFullException{
        for (Slot slot: twoWheelerSlots){
            if (slot.isEmpty()){
                return slot;
            }
        }
        throw new ParkingFullException("No Empty slot available!");
    }

    @Override
    public int unPack(Ticket ticket, ParkingChargeStrategy parkingCostStrategy) throws InvalidVehicleNumberException {
        int costByHours=0;
        Slot slot;
        try {
            if (ticket.getVehicleSize().equals(VehicleSize.FOURWHEELER)){
                slot= getFourWheelerSlotByVehicleNumber(ticket.getVehicleNumber());
            }else{
                slot= getTwoWheelerSlotByVehicleNumber(ticket.getVehicleNumber());
            }
            slot.vacateSlot();
            int hours= getHoursParked(ticket.getDate(), new Date());
            costByHours= getCostByHours(hours, parkingCostStrategy);
            System.out.println("Vehicle "+ticket.getVehicleNumber()+" at "+ ticket.getSlotNumber()+" was parked for "+ hours+ " hours and the total cost is"+ costByHours);
        }catch (InvalidVehicleNumberException invalidVehicleNumber){
            System.out.println(invalidVehicleNumber.getMessage());
            throw invalidVehicleNumber;

        }
        return costByHours;
    }

    private Slot getTwoWheelerSlotByVehicleNumber(String vehicleNumber) throws InvalidVehicleNumberException {


        for (Slot slot: twoWheelerSlots){
            Vehicle vehicle= slot.getVehicleParked();
            if (vehicle != null && vehicle.getVehicleNumber().equals(vehicleNumber)){
                return slot;
            }
        }
        throw new InvalidVehicleNumberException("No Vehicle parked with this number!");

    }

    private Slot getFourWheelerSlotByVehicleNumber(String vehicleNumber) throws InvalidVehicleNumberException {

        for (Slot slot: fourWheelerSlots){
            Vehicle vehicle= slot.getVehicleParked();
            if (vehicle != null && vehicle.getVehicleNumber().equals(vehicleNumber)){
                return slot;
            }
        }
        throw new InvalidVehicleNumberException("No Vehicle parked with this number!");
    }

    private int getHoursParked(Date startDate, Date endDate){

        long secs= (endDate.getTime()- startDate.getTime())/1000;
        int hours= (int) (secs/3600);

        return hours;
    }
    private int getCostByHours(int hours, ParkingChargeStrategy parkingChargeStrategy){

        return parkingChargeStrategy.getCharge(hours);
    }
}
