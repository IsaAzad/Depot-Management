package model;

public class Parcel {
    //Attributes
    private String parcelId;
    private int daysInDepot;
    private double weight;
    private String dimensions;
    private String status;

    //Constructors
    public Parcel(String parcelId, int daysInDepot, double weight, String dimensions){
        this.parcelId = parcelId;
        this.daysInDepot = daysInDepot; // initialise days in depot to 0
        this.weight = weight;
        this.dimensions = dimensions;
        this.status = status; // initialises status to unknown, to be updated
    }

    public String getParcelId(){
        return parcelId;
    }

    public void setParcelId(String parcelId){
        this.parcelId = parcelId;
    }

    public int getDaysInDepot(){
        return daysInDepot;
    }

    public void setDaysInDepot(int daysInDepot){
        this.daysInDepot = daysInDepot;
    }

    public double getWeight(){
        return weight;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Override toString() method to display parcel details
    @Override
    public String toString() {
        return "Parcel ID: " + parcelId + "\n" + "Days In Depot: " + daysInDepot + "\n" + "Weight: " + weight + "\n" + "Dimensions: " + dimensions + "\n" + "Status: " + status;
    }
}
