package marlon.goap.actions;

import java.util.ArrayList;
import java.util.List;

import marlon.minecraftai.ai.net.NetworkHelper;
import marlon.minecraftai.ai.strategy.AIStrategy;
import marlon.minecraftai.ai.strategy.CraftStrategy;

public class CraftAxe extends Action {
	
	public CraftAxe(){		
		addPrecondition("hasAxeMats",true);	
		addEffect("hasAxe", true);
		setWorldPosition();
		this.fixedCost = 7;	
		enable = true;
	}
	
	@Override
	public boolean checkRunnable() {
		
		if(!enable)         //check if crafting table is available
			return false;
		
		if(getCraftingTable(258) != null)		
			return true; 
		else
			return false;  
	}	
	
	@Override
	public void setWorldPosition() {
		
		this.worldPosition =  getCraftingTable(258);	
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
		
		list.add(new CraftStrategy(1, 258, 0)); //ID for Axe	
		
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
