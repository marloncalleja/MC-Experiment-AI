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
package marlon.minecraftai.ai.command;

import java.util.List;

import marlon.goap.goalcommand.GetFood;
import marlon.goap.goalcommand.GetFuel;
import marlon.goap.goalcommand.GetWool;
import marlon.minecraftai.ai.scripting.CommandJs;
import marlon.minecraftai.build.commands.CommandBuild;
import marlon.minecraftai.build.commands.CommandClearArea;
import marlon.minecraftai.build.commands.CommandCount;
import marlon.minecraftai.build.commands.CommandExpand;
import marlon.minecraftai.build.commands.CommandListBuilds;
import marlon.minecraftai.build.commands.CommandMove;
import marlon.minecraftai.build.commands.CommandReset;
import marlon.minecraftai.build.commands.CommandReverse;
import marlon.minecraftai.build.commands.CommandScheduleBuild;
import marlon.minecraftai.build.commands.CommandSetPos;
import marlon.minecraftai.build.commands.CommandStepNext;
import marlon.minecraftai.build.commands.CommandStepPlace;
import marlon.minecraftai.build.commands.CommandStepWalk;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import com.google.common.base.Function;

/**
 * Controlls the AI from a chat line.
 * 
 * 
 * 
 */
public class AIChatController {
	private static final CommandRegistry registry = new CommandRegistry();

	private static final int PER_PAGE = 8;

	static {
		
		registerCommand(GetWool.class); //added Marlon.C
		registerCommand(GetFuel.class); //added Marlon.C
		registerCommand(GetFood.class); //added Marlon.C
		
	}

	private AIChatController() {
	}

	private static void registerCommand(Class<?> commandClass) {
		registry.register(commandClass);
	}

	public static void addChatLine(String message) {
		addToChat("[Minebot] " + message);
	}

	public static CommandRegistry getRegistry() {
		return registry;
	}

	private static void addToChat(String string) {
		Minecraft.getMinecraft().thePlayer
				.addChatMessage(new ChatComponentText(string));
	}

	public static <T> void addToChatPaged(String title, int page, List<T> data,
			Function<T, String> convert) {
		AIChatController.addChatLine(title + " " + page + " / "
				+ (int) Math.ceil((float) data.size() / PER_PAGE));
		for (int i = Math.max(0, page - 1) * PER_PAGE; i < Math.min(page
				* PER_PAGE, data.size()); i++) {
			final String line = convert.apply(data.get(i));
			addChatLine(line);
		}
	}

}
