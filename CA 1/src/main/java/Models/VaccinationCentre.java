package Models;

import Utils.CoolLinkedList;

public class VaccinationCentre {
    private String name, address, eircode;
    private int parkingSpaces;
    private CoolLinkedList<VaccinationBooth> booths;

    public VaccinationCentre(String name, String address, String eircode, int parkingSpaces) {
        this.name = name;
        this.address = address;
        this.eircode = eircode;
        this.parkingSpaces = parkingSpaces;
        this.booths = new CoolLinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public boolean validID(String id){
        for(VaccinationBooth booth:booths){
            if(booth.getBoothID().equals(id))
                return true;
        }
        return false;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getEircode() {
        return eircode;
    }

    public void setEircode(String eircode) {
        this.eircode = eircode;
    }

    public int getParkingSpaces() {
        return parkingSpaces;
    }

    public CoolLinkedList<VaccinationBooth> getBooths() {
        return booths;
    }


    public void setBooths(CoolLinkedList<VaccinationBooth> booths) {
        this.booths = booths;
    }

    public void clearBooths(){
        booths.clear();
    }

    public void setParkingSpaces(int parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    public void addBooth(String boothID, String info, int level){
        booths.add(new VaccinationBooth(boothID,info,level));
    }

    public void deleteBooth(int index){
            booths.remove(index);
    }
    public int boothAmount(){
        return booths.size();
    }
    public int appointmentAmount(){
        int total=0;
        for(VaccinationBooth booth:booths)
            total=+booth.appointmentAmount();
        return total;
    }
    public VaccinationBooth getBooth(int index){
        return booths.get(index);
    }
    public VaccinationBooth getBooth(String id){
        for(VaccinationBooth booth: booths){
            if(booth.getBoothID().equals(id))
                return booth;
        }
        return null;
    }
    public void editBooth(int oldIndex, VaccinationBooth newBooth){
        newBooth.setAppointments(booths.get(oldIndex).getAppointments());
        booths.set(oldIndex,newBooth);
    }

    public String toString(){
        return name + "     " +
  //              address + "     " +
                eircode + "     " +
                parkingSpaces;
    }
}
