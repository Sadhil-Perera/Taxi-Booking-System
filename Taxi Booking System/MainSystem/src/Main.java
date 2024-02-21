import java.util.*;
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

    public Taxi dispatchToCustomer(String prickupLocation, String dropoffLocation) {
        List<String> path = Graph.shortestPath(prickupLocation, dropoffLocation);

        if(!path.isEmpty()) {
            Taxi dispatchedTaxi = dispatch();

            if(dispatchedTaxi != null) {
                System.out.println("Dispatching Taxi to pick up Customer at Location "+prickupLocation);

                for(String location : path) {
                    System.out.println("Moving to location: "+location);
                }
                System.out.println("Taxi reached the customer at lacation "+prickupLocation);

                path = Graph.shortestPath(prickupLocation, dropoffLocation);
                for(String location : path) {
                    System.out.println("Moving to location "+location);
                }
                System.out.println("Customer dropped off at location "+dropoffLocation);

                return dispatchedTaxi;
            }
        }
        System.out.println("No available taxis");
        return null;
    }
}

class Graph {

    private static class Edge {
        String to;
        double weight;

        public Edge(String to, double weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    private static class Vertex implements Comparable<Vertex> {
        String name;
        double distance;

        public Vertex(String name, double distance) {
            this.name = name;
            this.distance = distance;
        }

        @Override
        public int compareTo(Vertex other) {
            return Double.compare(this.distance, other.distance);
        }
    }

    private static Map<String, List<Edge>> graph;
    private static Map<String, Double> dist;
    private static Map<String, String> parent;

    public static List<String> shortestPath(String source, String target) {
        dist = new HashMap<>();
        parent = new HashMap<>();
        for (String location : graph.keySet()) {
            dist.put(location, Double.POSITIVE_INFINITY);
            parent.put(location, null);
        }

        dist.put(source, 0.0);

        PriorityQueue<Vertex> pq = new PriorityQueue<>();
        pq.offer(new Vertex(source, 0));

        while (!pq.isEmpty()) {
            Vertex u = pq.poll();

            for (Edge edge : graph.get(u.name)) {
                double newDist = dist.get(u.name) + edge.weight;
                if (newDist < dist.get(edge.to)) {
                    dist.put(edge.to, newDist);
                    parent.put(edge.to, u.name);
                    pq.offer(new Vertex(edge.to, newDist));
                }
            }
        }


        List<String> path = new ArrayList<>();
        for (String v = target; v != null; v = parent.get(v)) {
            path.add(v);
        }
        Collections.reverse(path);

        return path;
    }


    public class Main {
        public static void main(String[] args) {
            Taxi taxi1 = new Taxi(101, "Siripala", "KY-1711", 4, 150.00, true); // Create new Taxi object
            Taxi taxi2 = new Taxi(202, "Kapuge", "ABL-0101", 3, 75.00, true);

            TaxiList taxiList = new TaxiList();

            taxiList.addTaxi(taxi1); // Add new taxi to the list
            taxiList.addTaxi(taxi2);


            graph = new HashMap<>();
            graph.put("A", Arrays.asList(new Graph.Edge("B", 5), new Graph.Edge("C", 2)));
            graph.put("B", Arrays.asList(new Graph.Edge("D", 1)));
            graph.put("C", Arrays.asList(new Graph.Edge("B", 3), new Graph.Edge("D", 7)));
            graph.put("D", Arrays.asList(new Graph.Edge("E", 7)));
            graph.put("E", Arrays.asList(new Graph.Edge("C", 12)));

            // List<String> path = shortestPath("A", "E");
            // System.out.println(path);

            Taxi dispatchedTaxi = taxiList.dispatchToCustomer("B", "E");
            Taxi dispatchedTaxi2 = taxiList.dispatchToCustomer("C", "D");



            if (dispatchedTaxi != null) {
                taxiList.callBack(dispatchedTaxi);
            }
        }
    }
}