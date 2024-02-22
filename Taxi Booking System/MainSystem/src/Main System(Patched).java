
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


    public Taxi(int id, String name, String plateNo, int cap,double price ,boolean ava) {
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

    Graph graph;

    public TaxiList(Graph graph) {
        this.graph = graph;
    }

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
                        ", Price per (1km)" + curTaxi.price+
                        ", Availability: " + curTaxi.availability);

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

    public void dispatchToCustomer(Vertex pickupLocation, Vertex dropoffLocation) {
        List<Vertex> pathToCustomer = graph.shortestPath('A', pickupLocation.data);

        if (!pathToCustomer.isEmpty()) {
            Taxi dispatchedTaxi = dispatch();
            System.out.println();

            if (dispatchedTaxi != null) {
                System.out.println("Dispatching Taxi ID: " + dispatchedTaxi.taxiID +
                        " to pick up customer at Location " + pickupLocation.data);

                double totalDistance = 0.0;

                for (int i = 0; i < pathToCustomer.size() - 1; i++) {
                    Vertex currentLocation = pathToCustomer.get(i);
                    Vertex nextLocation = pathToCustomer.get(i + 1);
                    double distance = graph.getEdgeWeight(currentLocation, nextLocation);
                    totalDistance += distance;
                    System.out.println("Moving to location: " + nextLocation.data + " (Distance: " + distance + ")");
                    delay(1000);
                }

                System.out.println("Taxi reached the customer at location " + pickupLocation.data);
                delay(1000);

                List<Vertex> pathToDropoff = graph.shortestPath(pickupLocation.data, dropoffLocation.data);
                for (int i = 0; i < pathToDropoff.size() - 1; i++) {
                    Vertex currentLocation = pathToDropoff.get(i);
                    Vertex nextLocation = pathToDropoff.get(i + 1);
                    double distance = graph.getEdgeWeight(currentLocation, nextLocation);
                    totalDistance += distance;
                    System.out.println("Moving to location " + nextLocation.data + " (Distance: " + distance + ")");
                    delay(1000);
                }

                System.out.println("Customer dropped off at location " + dropoffLocation.data);
                delay(1000);
                System.out.println("Total travel distance: " + totalDistance + " KM");
                delay(1000);
                double totalRidePrice = totalDistance * dispatchedTaxi.price;
                System.out.println("Total Cost: "+totalRidePrice);
                delay(1000);
                System.out.println("------------------------------------------------------------------------------------");

//                callBack(dispatchedTaxi);
            }
        } else {
            System.out.println("No available taxis or invalid locations.");
        }
    }
    private void delay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Vertex {
    char data;

    public Vertex(char ch) {
        this.data = ch;
    }
}
class Graph {
    ArrayList<Vertex> vertices;
    int[][] matrix;

    public Graph(int size) {
        vertices = new ArrayList<>();
        matrix = new int[size][size];
    }

    public void addVertex(Vertex v) {
        vertices.add(v);
    }

    public void addEdge(int src, int dst, int weight) {
        matrix[src][dst] = weight;
        matrix[dst][src] = weight;
    }

//    public void print() {
//        System.out.print("   ");
//        for (Vertex v : vertices) {
//            System.out.print(v.data + "  ");
//        }
//        System.out.println();

//        for(int i=0;i<matrix.length;i++) {
//            System.out.print(vertices.get(i).data + "  ");
//            for(int j=0;j<matrix[i].length;j++) {
//                System.out.print(matrix[i][j] + "  ");
//            }
//            System.out.println();
//        }
//    }

    public List<Vertex> shortestPath(char start, char end) {
        Map<Vertex, Double> distance = new HashMap<>();
        Map<Vertex, Vertex> previous = new HashMap<>();
        Set<Vertex> visited = new HashSet<>();
        PriorityQueue<Vertex> queue = new PriorityQueue<>(Comparator.comparingDouble(distance::get));

        Vertex startVertex = getVertex(start);
        Vertex endVertex = getVertex(end);

        distance.put(startVertex, 0.0);
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            for (Vertex neighbor : getNeighbors(current)) {
                double newDistance = distance.get(current) + getEdgeWeight(current, neighbor);
                if (newDistance < distance.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    distance.put(neighbor, newDistance);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        List<Vertex> path = new ArrayList<>();
        for (Vertex at = endVertex; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);

        return path;
    }

    private Vertex getVertex(char ch) {
        for (Vertex v : vertices) {
            if (v.data == ch) {
                return v;
            }
        }
        return null;
    }

    private List<Vertex> getNeighbors(Vertex vertex) {
        List<Vertex> neighbors = new ArrayList<>();
        int index = vertices.indexOf(vertex);
        for (int i = 0; i < matrix[index].length; i++) {
            if (matrix[index][i] != 0) {
                neighbors.add(vertices.get(i));
            }
        }
        return neighbors;
    }

    public double getEdgeWeight(Vertex src, Vertex dst) {
        int srcIndex = vertices.indexOf(src);
        int dstIndex = vertices.indexOf(dst);
        return matrix[srcIndex][dstIndex];
    }
}



public class Main {
    public static void main(String[] args) {
        Taxi taxi1 = new Taxi(101, "Siripala", "KY-1711", 4,150.0,true);
        Taxi taxi2 = new Taxi(202, "Kapuge", "ABL-0101", 3,100,true);

        Graph graph = new Graph(5);
        TaxiList taxiList = new TaxiList(graph);

        taxiList.addTaxi(taxi1);
        taxiList.addTaxi(taxi2);

        graph.addVertex(new Vertex('A'));
        graph.addVertex(new Vertex('B'));
        graph.addVertex(new Vertex('C'));
        graph.addVertex(new Vertex('D'));
        graph.addVertex(new Vertex('E'));

        graph.addEdge(0, 1, 5);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 2, 3);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 3, 7);
        graph.addEdge(2, 4, 6);
        graph.addEdge(3, 4, 7);

        Vertex pickupLocation = new Vertex('C');
        Vertex dropoffLocation = new Vertex('E');

        taxiList.dispatchToCustomer(pickupLocation, dropoffLocation);
        taxiList.dispatchToCustomer(new Vertex('B'), new Vertex('E'));

        // graph.print();

    }
}