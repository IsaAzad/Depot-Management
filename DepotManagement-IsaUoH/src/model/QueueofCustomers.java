package model;

import java.util.LinkedList;
import java.util.Queue;

//
public class QueueofCustomers {

    private Queue<Customer> customerQueue;

    public QueueofCustomers(){
        this.customerQueue = new LinkedList<>();
    }

    public void enqueueCustomer(Customer customer){
        customerQueue.add(customer);
    }

    public Customer dequeueCustomer(){
        return customerQueue.poll();
    }

    public int getQueueSize(){
        return customerQueue.size();
    }

    public boolean isEmpty(){
        return customerQueue.isEmpty();
    }

    public int getCustomerPosition(String SequenceNumber){
        int position = 1;
        for (Customer customer: customerQueue) {
            if (customer.getSequenceNumber() == SequenceNumber) {
                return position;
            }

            position++;
        }
        return -1;// customer not found

    }


    public Customer[] getAllCustomers() {
        return customerQueue.toArray(new Customer[customerQueue.size()]);
    }
}
