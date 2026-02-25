package controller;
import model.Customer;
import model.Parcel;
import model.ParcelMap;
import model.QueueofCustomers;
import utils.Log;

import java.text.DecimalFormat;

public class Worker {

    private DecimalFormat df = new DecimalFormat("#.##"); // for formatting fees

    // The method to calculate the fee for a parcel
    public double calculateFee(Parcel customerParcel) {
        double weight = customerParcel.getWeight();
        int daysInDepot = customerParcel.getDaysInDepot();
        double fee = 0;
        double fee1 = 0;
        double totalfee = 0;

        if (weight > 3.0 & daysInDepot > 1) {
            fee += weight * 0.80; // fee per kg
            //Additional fee based on days in the depot
            fee1 += daysInDepot * 0.50; // 0.50 a day
            totalfee = fee + fee1;

        }else if(weight < 3.0 & daysInDepot > 1) {
            fee1 += daysInDepot * 0.50;
            totalfee = totalfee + fee1;
        }

        return Double.parseDouble(df.format(totalfee));
    }

    //process a customer
    public void processCustomer(QueueofCustomers customerQueue, ParcelMap parcelMap, Log logger) {
        Customer currentCustomer = customerQueue.dequeueCustomer();
        if (currentCustomer != null) {
            Parcel customerParcel = parcelMap.getParcel(currentCustomer.getParcelId());
            if (customerParcel != null) {
                double fee = calculateFee(customerParcel);
                logger.logEvent("Customer " + currentCustomer.getSequenceNumber() +
                        " processed. Parcel ID: " + customerParcel.getParcelId() +
                        ", Fee: " + fee);
            } else {
                // Handle the case where the parcel is not found
                logger.logEvent("Error: Parcel not found for customer " + currentCustomer.getSequenceNumber());
            }
        } else {
            // Handle the case where the queue is empty
            logger.logEvent("Queue is empty. No customer to process.");
        }

    }
}
