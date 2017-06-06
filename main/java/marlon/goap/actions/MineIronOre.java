package marlon.goap.actions;

import java.util.ArrayList;
import java.util.List;

import marlon.minecraftai.ai.net.NetworkHelper;
import marlon.minecraftai.ai.path.MineIronPathFinder;
import marlon.minecraftai.ai.strategy.AIStrategy;
import marlon.minecraftai.ai.strategy.PathFinderStrategy;


public class MineIronOre extends Action{
	
	public MineIronOre(){			
		addPrecondition("hasPickaxe",true); 	
		addEffect("hasIronOre", true);
		this.fixedCost = 14;	
		this.variableCost = 1;   //world position is not supported, added as default
		enable = true;
	}	
	
	@Override
	public boolean checkRunnable() {
		
		if(!enable)
			return false;
		
		return true;   //check if ore is available
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
		list.add(new PathFinderStrategy(new MineIronPathFinder(getLookDirection(), getPlayerPosition().getY()),"Mine iron ore "));
		//mine Iron Ore
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
