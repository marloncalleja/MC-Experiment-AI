package marlon.goap.actions;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Predicate;

import marlon.minecraftai.ai.animals.AnimalyType;
import marlon.minecraftai.ai.net.NetworkHelper;
import marlon.minecraftai.ai.strategy.AIStrategy;
import marlon.minecraftai.ai.strategy.AttackEntityStrategy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;

public class GatherRawMeat extends Action {
	
	public GatherRawMeat(){			
		addPrecondition("hasSword",true); 	
		addEffect("hasMeat", true);
		setWorldPosition();
		this.fixedCost = 14; 
		enable = true;
	}
	
	@Override
	public boolean checkRunnable() {
		
		if(!enable)
			return false;
		
		final Predicate<Entity> sheepSelector = new SheepSelector(); 		
		
		//search for sheep in area
		List<Entity> foundEntities = getEntities(15, sheepSelector); 
		
		if (!foundEntities.isEmpty()) 
			return true;  
		else
			return false;	
	}
	
	@Override
	public void setWorldPosition() {	
		
	final Predicate<Entity> sheepSelector = new SheepSelector(); 	
		
		//get closest entity to player	
		Entity foundEntity = getClosestEntity(15, sheepSelector); 
		
		if(foundEntity != null)
			this.worldPosition = foundEntity.getPosition();	
		
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
		
		list.add(new AttackEntityStrategy(1, AnimalyType.SHEEP, null));
	
		return list;
	}
	
	private final class SheepSelector implements Predicate<Entity> {
		@Override
		public boolean apply(Entity var1) {
			return var1 instanceof EntitySheep;
		}
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
