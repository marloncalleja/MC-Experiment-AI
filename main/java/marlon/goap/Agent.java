package marlon.goap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.google.common.base.Predicate;

import marlon.goap.Node;
import marlon.goap.actions.*;

import marlon.goap.actions.Action;
import marlon.minecraftai.ai.strategy.CraftPickaxeStrategy;
import marlon.minecraftai.ai.strategy.CraftStrategy;
import marlon.minecraftai.ai.strategy.FurnaceStrategy;

public class Agent  {
	
	private HashSet<Action> availableActions;	
	private StateScanner ss;	
	private Planner p;
	private BFS bfs;
	private DFS dfs;
	private AStar astar;
	private HashMap<String,Boolean> worldState; 
	private HashMap<String,Boolean> goal; 
	
	
	public Agent(HashMap<String,Boolean> setGoal) {
		
		resetStrategy();
		
		goal = setGoal;
		worldState = new HashMap<String,Boolean>();	
		
		availableActions = new HashSet<Action>();	
		ss = new StateScanner();	
		p = new Planner();
		bfs = new BFS();
		dfs = new DFS();
		astar = new AStar();	
	}
	
	private HashSet<Action> loadActions () //ADD ACTIONS TO PROCESS -- Here
	{	
		HashSet<Action> actions = new HashSet<Action>();	
		
		//WOOL / FUEL
		actions.add(new UnstoreWool());
		actions.add(new ShearSheep());
		actions.add(new UnstoreShears());	
		actions.add(new CraftShears());				
		actions.add(new UnstoreShearsMats());			
		actions.add(new SmeltShearsMats());
		actions.add(new UnstoreIronOre());	
		actions.add(new MineIronOre());		
		
		//FUEL
		actions.add(new ChopWood());
		actions.add(new UnstoreWood());		
		actions.add(new MineCoalOre());	
		actions.add(new UnstoreCoal());	
		actions.add(new UnstoreAxe());			
		actions.add(new CraftAxe());			
		actions.add(new UnstoreAxeMats());		
		
		
		//FOOD
		actions.add(new UnstoreApple());	
		actions.add(new CraftBread());	
		actions.add(new UnstoreWheat());
		actions.add(new CookMeat());
		actions.add(new GatherRawMeat());
		actions.add(new CraftSword());
		actions.add(new UnstoreRawMeat());		
		actions.add(new UnstoreSword());
		actions.add(new UnstoreSwordMats());
	
		
		//Common
		actions.add(new UnstorePickaxe());	
		actions.add(new CraftPickaxe());		
		actions.add(new UnstorePickaxeMats());	
		
		return actions;
	}	
	
	private HashMap<String,Boolean> loadWorldState()
	{	
		HashMap<String,Boolean> stateData = new HashMap<String,Boolean>();
		
		//FOOD		
		stateData.put("hasWheat", false);	
		stateData.put("hasMeat", false);	
		//stateData.put("hasSword", ss.hasSword());	
		stateData.put("hasSword", false);
		stateData.put("hasSwordMats", false);	//*filter is not supported - cannot scan for iron ingot*			
		
		//WOOL			
		//stateData.put("hasShears", ss.hasShears());	
		stateData.put("hasShears", false);
		stateData.put("hasShearsMats", false); //*filter is not supported - cannot scan for iron ingot*
		//stateData.put("hasIronOre", ss.hasIronOre());
		stateData.put("hasIronOre", false);	
		
		//FUEL /WOOL		
		//stateData.put("hasAxe", ss.hasAxe());
		stateData.put("hasAxe", false);
		//stateData.put("hasPickaxe",ss.hasPickaxe()); 	
		stateData.put("hasPickaxe", false); 	
		stateData.put("hasAxeMats", false); //*filter is not supported - cannot scan for iron ingot*
		stateData.put("hasPickaxeMats", false); //*filter is not supported - cannot scan for iron ingot*
		
		return stateData;		
	}
	
	public Queue<Action> loadData()
	{		
		loadWorldState();	
		availableActions = loadActions();
		worldState = loadWorldState();
		Node root = new Node(null,null,null);
		Node solution = new Node(null,null,null);
		Queue<Action> sequence = new LinkedList<Action>();		
		
		//generateTreeTest(root);
		//printTree(root);  
		//printSolutions(root, solution);
		//findSolutionTest(root, solution);   
    	
		root = new Tree().populateTree(goal, availableActions);
		//solution = this.astar.search(root, worldState);    	
		//solution = this.bfs.search(root, worldState);   	
		solution = this.dfs.search(root, worldState);  
    	
    		  
    	sequence = p.buildSequence(solution);	
    	
		return sequence;	//data delivered to minecraftai.ai.command.CommandRegistry	
	}
	
	public void generateTreeTest(Node root)
	{
		availableActions = loadActions();	
		root = new Tree().populateTree(goal, availableActions);	//PRE-RUN
		
		double totalTime = 0;		
		int measurementRepetitions = 10000; //Algorithm iterations 
		List<String> sample = new ArrayList<String>();
		
		for (int j = 1; j <= measurementRepetitions; j++)
		{
			availableActions = loadActions();		
		
			double startTime = System.nanoTime();			
				
			root = new Tree().populateTree(goal, availableActions);
		
			double endTime = System.nanoTime();	
			double tD = endTime - startTime;
			
			
			if(j == 1 || j % 500 == 0) 
				sample.add("Iteration " + j + ": " + tD);
			
			
			totalTime += tD;		
		}
		
				
		for(String s : sample)
		{
			System.out.println(s);		
		}
		
		// take the mean time taken
        double meanTime = (totalTime / measurementRepetitions);
    	System.out.println("Mean time for " + measurementRepetitions + " samples: " + meanTime);		
	}
	
	
	public void printTree(Node root)
	{
		Tree t = new Tree();
		availableActions = loadActions();	    	
    	root = t.populateTree(goal, availableActions);
    	t.printTree(root.getChildNodes()); 
	}
	
	public void printSolutions(Node root, Node solution)
	{
		availableActions = loadActions();	 
		worldState = loadWorldState();
		root = new Tree().populateTree(goal, availableActions);     
		
		do
    	{     		
    		solution = this.astar.search(root, worldState);    	
    		//solution = this.bfs.search(root, worldState);   	
    		//solution = this.dfs.search(root, worldState);     
    		
    		if(solution != null)    
    		{
    			p.printSolution(solution);    	
    			solution.disable();
    		}
    		
    		new Tree().resetVisitedNodes(root);
    	
    		
    	}while(solution != null);       	
	
	}	
	
	private void findSolutionTest(Node root, Node solution)
	{	
		int testRepeat = 10000; //Test iterations 	
		int solutionFailedCount = 0;
		double startTime;
		double endTime;
		double tD;
		
		double astarTotalTime = 0;
		double bfsTotalTime = 0;
		double dfsTotalTime = 0;
		
		double astarTotalCost = 0;;
		double bfsTotalCost = 0;;
		double dfsTotalCost = 0;
		
		
		for (int j = 1; j <= testRepeat; j++)
		{
			availableActions = loadActions();	
			randomActiveActions(availableActions);	
			worldState = randomWorldState();		
	    	root = new Tree().populateTree(goal, availableActions); 
	    	
	    	
	    	if(j == 1 || j % 1000 == 0) 
    			System.out.println("Test " + j + ": ");
	    	
	    	
	    	solution = new Node(null,null,null);	    	
	    	
	    	solution = this.astar.search(root, worldState);  //PRE-RUN
	    	solution = this.bfs.search(root, worldState); 
	    	new Tree().resetVisitedNodes(root);	
	    	solution = this.dfs.search(root, worldState);
	    	new Tree().resetVisitedNodes(root);	   	
	    		    	
	    	
	    	if(solution != null)
	    	{   		
		    	
		    	
		    	//-------------------------------------ASTAR
		    	
		    	solution = new Node(null,null,null);
		    	
		    	startTime = System.nanoTime();	
		    	
		    		solution = this.astar.search(root, worldState);     		
		    	
		    	endTime = System.nanoTime();
		    	tD = endTime - startTime;	    
		    	
		    	if(solution != null)
		    	{  
		    		if(j == 1 || j % 1000 == 0) //print out last 25 samples
		    		{
		    			System.out.println("A-STAR");	    
		    			System.out.println("Time taken: " + tD + " | Solution Cost: " + solution.getCost());	    		  
		    			System.out.println("------------------------------");	
		    		}
		    		
		    		astarTotalTime += tD;    	
		    		astarTotalCost += solution.getCost();	
		    	}		
		    	
		    	
		    	//------------------------------------BFS
		    	
				solution = new Node(null,null,null);
				
		    	startTime = System.nanoTime();	
		    	
		    		solution = this.bfs.search(root, worldState);  
		    	
		    	endTime = System.nanoTime();
		    	tD = endTime - startTime;
		    	
		    	
		    	if(solution != null)
		    	{
		    		if(j == 1 || j % 1000 == 0) //print out last 25 samples
		    		{
		    			System.out.println("BFS");	    
		    			System.out.println("Time taken: " + tD + " | Solution Cost: " + solution.getCost());	    		  
		    			System.out.println("------------------------------");	
		    		}
		    		
		    		
		    		bfsTotalTime += tD;
		    		bfsTotalCost += solution.getCost();	
		    	}	    	
		    		    	
		    	new Tree().resetVisitedNodes(root);	    	
		    	
		    	
		    	//------------------------------------DFS
		    	
		    	solution = new Node(null,null,null);
		    		    	
		    	startTime = System.nanoTime();		    	
		    	
		    		solution = this.dfs.search(root, worldState);
		    	
		    	endTime = System.nanoTime();
		    	tD = endTime - startTime;	
		    	
		    	if(solution != null)
		    	{	
		    		if(j == 1 || j % 1000 == 0) //print out last 25 samples
		    		{
		    			System.out.println("DFS");	    
		    			System.out.println("Time taken: " + tD + " | Solution Cost: " + solution.getCost());	    		  
		    			System.out.println("------------------------------");	
		    		}	    		
		    		
		    		dfsTotalTime += tD;
		    		dfsTotalCost += solution.getCost();	
		    	}
		    	
		    	new Tree().resetVisitedNodes(root);		    	
	    	
	    	
	    	}
	    	else
	    		solutionFailedCount++;	    	
    	
		}
		
		
		System.out.println("A-STAR");	
        double astarMeanTime = (astarTotalTime  / (testRepeat - solutionFailedCount));
    	System.out.println("Mean solution time: " + astarMeanTime);	
    	double astarMeanCost = (astarTotalCost  / (testRepeat - solutionFailedCount));
     	System.out.println("Mean action cost: " + astarMeanCost);	
     	System.out.println("-----------------------");	
     	
    	System.out.println("BFS");	
        double bfsMeanTime = (bfsTotalTime  / (testRepeat - solutionFailedCount));
    	System.out.println("Mean solution time: " + bfsMeanTime);	
    	double bfsMeanCost = (bfsTotalCost  / (testRepeat - solutionFailedCount));
     	System.out.println("Mean action cost: " + bfsMeanCost);	
     	System.out.println("-----------------------");	
		
     	System.out.println("DFS");	
        double dfsMeanTime = (dfsTotalTime  / (testRepeat - solutionFailedCount));
    	System.out.println("Mean solution time: " + dfsMeanTime);	
    	double dfsMeanCost = (dfsTotalCost  / (testRepeat - solutionFailedCount));
     	System.out.println("Mean action cost: " + dfsMeanCost);	
     	
     	System.out.println("-----------------------");	
    	System.out.println("No solution found for " + solutionFailedCount + " out of " + testRepeat + " tests.");	
		
	}
	
	private void randomActiveActions(HashSet<Action> actions)
	{
		for(Action a : actions)
		{
			a.randEnable(Math.random() < 0.30); //enable factor - must be less than 
		}
	}
	
	private HashMap<String,Boolean> randomWorldState()
	{	
		double randFactor = 0.50; //enable factor - must be less than 
		HashMap<String,Boolean> stateData = new HashMap<String,Boolean>();
		
		//FOOD		
		stateData.put("hasWheat", Math.random() < randFactor );	
		stateData.put("hasMeat", Math.random() < randFactor );			
		stateData.put("hasSword", Math.random() < randFactor );
		stateData.put("hasSwordMats", Math.random() < randFactor);			
		
		//WOOL		
		stateData.put("hasShears", Math.random() < randFactor);
		stateData.put("hasShearsMats", Math.random() < randFactor);		
		stateData.put("hasIronOre", Math.random() < randFactor);	
		
		//FUEL /WOOL			
		stateData.put("hasAxe", Math.random() < randFactor);			
		stateData.put("hasPickaxe", Math.random() < randFactor); 	
		stateData.put("hasAxeMats", Math.random() < randFactor); 
		stateData.put("hasPickaxeMats", Math.random() < randFactor);
		
		return stateData;		
	}
	
	
	
	private void resetStrategy() // reset attribute for active
	{
		CraftPickaxeStrategy.active = true;
		CraftStrategy.active = true;
		FurnaceStrategy.active = true;
	}

}
