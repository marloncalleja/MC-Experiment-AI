package marlon.goap.actions;

import java.util.ArrayList;
import java.util.List;

import marlon.minecraftai.ai.net.NetworkHelper;
import marlon.minecraftai.ai.strategy.AIStrategy;
import marlon.minecraftai.ai.strategy.InventoryDefinition;
import marlon.minecraftai.ai.strategy.UnstoreStrategy;
import marlon.minecraftai.ai.strategy.UnstoreStrategy.Wishlist;

public class UnstoreWheat extends Action {
	
	public UnstoreWheat(){				
		addEffect("hasWheat", true);
		setWorldPosition();
		this.fixedCost = 5;	
		enable = true;
	}
	
	@Override
	public boolean checkRunnable() {
		
		if(!enable)
			return false;
		
		if(getChest(296) != null)		
			return true; 
		else
			return false;
	}
	
	@Override
	public void setWorldPosition() {	
		
		this.worldPosition = getChest(296);
		
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
		
		list.add(new UnstoreStrategy(new Wishlist(new InventoryDefinition(("[{slotIndex: 0, amount: 3, itemId: 296, damageValue: 1}]")))));
		
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
