package marlon.goap.actions;

import java.util.ArrayList;
import java.util.List;

import marlon.minecraftai.ai.net.NetworkHelper;
import marlon.minecraftai.ai.strategy.AIStrategy;
import marlon.minecraftai.ai.strategy.FurnaceStrategy;
import marlon.minecraftai.ai.strategy.FurnaceStrategy.FurnaceTaskList;


public class SmeltShearsMats extends Action{
	
	public SmeltShearsMats(){			
		addPrecondition("hasIronOre",true); 			
		addEffect("hasShearsMats", true);
		setWorldPosition();
		this.fixedCost = 10;
		enable = true;
	}	
	
	@Override
	public boolean checkRunnable() {
		
		if(!enable)         //check if furnace is available
			return false;
		
		if(getFurnace() != null)		
			return true; 
		else
			return false;  
	}	
	
	@Override
	public void setWorldPosition() {		
		
		this.worldPosition = getFurnace();			
	}	

		
	@Override
	public Action action() {		
		
		if(checkRunnable())				
			return this;					
		else		
			return null;
	}	
	
	public List<AIStrategy> strategy()
	{
		List<AIStrategy> list = new ArrayList<AIStrategy>();
		
		list.add(new FurnaceStrategy(new FurnaceTaskList(true,false,true))); //place raw material, take finished material (consider fuel as already placed)
		
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
