package marlon.minecraftai.ai.path;

import marlon.minecraftai.ai.command.BlockWithData;
import marlon.minecraftai.ai.command.BlockWithDontcare;
import marlon.minecraftai.ai.path.world.BlockFloatMap;
import marlon.minecraftai.ai.path.world.BlockSet;
import net.minecraft.util.EnumFacing;

public class MineCoalPathFinder extends MinePathfinder {
	
	private final BlockSet blocks;
	
	public MineCoalPathFinder(EnumFacing preferedDirection,
			int preferedLayer) {
		super(preferedDirection, preferedLayer);	
		this.blocks = new BlockWithDontcare(16).toBlockSet(); //ID for Coal
	}
	
	@Override
	protected BlockFloatMap getFactorProvider() {
		BlockFloatMap map = new BlockFloatMap();
		for (BlockWithData block : blocks) {
			map.set(block, 1);
		}
		map.setDefault(0);
		return map;
	}
	
	@Override
	protected BlockFloatMap getPointsProvider() {
		BlockFloatMap map = new BlockFloatMap();
		map.setDefault(0);
		return map;
	}	

} //added Marlon.C
