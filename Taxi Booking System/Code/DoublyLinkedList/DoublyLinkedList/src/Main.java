
class Taxi { // Taxi Object
     int taxiID;
     String driverName;
     String licensePlateNo;
     boolean availability;
     int capacity;
     double price;

     Taxi next;
     Taxi prev;


    public Taxi(int id, String name, String plateNo, int cap, double price, boolean ava) {
        this.taxiID = id;
        this.driverName = name;
        this.licensePlateNo = plateNo;
        this.availability = ava;
        this.capacity = cap;
        this.price = price;
    }


}

class TaxiList {
    Taxi head;
    Taxi tail;

    public void addTaxi(Taxi newTaxi) { // Add a new taxi into the doublyLinkedList
        if(head == null) {
            head = newTaxi;
            tail = newTaxi;
        }
        else {
            tail.next = newTaxi;
            newTaxi.prev = tail;
            tail = newTaxi;
        }
    }


    public Taxi dispatch() { // Method to send taxi
        Taxi curTaxi = head;

        while(curTaxi != null) {
            if(curTaxi.availability) {
                System.out.println("Dispatching Taxi ID: " + curTaxi.taxiID +
                        ", Driver's Name: " + curTaxi.driverName +
                        ", License Plate: " + curTaxi.licensePlateNo +
                        ", Car capacity: " + curTaxi.capacity +
                        ", Availability: " + curTaxi.availability +
                        ", Price (1 KM): " + curTaxi.price);

                curTaxi.availability = false;
                return curTaxi;
            }
            curTaxi = curTaxi.next;
        }
        System.out.println("Taxis are not available at the moment.");
        return null;
    }

    public void callBack(Taxi dispatchedTaxi) { // Method to retrieve taxi
        if(dispatchedTaxi != null) {
            dispatchedTaxi.availability = true;
            System.out.println("Taxi ID: "+dispatchedTaxi.taxiID+" is available again");
        }
        else {
            System.out.println("Needs to call back");
        }
    }
}






public class Main {
    public static void main(String[] args) {
        Taxi taxi1 = new Taxi(101,"Siripala","KY-1711",4,150.00,true); // Create new Taxi object
        Taxi taxi2 = new Taxi(202,"Kapuge","ABL-0101",3,75.00,true);

        TaxiList taxiList = new TaxiList();

        taxiList.addTaxi(taxi1); // Add new taxi to the list
        taxiList.addTaxi(taxi2);

        taxiList.dispatch(); // Send taxi1 to the location
        taxiList.dispatch();

        taxiList.callBack(taxi1); // Call back taxi1 to taxi stand.


    }
}