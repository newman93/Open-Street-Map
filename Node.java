package projekt1;

public class Node 
{
    private long id;
    private double lat;
    private double lon;
    
    public Node(long id, double lat, double lon) 
    {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }
    
    public long getId() 
    {
        return id;
    }
    
    public void setId(long id) 
    {
        this.id = id;
    }
    
    public double getLat() 
    {
        return lat;
    }
    
    public void setLat(double lat)
    {
        this.lat = lat;
    }
    
    public double getLon()
    {
        return lon;
    }
    
    public void setLon(double lon) 
    {
        this.lon = lon;
    }
     
    @Override
    public String toString()
    {
        return "Node:: ID = " + this.id + " Latitude = " + this.lat + " Longitutde = " + this.lon;
    }
}