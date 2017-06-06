package marlon.goap.actions;

import java.util.ArrayList;
import java.util.List;

import marlon.minecraftai.ai.net.NetworkHelper;
import marlon.minecraftai.ai.strategy.AIStrategy;
import marlon.minecraftai.ai.strategy.InventoryDefinition;
import marlon.minecraftai.ai.strategy.UnstoreStrategy;
import marlon.minecraftai.ai.strategy.UnstoreStrategy.Wishlist;
import net.minecraft.util.BlockPos;

public class UnstoreWood extends Action {
	
	public UnstoreWood(){			
		addEffect("hasFuel", true);
		setWorldPosition();
		this.fixedCost = 5 - 2;	
		enable = true;
	}
	
	@Override
	public boolean checkRunnable() {
		
		if(!enable)
			return false;
		
		if(getChest(17) != null)		
			return true; 
		else
			return false;
	}
	
	@Override
	public void setWorldPosition() {	
		
		this.worldPosition = getChest(17);
		
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
		
		list.add(new UnstoreStrategy(new Wishlist(new InventoryDefinition(("[{slotIndex: 0, amount: 1, itemId: 17, damageValue: 1}]")))));
		
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
