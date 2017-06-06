package marlon.goap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import marlon.goap.actions.Action;

public class BFS {	   
	
	Planner plan = new Planner();    
	
	 /**
     * returns the first unvisited node in list
     */
    private Node getUnvisitedChildNodes(Node node) {
    	
    	if(node.getChildNodes() != null)
    	{
    		for(Node n : node.getChildNodes())
    		{
    			if(!n.isVisited())
    				return n;
    		}
    	}	
      
        return null;
    }

    public Node search(Node root, HashMap<String,Boolean> state) {
        // BFS uses Queue
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(root); //Push the root node in the Queue.
        root.setVisited(true);
        
        //System.out.println();
        
        while (!queue.isEmpty()) { //Loop until the queue is empty.
            Node node = queue.remove();  // remove the head of queue
            Node child = null;
            while ((child = getUnvisitedChildNodes(node)) != null) {
                child.setVisited(true); //mark as visited 
                
            	if(child.getAction().checkRunnable() && child.active) 
            	{                
            		//System.out.println();
            		if(plan.checkCondition(child, state)) //test action for viability
            			return child; //build action sequence  and return queue
                
            		queue.add(child); // insert unvisited child in the queue
            	}
            }
        }
        
        return null;
    }     
    
}
