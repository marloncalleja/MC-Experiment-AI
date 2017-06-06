package marlon.goap.actions;

import java.util.ArrayList;
import java.util.List;

import marlon.minecraftai.ai.net.NetworkHelper;
import marlon.minecraftai.ai.strategy.AIStrategy;
import marlon.minecraftai.ai.strategy.CraftPickaxeStrategy;

public class CraftPickaxe extends Action{
	
	public CraftPickaxe(){		 	
		addPrecondition("hasPickaxeMats",true);	
		addEffect("hasPickaxe", true);
		setWorldPosition();
		this.isMultiuse = true;  //can be used in multiple sub trees
		this.fixedCost = 7;	
		enable = true;
	}
	
	@Override
	public boolean checkRunnable() {
		
		if(!enable)         //check if crafting table is available
			return false;
		
		if(getCraftingTable(257) != null)		
			return true; 
		else
			return false;  
	}	
	
	@Override
	public void setWorldPosition() {
	
		this.worldPosition = getCraftingTable(257);	
		
	}	

	
	@Override
	public Action action() {		
		
		if(checkRunnable())				
			return this;					
		else		
			return null;
	}
	
	@Override
	public List<AIStrategy> strategy()
	{		
		List<AIStrategy> list = new ArrayList<AIStrategy>();
		
		list.add(new CraftPickaxeStrategy(1, 257, 0)); //ID for Pickaxe	
		
		return list;
	}

	@Override
	public AIStrategy getResumeStrategy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NetworkHelper getNetworkHelper() {
		// TODO Auto-generated method stub
		return null;
	}

}
