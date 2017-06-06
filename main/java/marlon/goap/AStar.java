package marlon.goap;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;

import marlon.goap.actions.Action;

public class AStar {   
	
	Planner plan = new Planner();  
    
    private Node getLowestCost(HashSet<Node> set) {
    	
    	double lowestCost = 99999;	
    	Node selected = new Node(null,null,null);
    	
    	if(set.isEmpty())
    		return null;    	
    	
    	for(Node n : set)
    	{
    		if(n.getCost() <= lowestCost)
    		{   		
    			lowestCost = n.getCost();
    			selected = n;
    		}   		
    	}
      
        return selected;
    }

    public Node search(Node root, HashMap<String,Boolean> state) {
    	
    	HashSet<Node> open = new HashSet<Node>();   	
    	HashSet<Node> closed = new HashSet<Node>(); //explored nodes 	    	
    	
    	for(Node n : root.getChildNodes())
		{
			open.add(n);    			
		}       	
    	
    	while(!open.isEmpty())
    	{
    		Node current = getLowestCost(open);
    		
    		open.remove(current);
    		closed.add(current);
    		
    		if(current.getAction().checkRunnable() && current.active) 
            {        		
    			if(plan.checkCondition(current, state))  //test action for viability	
    				return current;  //build action sequence  and return queue
    		
    			for(Node n : current.getChildNodes())
    			{
    				open.add(n);    			
    			}     
            }
    	}
    	
    	return null;
    }  

}
