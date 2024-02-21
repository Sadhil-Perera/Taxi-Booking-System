
package javaapplication65;
import java.util.*;
public class JavaApplication65 {

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




    public static void main(String[] args) {
        graph = new HashMap<>();
        graph.put("A", Arrays.asList(new Edge("B", 5), new Edge("C", 2)));
        graph.put("B", Arrays.asList(new Edge("D", 1)));
        graph.put("C", Arrays.asList(new Edge("B", 3), new Edge("D", 7)));
        graph.put("D", Arrays.asList(new Edge("E", 7)));
        graph.put("E", Arrays.asList(new Edge("C", 12)));

        List<String> path = shortestPath("A", "E");
        System.out.println(path); 

    }
    
}
