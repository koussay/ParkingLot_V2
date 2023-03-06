package model;

public class Slot {
    private Integer slotNumber;
    private boolean isEmpty;
    private Vehicle vehicleParked;

    public Slot(Integer slotNumber) {
        this.slotNumber = slotNumber;
        isEmpty= true;
    }

    public Integer getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(Integer slotNumber) {
        this.slotNumber = slotNumber;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        this.isEmpty = empty;
    }

    public Vehicle getVehicleParked() {
        return vehicleParked;
    }

    public void setVehicleParked(Vehicle vehicleParked) {
        this.vehicleParked = vehicleParked;
    }
    public void vacateSlot(){
        vehicleParked = null;
        this.isEmpty= true;
    }
    public void occupySlot(Vehicle vehicleParked){
        this.vehicleParked= vehicleParked;
        this.isEmpty= false;

    }
}
