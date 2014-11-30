package projekt1;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

public class WeightedGraph implements Serializable 
{
    private static final long serialVersionUID = 1L;
    List<Node> adjacencyList;
    
    class Node implements Serializable
    {
        long id;
        double lat;
        double lon;
        transient Neighbours listNeighbours;
        
        Node(long id, double lat, double lon, Neighbours listNeighbours)
        {
            this.id = id;
            this.lat = lat;
            this.lon = lon;
            this.listNeighbours = listNeighbours;
        }
        
        @Override
        public String toString() 
        {
            String text = super.toString();
            return text + "(" + id + ", " + lat + ", " + lon  + listNeighbours + ")";
        }
    }
    
    class Neighbour implements Serializable
    {
        long id;
        double lat;
        double lon;
        
        Neighbour(long id, double lat, double lon)
        {
            this.id = id;
            this.lat = lat;
            this.lon = lon;
        }
    }
    
    class Neighbours implements Serializable
    {
        HashMap<Double, Neighbour> neighbours;
        
        Neighbours(HashMap<Double, Neighbour> neighbours)
        {
            this.neighbours = neighbours;
        }
        
        @Override
        public String toString() 
        {
            String text = super.toString();
            Iterator<Double> keySetIterator = neighbours.keySet().iterator();
            while(keySetIterator.hasNext())
            {
                Double key = keySetIterator.next();
                
                text  += " -> " + key + " : " + neighbours.get(key);
            }
            return text;
        }
    }
    
    WeightedGraph(Map<Long, Graph.Vertex> graph)
    {
        this.adjacencyList = new ArrayList<>();
        HashMap<Double,Neighbour> refNeighbours = new HashMap<>();
        MyHandler handler = new MyHandler();
        HashMap<Long, projekt1.Node> myMap = handler.getMap();
        Iterator<Long> keySetIterator = graph.keySet().iterator();
        while(keySetIterator.hasNext())
        {
            Long key = keySetIterator.next();
            Iterator<Graph.Vertex> keySetIterator1 = Graph.Vertex.neighbours.keySet().iterator();
            while (keySetIterator1.hasNext())
            {
                Graph.Vertex key1 = keySetIterator1.next();
                refNeighbours.put(Graph.Vertex.neighbours.get(key1), new Neighbour(key1.name ,myMap.get(key1.name).getLat(), myMap.get(key1.name).getLon()));              
            }
            Neighbours oNeighbours = new Neighbours(refNeighbours);
            Node node = new Node(key, myMap.get(key).getLat(), myMap.get(key).getLon(), oNeighbours);
            adjacencyList.add(node);   
        }
    } 
    
    @Override
    public String toString() 
    {
        String text = super.toString();
        return text;
    }
}