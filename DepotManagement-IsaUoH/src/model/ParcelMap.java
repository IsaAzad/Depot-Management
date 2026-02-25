package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//
public class ParcelMap {

    private Map<String, Parcel> parcels;

    public ParcelMap(){
        parcels = new HashMap<>();
    }

    public void addParcel(Parcel parcel) {
        parcels.put(parcel.getParcelId(), parcel);
    }

    public Parcel getParcel(String parcelID){
        return parcels.get(parcelID);
    }

    public void removeParcel(String parcelID){
        parcels.remove(parcelID);
    }

    public Set<String> getAllParcelIDs(){
        return parcels.keySet();
    }

    public int getNumberofParcels(){
        return parcels.size();
    }
}




