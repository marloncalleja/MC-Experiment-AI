package marlon.goap;

import java.util.HashMap;

import marlon.goap.actions.Action;
import marlon.minecraftai.ai.AIHelper;
import marlon.minecraftai.ai.net.NetworkHelper;
import marlon.minecraftai.ai.scanner.BlockRangeScanner;
import marlon.minecraftai.ai.scanner.ChestBlockHandler.ChestData;
import marlon.minecraftai.ai.scanner.FurnaceBlockHandler.FurnaceData;
import marlon.minecraftai.ai.strategy.AIStrategy;
import marlon.minecraftai.ai.strategy.CraftStrategy;
import marlon.minecraftai.ai.strategy.CraftStrategy.CraftingTableData;
import marlon.minecraftai.ai.strategy.CraftStrategy.CraftingTableFinder;
import marlon.minecraftai.ai.strategy.CraftStrategy.CraftingWish;
import marlon.minecraftai.ai.strategy.FurnaceStrategy.FurnacePathFinder;
import marlon.minecraftai.ai.strategy.FurnaceStrategy.FurnaceTaskList;
import marlon.minecraftai.ai.strategy.InventoryDefinition;
import marlon.minecraftai.ai.strategy.UnstoreStrategy.UnstorePathFinder;
import marlon.minecraftai.ai.strategy.UnstoreStrategy.Wishlist;
import marlon.minecraftai.ai.task.inventory.ItemWithSubtype;
import net.minecraft.util.BlockPos;

public class Utility extends AIHelper{
	
	public BlockPos getChest(int id){		
		
		Wishlist w = new Wishlist(new InventoryDefinition(("[{slotIndex: 0, amount: 1, itemId: " + id + ", damageValue: 1}]")));	
		UnstorePathFinder u = new UnstorePathFinder(w);			
		
		//scan for surrounding chests, from the player's position
		BlockRangeScanner scanner = u.constructScanner(getPlayerPosition());
		scanner.scanArea(getWorld());
		
		//retrieve all chests found during scan
		HashMap<BlockPos, ChestData> chests = u.getChests();			
		
		if(chests.isEmpty() != true)
		{		
			//find relevant chest for current action. (first in list)
			for (BlockPos c : chests.keySet()) { 		
			
				if (u.getList().couldUseOneOf(chests.get(c)))
				return chests.get(c).getPos();	
			}	
		}
		
		return null;
	}
	
	public BlockPos getCraftingTable(int id){		
		
		ItemWithSubtype item = new ItemWithSubtype(id, 0);		
		CraftingWish wish = new CraftingWish(1, item);		
		CraftingTableFinder f = new CraftingTableFinder(wish);		
		
		//scan for surrounding tables, from the player's position
		BlockRangeScanner scanner = f.constructScanner(getPlayerPosition());
		scanner.scanArea(getWorld());
		
		//retrieve all crafting tables found during scan
		HashMap<BlockPos, CraftingTableData> tables = f.getCraftingTables();	
		
		if(tables.isEmpty() != true)
		{
			//find crafting table for current action. (first in list)
			for (BlockPos t : tables.keySet()) { 			
			
				return tables.get(t).getPos();	
			}	
		}
		
		return null;
	}
	
	public BlockPos getFurnace(){	
		
		FurnaceTaskList wish = new FurnaceTaskList(true,false,true);		
		FurnacePathFinder f = new FurnacePathFinder(wish);
		
		//scan for surrounding furnace, from the player's position
		BlockRangeScanner scanner = f.constructScanner(getPlayerPosition());
		scanner.scanArea(getWorld());
		
		//retrieve all furnace found during scan
		HashMap<BlockPos, FurnaceData> furnace = f.getFurnace();
		
		if(furnace.isEmpty() != true)
		{
			//find furnace for current action. (first in list)
			for (BlockPos t : furnace.keySet()) { 				
			
				return furnace.get(t).getPos();	
			}	
		}
		
		return null;
	}

	
	public int getDistanceBetweenNodes(Action a, Action b)
	{
		int distance = 999; //default to high variable cost
		
		BlockPos positionA = a.getWorldPosition();
		BlockPos positionB = b.getWorldPosition();
		
		if((positionA != null) && (positionB != null))		
		distance = (int) Math.round(Math.sqrt
				(positionA.distanceSq(positionB.getX(), positionB.getY(), positionB.getZ()))); 
		//get distance between points		
						
		return distance;
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