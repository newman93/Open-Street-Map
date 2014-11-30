package projekt1;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.NavigableSet;

class Graph 
{
    static double distance(double lat1, double lon1, double lat2, double lon2)
    {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
  
        return dist;
    }

    private static double deg2rad(double deg) 
    {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) 
    {
        return (rad * 180 / Math.PI);
    }
    
    private Map<Long, Vertex> graph; // mapping of vertex names to Vertex objects, built from a set of Edges
    
    public Map<Long, Vertex> getGraph()
    {
        return graph;
    }
 
    /** One edge of the graph (only used by Graph constructor) */
    public static class Edge 
    {
        public final long v1, v2;
        public final double dist;
        public Edge(long v1, long v2, double dist) 
        {
            this.v1 = v1;
            this.v2 = v2;
            this.dist = dist;
        }
    }
 
    /** One vertex of the graph, complete with mappings to neighbouring vertices */
    public static class Vertex implements Comparable<Vertex> 
    {
        public final long name;
        public double dist = Double.MAX_VALUE; // MAX_VALUE assumed to be infinity
        public Vertex previous = null;
        public final Map<Vertex, Double> neighbours = new HashMap<>();
       
        public Vertex(long name) 
        {
            this.name = name;
        }
 
        private void printPath() 
        {
            if (this == this.previous) 
            {
                System.out.printf("%d", this.name);
            } else if (this.previous == null) 
            {
                System.out.printf("%d(unreached)", this.name);
            } else 
            {
                this.previous.printPath();
                System.out.printf(" -> %d(%f)", this.name, this.dist);
            }
        }
 
        @Override
        public int compareTo(Vertex other) 
        {
            return Double.compare(dist, other.dist);
        }
    }
 
    /** Builds a graph from a set of edges */
    public Graph(Edge[] edges) 
    {
        graph = new HashMap<>(edges.length);
 
        //one pass to find all vertices
        for (Edge e : edges) 
        {
            if (!graph.containsKey(e.v1)) graph.put(e.v1, new Vertex(e.v1));
            if (!graph.containsKey(e.v2)) graph.put(e.v2, new Vertex(e.v2));
        }
 
        //another pass to set neighbouring vertices
        for (Edge e : edges) 
        {
            graph.get(e.v1).neighbours.put(graph.get(e.v2), e.dist);
            graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); 
        }
    }
 
    /** Runs dijkstra using a specified source vertex */ 
    public void dijkstra(long startName) 
    {
        if (!graph.containsKey(startName)) 
        {
            System.err.printf("Graph doesn't contain start vertex \"%d\"\n", startName);
            return;
        }
        final Vertex source = graph.get(startName);
        NavigableSet<Vertex> q = new TreeSet<>();
 
        // set-up vertices
        for (Vertex v : graph.values()) 
        {
            v.previous = v == source ? source : null;
            v.dist = v == source ? 0 : Integer.MAX_VALUE;
            q.add(v);
        }
 
        dijkstra(q);
    }
 
    /** Implementation of dijkstra's algorithm using a binary heap. */
    private void dijkstra(final NavigableSet<Vertex> q) 
    {      
        Vertex u, v;
        while (!q.isEmpty()) 
        {
 
            u = q.pollFirst(); // vertex with shortest distance (first iteration will return source)
            if (u.dist == Double.MAX_VALUE) break; // we can ignore u (and any other remaining vertices) since they are unreachable
 
            //look at distances to each neighbour
            for (Map.Entry<Vertex, Double> a : u.neighbours.entrySet()) 
            {
                v = a.getKey(); //the neighbour in this iteration
 
                final double alternateDist = u.dist + a.getValue();
                if (alternateDist < v.dist) // shorter path to neighbour found
                { 
                    q.remove(v);
                    v.dist = alternateDist;
                    v.previous = u;
                    q.add(v);
                } 
            }
        }
    }
 
    /** Prints a path from the source to the specified vertex */
    public void printPath(long endName) 
    {
        if (!graph.containsKey(endName)) 
        {
            System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
            return;
        }
 
        graph.get(endName).printPath();
        System.out.println();
    }
    
    /** Prints the path from the source to every vertex (output order is not guaranteed) */
    public void printAllPaths() 
    {
        for (Vertex v : graph.values()) 
        {
            v.printPath();
            System.out.println();
        }
    }
}