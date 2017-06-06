package marlon.goap.actions;

import java.util.HashMap;
import java.util.List;

import marlon.goap.Utility;
import marlon.minecraftai.ai.AIHelper;
import marlon.minecraftai.ai.strategy.AIStrategy;
import net.minecraft.util.BlockPos;

public abstract class Action extends Utility{
	
	private HashMap<String, Boolean> preconditions;
	private HashMap<String, Boolean> effects;		
	protected boolean enable;	
	
	public BlockPos worldPosition;	
	public int fixedCost;
	public int variableCost; //used for actions which include 'distance' from object	
	public boolean isMultiuse; //can be used in multiple sub trees
	
	
	public Action() {
		this.preconditions = new HashMap<String, Boolean> ();
		this.effects = new HashMap<String, Boolean> ();
		this.worldPosition = getPlayerPosition();   //defaults to player position
		this.fixedCost = 0;
		this.variableCost = 0;
		this.isMultiuse = false;
	}	
	
	public abstract Action action();	
	
	public abstract boolean checkRunnable();	
	
	public abstract List<AIStrategy> strategy();	
	
	public abstract void setWorldPosition();	
	
	
	public void calcVariableCost(Action a , Action b) {  
	
		int cost = this.variableCost;	
		
		cost += getDistanceBetweenNodes(a, b);
		
		this.variableCost = cost;		
	}	
	
	public void addPrecondition(String key, Boolean value) {
		this.preconditions.put(key, value);
	}
	
	public void addEffect(String key, Boolean value) {
		this.effects.put(key, value);
	}	
	
	public HashMap<String, Boolean> Preconditions(){
		return this.preconditions;
	}
	
	public HashMap<String, Boolean> Effects(){
		return this.effects;
	}
	
	public int calculateTotalCost()
	{
		return this.fixedCost + this.variableCost;
	}
	
	public BlockPos getWorldPosition()
	{
		return this.worldPosition;
	}	
	
	public void randEnable(boolean rand)
	{
		this.enable = rand;
	}
}
