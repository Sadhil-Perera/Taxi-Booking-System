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