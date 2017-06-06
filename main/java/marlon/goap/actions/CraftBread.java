package marlon.goap.actions;

import java.util.ArrayList;
import java.util.List;

import marlon.minecraftai.ai.net.NetworkHelper;
import marlon.minecraftai.ai.strategy.AIStrategy;
import marlon.minecraftai.ai.strategy.CraftStrategy;

public class CraftBread extends Action{
	
	public CraftBread(){		
		addPrecondition("hasWheat",true);	
		addEffect("hasFood", true);
		setWorldPosition();
		this.fixedCost = 7 - 2;	
		enable = true;
	}
	
	@Override
	public boolean checkRunnable() {
		
		if(!enable)         //check if crafting table is available
			return false;
		
		if(getCraftingTable(297) != null)		
			return true; 
		else
			return false;  
	}	
	
	@Override
	public void setWorldPosition() {
		
		this.worldPosition =  getCraftingTable(297);		
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
		
		list.add(new CraftStrategy(1, 297, 0)); //ID for Bread	
		
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
