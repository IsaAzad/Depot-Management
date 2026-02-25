package model;

public class Customer {
    //Attributes section
    private String SequenceNumber;
    private String name;
    private String parcelId;

    //Constructor
    public Customer(String SequenceNumber, String name, String parcelId){
        this.SequenceNumber = SequenceNumber;
        this.name = name;
        this.parcelId = parcelId;
    }

    public String getSequenceNumber(){
        return SequenceNumber;
    }

    public void setSequenceNumber(String SequenceNumber){
        this.SequenceNumber = SequenceNumber;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getParcelId(){
        return parcelId;
    }

    public void setParcelId(String parcelId){
        this.parcelId = parcelId;
    }



    @Override
    public String toString() {
        return "SequenceNumber: " + SequenceNumber + ", Name: " + name +
                ", Parcel ID: " + parcelId;
    }
}

