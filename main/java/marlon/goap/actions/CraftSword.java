package marlon.goap.actions;

import java.util.ArrayList;
import java.util.List;

import marlon.minecraftai.ai.net.NetworkHelper;
import marlon.minecraftai.ai.strategy.AIStrategy;
import marlon.minecraftai.ai.strategy.CraftStrategy;

public class CraftSword extends Action {
	
	public CraftSword(){			
		addPrecondition("hasSwordMats",true);		
		addEffect("hasSword", true);
		setWorldPosition();
		this.fixedCost = 7;	
		enable = true;
	}
	
	@Override
	public boolean checkRunnable() {
		
		if(!enable)         //check if crafting table is available
			return false;
		
		if(getCraftingTable(267) != null)		
			return true; 
		else
			return false;  
	}	
	
	@Override
	public void setWorldPosition() {
	
		this.worldPosition = getCraftingTable(267);	
		
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
		
		list.add(new CraftStrategy(1, 267, 0)); //ID for Sword		
		
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
