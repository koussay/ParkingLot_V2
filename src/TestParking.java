import exceptions.InvalidVehicleNumberException;
import exceptions.ParkingFullException;
import model.Ticket;
import model.Vehicle;
import model.VehicleSize;
import service.ParkingLot;
import strategy.FourWeelerWeekendChargeStrategy;
import strategy.TwoWheelerWeekdayChargeStrategy;
import strategy.TwoWheelerWeekendChargeStrategy;

public class TestParking {
    public static void main(String[] args) throws ParkingFullException, InvalidVehicleNumberException {
        ParkingLot parkingLot= ParkingLot.getParkingLot();
        parkingLot.initializeParkingSlots(10,10);

        Vehicle vehicle= new Vehicle("Mh12", VehicleSize.TWOWHEELER);
        Ticket ticket= parkingLot.park(vehicle);
        System.out.println(ticket);

        Vehicle vehicle2= new Vehicle("Mh13", VehicleSize.TWOWHEELER);
        Ticket ticket2= parkingLot.park(vehicle2);
        System.out.println(ticket2);

        Vehicle vehicle3= new Vehicle("Mh14", VehicleSize.FOURWHEELER);
        Ticket ticket3= parkingLot.park(vehicle3);
        System.out.println(ticket3);

        int cost1= parkingLot.unPack(ticket, new TwoWheelerWeekdayChargeStrategy());
        System.out.println(cost1);
        int cost2= parkingLot.unPack(ticket2, new TwoWheelerWeekendChargeStrategy());
        System.out.println(cost2);
        int cost3= parkingLot.unPack(ticket3, new FourWeelerWeekendChargeStrategy());
        System.out.println(cost3);
    }
}
