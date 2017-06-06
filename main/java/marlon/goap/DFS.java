package marlon.goap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import marlon.goap.actions.Action;

public class DFS {
	
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
	   
	   Stack<Node> stack = new Stack<Node>();
       stack.push(root); //Push the root node in the Stack.  
       root.setVisited(true);
       
      // System.out.println();
       
       while (!stack.isEmpty()) { //Loop until stack is empty. 
           Node node = stack.peek();   // Peek the node of the stack.    
           Node child = getUnvisitedChildNodes(node);
           if(child != null) {  //Get the unvisited child node
               child.setVisited(true); //mark it as traversed 
               
               if(child.getAction().checkRunnable() && child.active) 
               {              
            	   //System.out.println();
            	   if(plan.checkCondition(child, state)) //test action for viability
               			return child;  //build action sequence  and return queue
            	   
            	   //System.out.println(child.getAction().getClass().getSimpleName());
               
            	   stack.push(child); //push it on stack.   
               }
           } else { 
               stack.pop(); // If the node does not have any unvisited child nodes, pop the node from the stack.
           }
       }
       
       return null;
   }      

}
