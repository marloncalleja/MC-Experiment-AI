package marlon.minecraftai.settings;

import marlon.minecraftai.ai.tools.ToolRater;
import marlon.minecraftai.ai.tools.ToolRater.ToolType;

/**
 * This is the root object for our json settings file.
 * 
 *
 */
@MinebotSettingObject
public class MinebotSettingsRoot {
	private PathfindingSettings pathfinding = new PathfindingSettings();
	
	private SaferuleSettings saferules = new SaferuleSettings();

	private MiningSettings mining = new MiningSettings();

	private ToolRater toolRater = ToolRater.createDefaultRater();

	private ToolRater fishingRater = new ToolRater(ToolType.FISHING_ROD);
	
	public PathfindingSettings getPathfinding() {
		return pathfinding;
	}

	public MiningSettings getMining() {
		return mining ;
	}

	public ToolRater getToolRater() {
		return toolRater ;
	}

	public ToolRater getFishingRater() {
		return fishingRater;
	}
	
	public SaferuleSettings getSaferules() {
		return saferules;
	}
}
