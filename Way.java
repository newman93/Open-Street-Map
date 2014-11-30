package projekt1;

import java.util.ArrayList;

public class Way 
{
    private long id;
    private ArrayList<Long> refNode; 
    private String name;
    private int edges;
    
    public Way(long id, String name, ArrayList<Long> refN) 
    {
        this.id = id;
        this.name = name;
        this.refNode = new ArrayList<>(refN);
        this.edges = refN.size() - 1;
    }
    
    public long getId() 
    {
        return id;
    }
    
    public void setId(long id) 
    {
        this.id = id;
    }
    
    public String getName() 
    {
        return name;
    }
    
    public void setName(String name) 
    {
        this.name = name;
    }
    
    public ArrayList<Long> getRef() 
    {
        return refNode;
    }
    
    public int getEdges() 
    {
        return edges;
    }
             
    @Override
    public String toString() 
    {
        
        String tmp = "Way:: ID = " + this.id + " name = " + this.name + " refNode = ";  
        
        for (int i = 0; i < (this.refNode).size(); i++)  
        {  
            tmp = tmp + this.refNode.get(i) + " ";  
        }   
        
        return tmp;
    }
}