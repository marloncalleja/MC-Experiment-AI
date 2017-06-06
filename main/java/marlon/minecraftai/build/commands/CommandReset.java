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
package marlon.minecraftai.build.commands;

import marlon.minecraftai.ai.AIHelper;
import marlon.minecraftai.ai.command.AICommand;
import marlon.minecraftai.ai.command.AICommandInvocation;
import marlon.minecraftai.ai.command.AICommandParameter;
import marlon.minecraftai.ai.command.ParameterType;
import marlon.minecraftai.ai.strategy.AIStrategy;

@AICommand(helpText = "Reset the internal build queue.", name = "minebuild")
public class CommandReset {

	private static final class ResetStrategy extends AIStrategy {
		@Override
		public boolean checkShouldTakeOver(AIHelper helper) {
			return helper.buildManager.peekNextTask() != null;
		}

		@Override
		protected TickResult onGameTick(AIHelper helper) {
			while (helper.buildManager.peekNextTask() != null) {
				helper.buildManager.popNextTask();
			}
			return TickResult.NO_MORE_WORK;
		}

		@Override
		public String getDescription(AIHelper helper) {
			return "Clearing build list.";
		}
	}

	@AICommandInvocation()
	public static AIStrategy run(
			AIHelper helper,
			@AICommandParameter(type = ParameterType.FIXED, fixedName = "reset", description = "") String nameArg) {
		return new ResetStrategy();
	}
}
