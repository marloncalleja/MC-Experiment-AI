package marlon.goap.actions;

import java.util.ArrayList;
import java.util.List;

import marlon.minecraftai.ai.net.NetworkHelper;
import marlon.minecraftai.ai.path.TreePathFinder;
import marlon.minecraftai.ai.path.TreePathFinderStrategy;
import marlon.minecraftai.ai.strategy.AIStrategy;

public class ChopWood extends Action{
	
	public ChopWood(){				
		addPrecondition("hasAxe",true); 	
		addEffect("hasFuel", true);
		this.fixedCost = 14 - 2;		
		this.variableCost = 0; //world position is not supported, added as default
		enable = true;
	}	
	
	@Override
	public boolean checkRunnable() {
		
		if(!enable)
			return false;
			
		
		return true; 			
	}	
	
	@Override
	public void setWorldPosition() {
		// TODO Auto-generated method stub
		
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
		
		list.add(new TreePathFinderStrategy(new TreePathFinder(null, false), "Getting wood"));
		
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
