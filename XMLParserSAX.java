package projekt1;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Iterator;
 
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
 
import org.xml.sax.SAXException;

public class XMLParserSAX 
{   
    public static int menu()
    {
        System.out.println();
        System.out.println("     ****************************************");
        System.out.println("     *                 MENU                 *");
        System.out.println("     ****************************************");
        System.out.println("     1. Najkrótsza ścieżka");
        System.out.println("     2. Serializacja binarna");
        System.out.println("     3. Serializacja XML");
        System.out.println("     0. Koniec");
 
        Scanner in = new Scanner(System.in);
        int w = in.nextInt();
 
        return w;
    }
    public static void main(String[] args) 
    {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try 
        {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            MyHandler handler = new MyHandler();
            saxParser.parse(new File("C:\\Users\\Adrian\\Documents\\NetBeansProjects\\projekt1\\src\\projekt1\\map.xml"), handler);
            //Get Node map
            HashMap<Long, Node> myMap = handler.getMap();
            //Get Way map
            HashMap<Long, Way> myMap2 = handler.getMap2();
            //HashMap iterators
            HashMap<Long, Integer> myMap3 = new HashMap<>();
            //HashMap Graph
            HashMap<Node, String> myMap4 = new HashMap<>();
            //Iterator
            Integer iterator = 1;
            Iterator<Long> keySetIterator = myMap.keySet().iterator();
            Iterator<Long> keySetIterator2 = myMap2.keySet().iterator();
            Iterator<Long> keySetIterator3 = myMap2.keySet().iterator();
        
            while(keySetIterator.hasNext())
            {
                Long key = keySetIterator.next();
                //add iterators
                //myMap3.put(key, iterator++);
                //System.out.println(key + " : " + myMap.get(key) );
            }
        
            int edges = 0;
        
            while(keySetIterator2.hasNext())
            {
                Long key2 = keySetIterator2.next();
                edges = edges + myMap2.get(key2).getEdges();
            }
         
            Graph.Edge[] GRAPH = new Graph.Edge[edges+1];
            int eIterator = 0;
            while(keySetIterator3.hasNext())
            {
                Long key2 = keySetIterator3.next();
                for (int i = 0; i < myMap2.get(key2).getRef().size()-1; ++i)
                {
                    long v1 = myMap2.get(key2).getRef().get(i);
                    long v2 = myMap2.get(key2).getRef().get(i+1);
                    double lat1 = myMap.get(v1).getLat();
                    double lon1 = myMap.get(v1).getLon();
                    double lat2 = myMap.get(v2).getLat();
                    double lon2 = myMap.get(v2).getLon();
                    GRAPH[eIterator++] = new Graph.Edge(v1, v2, Graph.distance(lat1,lon1,lat2,lon2));
                }
            }
            Graph g = new Graph(GRAPH);
            int wybor = menu();
            
            while(wybor!=0){
            switch(wybor){
                case 1:
                        System.out.println("Wprowadź punkt startowy: ");
                        Scanner input1 = new Scanner(System.in);
                        String start = input1.nextLine();
                        long START = Long.parseLong(start);
                        System.out.println("Wproawdź punkt końcowy: ");
                        Scanner input2 = new Scanner(System.in);
                        String end = input2.nextLine();
                        long END = Long.parseLong(end);
                        System.out.println("Najkrótsza trasa dojazdu: ");
                        g.dijkstra(START);
                        g.printPath(END); 

                        break;
                case 2:
                        WeightedGraph graph = new WeightedGraph(g.getGraph());
                        String serializacjaBinarna = "C:\\Users\\Adrian\\Documents\\NetBeansProjects\\projekt1\\src\\projekt1\\graf.bin";
                        ObjectOutputStream out = new ObjectOutputStream(
                                                    new BufferedOutputStream(
                                                        new FileOutputStream(serializacjaBinarna)));

                        out.writeObject(graph);
                        out.close();
                        System.out.println(graph);
                       
                        break;
 
                case 3:
                        break;
 
            }
 
            System.out.println("\nWciśnij Enter, aby kontynuować...");
            System.in.read();
 
            wybor = menu();
        }
 
 
        System.out.println("     ****************************************");
        System.out.println("\n     Koniec programu\n\n");
            
                   
    }
    catch (ParserConfigurationException | SAXException | IOException e) 
    {
        e.printStackTrace();
    }
    }
}