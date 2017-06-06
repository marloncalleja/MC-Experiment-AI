package marlon.goap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import marlon.goap.actions.Action;

public class Node {
	
	private HashMap<String, Boolean> conditionalState;
    private Action action;
    private Node parent;
    private List<Node> childNode;      
    private boolean visited;
    private int runningCost;
    protected boolean active; // for testing
    
    public Node(HashMap<String, Boolean> precondition, Action action, Node parent) {
    	this.conditionalState = precondition;
		this.action = action;
		this.visited = false;
		this.childNode = new ArrayList<Node>();	
		this.parent = parent;
		this.runningCost = calculateCost(action, parent);		
		//this.runningCost = 0;
		this.active = true;
	}
    
    
    public int calculateCost(Action action, Node parent)
    {
    	int cost = 0;
    	
    	if(action!= null)     		
    		cost = action.calculateTotalCost();  //fixed cost + variable cost  	
    	
    	if(parent != null)
    		cost += parent.getCost();  	
    	
    	return cost;
    }
    
    public void addChildNode(Node n)
    {
    	this.childNode.add(n);
    }   
    
    public void setParent(Node n)
    {
    	this.parent = n;
    }     
    
    public void setVisited(boolean v)
    {
    	this.visited = v;
    }   
    
    public Action getAction()
    {
    	return this.action;
    }    
    
    public List<Node> getChildNodes()
    {
    	return this.childNode;
    }    
    
    public Node getParent()
    {
    	return this.parent;
    }   
    
    public HashMap<String, Boolean> getConditionalState()
    {
    	return this.conditionalState;
    }  
    
    public boolean isVisited()
    {
    	return this.visited;
    }  
    
    
    public double getCost()
    {
    	return this.runningCost;
    }    
    
    public void disable()
	{		
    	this.active = false;			
	}

}
