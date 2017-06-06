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
package marlon.minecraftai.ai.task.inventory;

import marlon.minecraftai.ai.AIHelper;
import marlon.minecraftai.ai.task.AITask;
import marlon.minecraftai.ai.task.TaskOperations;
import marlon.minecraftai.ai.task.error.StringTaskError;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;

/**
 * Put items in a currently opened container.
 * 
 *
 */
public abstract class PutItemInContainerTask extends AITask {

	private int slotToPlace = 0;
	private boolean placed = false;
	private boolean isFull;

	@Override
	public boolean isFinished(AIHelper h) {
		final GuiContainer screen = (GuiContainer) h.getMinecraft().currentScreen;
		return screen != null
				&& placed
				&& (slotToPlace < 0 || isFull || !screen.inventorySlots
						.getSlot(slotToPlace).getHasStack());
	}

	@Override
	public void runTick(AIHelper h, TaskOperations o) {
		final GuiContainer screen = (GuiContainer) h.getMinecraft().currentScreen;
		if (screen == null) {
			o.desync(new StringTaskError("Expected container to be open"));
			return;
		}
		slotToPlace = getStackToPut(h);
		placed = true;
		if (slotToPlace < 0) {
			System.out.println("No item to put.");
			o.desync(new StringTaskError("No item to put in that slot."));
		} else {
			System.out.println("Moving from slot: " + slotToPlace);
			Slot slot = screen.inventorySlots.getSlot(slotToPlace);
			int oldContent, newContent = getSlotContentCount(slot);
			do {
				oldContent = newContent;
				h.getMinecraft().playerController.windowClick(
						screen.inventorySlots.windowId, slotToPlace, 0, 1,
						h.getMinecraft().thePlayer);
				newContent = getSlotContentCount(slot);
			} while (newContent != oldContent);
			if (newContent > 0) {
				containerIsFull();
			}
		}
	}

	protected void containerIsFull() {
		isFull = true;
	}

	private int getSlotContentCount(Slot slot) {
		return slot.getHasStack() ? slot.getStack().stackSize : 0;
	}

	protected abstract int getStackToPut(AIHelper h);

}
