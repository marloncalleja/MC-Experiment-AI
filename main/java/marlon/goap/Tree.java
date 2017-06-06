package marlon.goap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import marlon.goap.Node;
import marlon.goap.actions.Action;

public class Tree {
	
	 /**
     * populate the tree and return the root node
     */
    public Node populateTree(HashMap<String, Boolean> goal, HashSet<Action> availableActions) {
    	
    	Node root = new Node(goal,null, null); //root for tree 
    	HashSet<Action> usableActions = new HashSet<Action>(availableActions); //copy actions    	
    	HashSet<Action> removeActions = new HashSet<Action>(); //actions to remove
    	
    	//Define upper level nodes, prepare for branching
    	for(Action a : usableActions)
    	{
    		HashMap<String, Boolean> actionEffect = a.Effects(); //get action Effect
    		
    		for (String g : goal.keySet()) {
    			
    			for(String e : actionEffect.keySet())
    			{    				
    				if ((e.equals(g)) && ((actionEffect.get(e)).equals(goal.get(g))))  //find Actions which link to goal state
    				{
    					Node node = new Node(a.Preconditions(),a,root); //declare action as node, set its root as parent
    					root.addChildNode(node); //link action to root
    					removeActions.add(a); //action to remove from set, no longer needed for graph population since already defined    					
    				}   				
    			}  	    			
    		}    		
    	}   	
    	
    	
    	
    	cleanActions(usableActions, removeActions); 	
    	   	
    	
    	   	
    	List<Node> upperLevel = root.getChildNodes();     	
    	recursiveFill(usableActions, removeActions, upperLevel);   	
    
    	
   
        return root;
    }
    
    
    
    private void recursiveFill(HashSet<Action> usableActions, HashSet<Action> removeActions, List<Node> treeLevel)
    {
    	
    	List<Node> level = treeLevel;	
    	List<Node> newLevel = new ArrayList<Node>();
    	boolean isSolution = false; //used to end recursion
    	    	
    	
    	//Define children nodes 
    	
    	for(Action a : usableActions) //iterate usable actions	
    	{    		
    		HashMap<String, Boolean> actionEffect = a.Effects(); //get action Effect
    		
    		for(Node n : level) //iterate current level nodes
    		{    			
    			HashMap<String, Boolean> precondition = n.getConditionalState(); //get parent precondition 
    			
    			for (String p : precondition.keySet())
    			{    			
    				for(String e : actionEffect.keySet())
    				{	//check if parent precondition and action effect link together   
    					if ((e.equals(p)) && ((actionEffect.get(e)).equals(precondition.get(p))))     						  						
    					{	    						
    						if(n.getAction() != a) //avoid build of same action
    						{
    							a.calcVariableCost(a, n.getAction());
    							Node child = new Node(a.Preconditions(),a, n);  
    							n.addChildNode(child); //declare action as child Node  
    						
    							removeActions.add(a); //processed actions for removal
    							newLevel.add(child); //prepare new tree level    						
    							isSolution = true;    	    				
    						}
    					}   				
    				}
    			}   			
    		}    		
    	}  
    	
    	
    	cleanActions(usableActions, removeActions);
    	
    	
    	if(!usableActions.isEmpty() && isSolution) //recursively call method, to build unsolved actions
    		recursiveFill(usableActions, removeActions, newLevel);       	
    	
    }       
    
    
    private void cleanActions(HashSet<Action> usableActions, HashSet<Action> removeActions)
    {
    	for(Action a : removeActions) //Clear previously declared actions
    	{
    		if(a.isMultiuse == false) //Actions which are used in multiple subtrees are not deleted   		
    			usableActions.remove(a); 
    	}   
    	
    	removeActions.clear();  
    }  
    
    
    public void resetVisitedNodes(Node root)
    {
    	root.setVisited(false);    	
    	resetChildNodes(root.getChildNodes());
    }       
    
    private void resetChildNodes(List<Node> level)
    {
    	if(!level.isEmpty())
    	{    
    		List<Node> nextLevel = new ArrayList<Node>();   
    		
    		for(Node n : level)
    		{
    			n.setVisited(false);   			
    			nextLevel.addAll(n.getChildNodes());   			
    		}     	      		
    	
    		resetChildNodes(nextLevel);
    	}    	
    }    
    
    public void printTree(List<Node> level)
    {	
    	
    	if(!level.isEmpty())
    	{    
    		List<Node> nextLevel = new ArrayList<Node>();   
    		
    		for(Node n : level)
    		{
    			if (n.getParent().getAction() == null)
    			{    			
    				System.out.println(n.getAction().getClass().getSimpleName() +  " (" + n.getAction().fixedCost + " + " + n.getAction().variableCost + ")" + "     Running Cost: " + n.getCost());   
    			}
    			else
    			{
    				System.out.println((n.getParent().getAction().getClass().getSimpleName()) + " <- " + (
        					n.getAction().getClass().getSimpleName() + " (" + n.getAction().fixedCost + " + " + n.getAction().variableCost + ")" + "    Running Cost: " + n.getCost()));   
    			}	
    			
    			
    			nextLevel.addAll(n.getChildNodes());   			
    		}     	   

    		System.out.println("---------------------------------------------------------");	
    	
    		printTree(nextLevel);
    	}
    } 

}
