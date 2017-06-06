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
package marlon.minecraftai.build;

import marlon.minecraftai.ai.AIHelper;
import marlon.minecraftai.ai.task.AITask;
import marlon.minecraftai.ai.task.TaskOperations;

/**
 * This task lets you skip one/many build tasks.
 * 
 *
 */
public final class NextTaskTask extends AITask {
	private int tasksToSkip;

	public NextTaskTask() {
		this(1);
	}

	public NextTaskTask(int tasksToSkip) {
		this.tasksToSkip = tasksToSkip;
	}

	@Override
	public void runTick(AIHelper h, TaskOperations o) {
		while (tasksToSkip > 0) {
			if (h.buildManager.peekNextTask() != null) {
				h.buildManager.popNextTask();
			}
			tasksToSkip--;
		}
	}

	@Override
	public boolean isFinished(AIHelper h) {
		return tasksToSkip <= 0;
	}

	@Override
	public String toString() {
		return "NextTaskTask [tasksToSkip=" + tasksToSkip + "]";
	}

}