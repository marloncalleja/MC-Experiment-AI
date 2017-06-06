package marlon.goap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import marlon.goap.actions.Action;

public class Planner {	
	
	
	/** Check that all items in 'Node' are in 'worldState'. If just one does not match or is not there then this returns false.
	 *  
	 */	
	public boolean checkCondition(Node node, HashMap<String,Boolean> worldState)
    {
     	boolean foundMatch = true;	
     	
    	for (String n : node.getConditionalState().keySet()) {
				
				boolean stateMatch = false;
				
				for (String s : worldState.keySet()) {  
					if ((s.equals(n)) && ((worldState.get(s)).equals(node.getConditionalState().get(n)))) //check key and value
					{
						stateMatch = true;  
						break;
					}			
				}
				if (!stateMatch)
				foundMatch = false;	
			}      		
    	
    	
    	return foundMatch;
    }
    
    
    public Queue<Action> buildSequence(Node n) 
    {

		// work up the tree, using parent node to climb
		List<Action> result = new ArrayList<Action>();
	
		while (n != null) {
			if (n.getAction()!= null) {
				result.add(n.getAction()); // insert action 			
			}
			n = n.getParent();
		}
		// we now have this action list in correct order
		
		Queue<Action> queue = new LinkedList<Action>();
		
		for (Action a : result) {	
			
			queue.add(a);
		}
		// we have a plan!		
		
		return queue;
    }
    
    
    public void printSolution(Node n)
    {
    	int i = 1;
    	    	
    	List<Action> result = new ArrayList<Action>();
    		
    	System.out.println("Running Cost: " + n.getCost());       
    	
    	// work up the tree, using parent node to climb
    	while (n != null) {
    		if (n.getAction()!= null) {
    			System.out.println(i + ": " + n.getAction().getClass().getSimpleName());   	
    			i++;
    		}
    		n = n.getParent();
    	}
    	
    	System.out.println("--------------------------------");  
    } 
	
		

}
