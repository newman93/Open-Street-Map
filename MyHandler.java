package projekt1;
 
import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler; 

/**
 * 
 * @author Adrian
 */
public class MyHandler extends DefaultHandler 
{ 
    //HashMap to hold Node object
    private HashMap<Long, Node> myMap = null;
    private Node nod = null;
    //HashMap to hold Way object
    private HashMap<Long, Way> myMap2 = null;
    private Way wa = null;
    //ArrayList to hold refNode
    private ArrayList<Long> refNode = new ArrayList<>();
    boolean bName = false;
    boolean bWay = false;
    boolean bTag = false;
    boolean bNd = false;
    long id2;
    //getter method for node map
    public HashMap<Long, Node> getMap() 
    {
        return myMap;
    }
    //getter method for way map
    public HashMap<Long, Way> getMap2()
    {
        return myMap2;
    }
    //getter method for refNode list
    public ArrayList<Long> getList() 
    {
        return refNode;
    }
           
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException 
    { 
        if (qName.equalsIgnoreCase("node")) 
        {
            //create a new Node and put it in Map
            String id = attributes.getValue("id");
            String lat = attributes.getValue("lat");
            String lon = attributes.getValue("lon");
            //initialize Node object and set id attribute
            nod = new Node(Long.parseLong(id), Double.parseDouble(lat), Double.parseDouble(lon)) ;
            //initialize map
            if (myMap == null)
                myMap = new HashMap<>();
        }
        if (qName.equalsIgnoreCase("way")) 
        {
            bWay = true;
            //create a new Way and put it in Map
            id2 = Long.parseLong(attributes.getValue("id"));
        } else if (qName.equalsIgnoreCase("nd"))
        {
            bNd = true;
            String ref = attributes.getValue("ref");
            refNode.add(Long.parseLong(ref));
        } else if (qName.equalsIgnoreCase("tag") && attributes.getValue("k").equals("addr:street"))
        {
            bTag = true;
            String name = attributes.getValue("v");
            bName = true;
            //initialize Way object and set id attributes
            wa = new Way(id2, name, refNode);
            refNode.clear();
        } 
        //initialize map
        if (myMap2 == null)
            myMap2 = new HashMap<>();   
    }
 
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException 
    {
        if (qName.equalsIgnoreCase("node")) 
        {
            //add Node object to map
            myMap.put(nod.getId(), nod);
        } else if (qName.equalsIgnoreCase("way") && bName == true) 
        {
            //add Way object to map
            myMap2.put(wa.getId(), wa);
        }     
    }
 
    @Override
    public void characters(char ch[], int start, int length) throws SAXException 
    {
    }
}