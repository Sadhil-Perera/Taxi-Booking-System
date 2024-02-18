import javax.swing.plaf.IconUIResource;

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

    public void addTaxi(Taxi newTaxi) {
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

    public void printBackward() {
        Taxi curTaxi = tail;

        while (curTaxi != null) {
            if (curTaxi.availability) {
                System.out.println("Taxi ID: " + curTaxi.taxiID +
                        ", Driver's Name: " + curTaxi.driverName +
                        ", License Plate: " + curTaxi.licensePlateNo +
                        ", Car capacity: " + curTaxi.capacity +
                        ", Availability : " + curTaxi.availability +
                        ", Price (1 KM): " + curTaxi.price);
            }
            curTaxi = curTaxi.prev;
        }
        System.out.println();
    }
}





public class Main {
    public static void main(String[] args) {
        Taxi taxi1 = new Taxi(101,"Thisara","KY-1711",4,150.00,false);
        Taxi taxi2 = new Taxi(202,"Keshan","ABL-0101",3,75.00,true);

        TaxiList taxiList = new TaxiList();

        taxiList.addTaxi(taxi1);
        taxiList.addTaxi(taxi2);

        taxiList.printBackward();

    }
}