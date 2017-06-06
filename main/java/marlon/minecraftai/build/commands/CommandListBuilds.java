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
import marlon.minecraftai.ai.command.AIChatController;
import marlon.minecraftai.ai.command.AICommand;
import marlon.minecraftai.ai.command.AICommandInvocation;
import marlon.minecraftai.ai.command.AICommandParameter;
import marlon.minecraftai.ai.command.ParameterType;
import marlon.minecraftai.ai.strategy.AIStrategy;
import marlon.minecraftai.build.blockbuild.BuildTask;

import com.google.common.base.Function;

@AICommand(helpText = "List all scheduled commands.", name = "minebuild")
public class CommandListBuilds {

	@AICommandInvocation()
	public static AIStrategy run(
			AIHelper helper,
			@AICommandParameter(type = ParameterType.FIXED, fixedName = "list", description = "") String nameArg2) {
		return run(helper, nameArg2, 1);
	}

	@AICommandInvocation()
	public static AIStrategy run(
			AIHelper helper,
			@AICommandParameter(type = ParameterType.FIXED, fixedName = "list", description = "") String nameArg2,
			@AICommandParameter(type = ParameterType.NUMBER, description = "page") int page) {
		AIChatController.addToChatPaged("scheduled builds", page,
				helper.buildManager.getScheduled(),
				new Function<BuildTask, String>() {
					@Override
					public String apply(BuildTask task) {
						return task + "";
					}
				});
		return null;
	}

}
