/*******************************************************************************
 * This file is part of Minebot.
 *
 * Minebot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Minebot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Minebot.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package marlon.minecraftai.ai.strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import marlon.minecraftai.ai.AIHelper;
import marlon.minecraftai.ai.task.CloseScreenTask;
import marlon.minecraftai.ai.scanner.BlockRangeFinder;
import marlon.minecraftai.ai.scanner.BlockRangeScanner;
import marlon.minecraftai.ai.scanner.ChestBlockHandler;
import marlon.minecraftai.ai.scanner.ChestBlockHandler.ChestData;
import marlon.minecraftai.ai.scanner.SameItemFilter;
import marlon.minecraftai.ai.strategy.InventoryDefinition.InventorySlot;
import marlon.minecraftai.ai.task.AITask;
import marlon.minecraftai.ai.task.OpenChestTask;
import marlon.minecraftai.ai.task.WaitTask;
import marlon.minecraftai.ai.task.inventory.MoveInInventoryTask;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public class UnstoreStrategy extends PathFinderStrategy {

	public static class Wishlist {
		InventoryDefinition wantedInventory;
		// int[] alreadyTaken = new int[36];
		boolean[] noMoreWork = new boolean[36];

		public Wishlist(InventoryDefinition wantedInventory) {
			this.wantedInventory = wantedInventory;
		}

		public boolean couldUseOneOf(ChestData c) {
			for (int i = 0; i < 36; i++) {
				InventorySlot slot = wantedInventory.getSlot(i);
				if (slot.isEmpty() || noMoreWork[i]) {
					continue;
				}

				if (c.couldTakeItem(slot.getFakeMcStack())) {
					return true;
				}
			}
			return false;
		}

		public ArrayList<AITask> getTakeTasks(ItemStack[] mainInventory,
				ChestData c) {
			ArrayList<AITask> tasks = new ArrayList<AITask>();
			for (int inventorySlot = 0; inventorySlot < 36; inventorySlot++) {
				InventorySlot slot = wantedInventory.getSlot(inventorySlot);
				if (slot.isEmpty() || !c.couldTakeItem(slot.getFakeMcStack())) {
					continue;
				}
				if (mainInventory[inventorySlot] != null) {
					if (!new SameItemFilter(mainInventory[inventorySlot])
							.matches(slot.getFakeMcStack())) {
						System.out
								.println("This slot already contains an other item.");
						noMoreWork[inventorySlot] = true;
						continue;
					} else if (mainInventory[inventorySlot].stackSize >= Math
							.min(mainInventory[inventorySlot].getMaxStackSize(),
									slot.amount)) {
						System.out
								.println("This slot already contains enough items.");
						noMoreWork[inventorySlot] = true;
						continue;
					}
				}

				tasks.add(getTask(inventorySlot, slot, c));
			}
			return tasks;
		}

		private MoveInInventoryTask getTask(final int inventorySlot,
				final InventorySlot slot, final ChestData c) {
			return new MoveInInventoryTask() {
				private int fromStack = -2;

				@Override
				public boolean isFinished(AIHelper h) {
					return noMoreWork[inventorySlot] || super.isFinished(h);
				}

				@Override
				protected int getToStack(AIHelper h) {
					GuiChest screen = (GuiChest) h.getMinecraft().currentScreen;
					int slots = screen.inventorySlots.inventorySlots.size();
					int iSlot;
					if (inventorySlot < 9) {
						iSlot = inventorySlot + 3 * 9;
					} else {
						iSlot = inventorySlot - 9;
					}
					return iSlot + (slots - 9 * 4);
				}

				@Override
				protected int getFromStack(AIHelper h) {
					if (fromStack == -2) {
						fromStack = -1;
						GuiChest screen = (GuiChest) h.getMinecraft().currentScreen;
						SameItemFilter filter = new SameItemFilter(
								slot.getFakeMcStack());
						List<Slot> inventorySlots = screen.inventorySlots.inventorySlots;
						int fromStackRating = -1;
						int missing = getMissingAmount(h,
								getSlotContentCount(screen.inventorySlots
										.getSlot(getToStack(h))));
						for (int i = 0; i < inventorySlots.size() - 36; i++) {
							Slot s = inventorySlots.get(i);
							if (filter.matches(s.getStack())) {
								int rating = rateSize(h,
										s.getStack().stackSize, missing);
								if (rating > fromStackRating) {
									fromStackRating = rating;
									fromStack = i;
								}
							}
						}
						if (fromStack < 0) {
							System.out.println("Empty stack.");
							c.markAsEmptyFor(slot.getFakeMcStack(), true);
						}
					}

					return fromStack;
				}

				private int rateSize(AIHelper h, int stackSize, int missing) {
					if (stackSize == missing) {
						return 4;
					} else if (stackSize == missing * 2) {
						return 3;
					} else if (stackSize < missing) {
						return 2;
					} else {
						return 1;
					}
				}

				@Override
				protected int getMissingAmount(AIHelper h, int currentCount) {
					return -currentCount + slot.amount;
				}
			};
		}
	}

	public static class UnstorePathFinder extends BlockRangeFinder { //changed Marlon.C to public from private
		private final Wishlist list;

		private ChestBlockHandler chestBlockHandler;

		@Override
		public BlockRangeScanner constructScanner(BlockPos playerPosition) { //changed Marlon.C to public from protected
			BlockRangeScanner scanner = super.constructScanner(playerPosition);
			chestBlockHandler = new ChestBlockHandler();
			scanner.addHandler(chestBlockHandler);
			return scanner;
		}	
		
		
		public HashMap<BlockPos, ChestData> getChests() //added Marlon.C
		{
			return this.chestBlockHandler.getChests();
		}	
		
		
		public Wishlist getList() //added Marlon.C
		{
			return this.list;
		}	

		public UnstorePathFinder(Wishlist list) {
			this.list = list;
		}

		@Override
		protected float rateDestination(int distance, int x, int y, int z) {
			ArrayList<ChestData> chests = chestBlockHandler
					.getReachableForPos(new BlockPos(x, y, z));
			if (chests != null) {
				for (ChestData c : chests) {
					if (list.couldUseOneOf(c)) {
						return distance;
					}
				}
			}
			return -1;
		}

		@Override
		protected void addTasksForTarget(BlockPos currentPos) {
			ArrayList<ChestData> chests = chestBlockHandler
					.getReachableForPos(currentPos);
			for (final ChestData c : chests) {
				ItemStack[] inventory = helper.getMinecraft().thePlayer.inventory.mainInventory;
				ArrayList<AITask> tasks = list.getTakeTasks(inventory, c);

				if (!tasks.isEmpty()) {
					addTask(new OpenChestTask(c.getSecondaryPos(), c.getPos()));
					addTask(new WaitTask(5));
					for (AITask t : tasks) {
						addTask(t);
						addTask(new WaitTask(5));
					}
					addTask(new CloseScreenTask());
					addTask(new WaitTask(5));
					break;
				}

			}
		}
	}

	public UnstoreStrategy(Wishlist list) {
		super(new UnstorePathFinder(list), null);
	}

	@Override
	public void searchTasks(AIHelper helper) {
		// If chest open, close it.
		if (helper.getMinecraft().currentScreen instanceof GuiChest) {
			addTask(new CloseScreenTask());
		}
		super.searchTasks(helper);
	}

	@Override
	public String getDescription(AIHelper helper) {
		return "Get items out of chest.";
	}

}
