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
import marlon.minecraftai.ai.strategy.RunOnceStrategy;

@AICommand(helpText = "Select the next task as active.", name = "minebuild")
public class CommandStepNext {

	@AICommandInvocation()
	public static AIStrategy run(
			AIHelper helper,
			@AICommandParameter(type = ParameterType.FIXED, fixedName = "step", description = "") String nameArg2,
			@AICommandParameter(type = ParameterType.FIXED, fixedName = "next", description = "") String nameArg3) {
		return new RunOnceStrategy() {
			@Override
			protected void singleRun(AIHelper helper) {
				if (helper.buildManager.peekNextTask() != null) {
					helper.buildManager.popNextTask();
				}
			}
		};
	}
}
