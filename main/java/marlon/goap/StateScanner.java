package marlon.goap;

import marlon.minecraftai.ai.AIHelper;
import marlon.minecraftai.ai.BlockItemFilter;
import marlon.minecraftai.ai.ItemFilter;
import marlon.minecraftai.ai.net.NetworkHelper;
import marlon.minecraftai.ai.path.world.BlockSets;
import marlon.minecraftai.ai.strategy.AIStrategy;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class StateScanner extends AIHelper {
	
	public boolean hasShears()  //load state for Shears
	{
		final class ShearsFilter implements ItemFilter { 
			@Override
			public boolean matches(ItemStack itemStack) {
				return itemStack != null
						&& itemStack.getItem() instanceof ItemShears;
			}
		}		
		
		final ItemFilter shearsFilter = new ShearsFilter(); 		
		
		boolean hasShears = canSelectItem(shearsFilter); //Check if shears are on hotbar		
		
		return hasShears;			
	}	
	
	public boolean hasPickaxe()  //load state for Shears
	{
		final class PickaxeFilter implements ItemFilter { 
			@Override
			public boolean matches(ItemStack itemStack) {
				return itemStack != null
						&& itemStack.getItem() instanceof ItemPickaxe;
			}
		}		
		
		final ItemFilter pickaxeFilter = new PickaxeFilter(); 		
		
		boolean hasPickaxe = canSelectItem(pickaxeFilter); //Check if pickaxe is on hotbar		
		
		return hasPickaxe;			
	}	
	
	public boolean hasSword()  //load state for Shears
	{
		final class SwordFilter implements ItemFilter { 
			@Override
			public boolean matches(ItemStack itemStack) {
				return itemStack != null
						&& itemStack.getItem() instanceof ItemSword;
			}
		}		
		
		final ItemFilter swordFilter = new SwordFilter(); 		
		
		boolean hasSword = canSelectItem(swordFilter); //Check if sword is on hotbar		
		
		return hasSword;			
	}	
	
	public boolean hasAxe()  //load state for Shears
	{
		final class AxeFilter implements ItemFilter { 
			@Override
			public boolean matches(ItemStack itemStack) {
				return itemStack != null
						&& itemStack.getItem() instanceof ItemAxe;
			}
		}		
		
		final ItemFilter axeFilter = new AxeFilter(); 		
		
		boolean hasAxe= canSelectItem(axeFilter); //Check if axe is on hotbar		
		
		return hasAxe;			
	}
	
	
	public boolean hasWool() //load state for Wool
	{
		final BlockItemFilter WOOL_FILTER = new BlockItemFilter(
				BlockSets.WOOL);
		
		
		boolean hasWool = false; 
		
		for (int i = 0; i < 9 * 4; ++i) {  //check inventory
			if (WOOL_FILTER.matches(getMinecraft().thePlayer.inventory
					.getStackInSlot(i))) {
				hasWool = true;
			}
		}		
		
		return hasWool;
	}
	

	public boolean hasIronOre() //load state for IronOre
	{
		final BlockItemFilter IRONORE_FILTER = new BlockItemFilter(
				BlockSets.IRONORE);
		
		
		boolean hasIronOre = false; 
		
		for (int i = 0; i < 9 * 4; ++i) {  //check inventory
			if (IRONORE_FILTER.matches(getMinecraft().thePlayer.inventory
					.getStackInSlot(i))) {
				hasIronOre = true;
			}
		}
		
		
		return hasIronOre;	
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
