package marlon.minecraftai.ai.task;

import marlon.minecraftai.ai.AIHelper;
import marlon.minecraftai.ai.path.world.BlockSet;
import marlon.minecraftai.ai.path.world.BlockSets;
import marlon.minecraftai.ai.path.world.WorldData;
import marlon.minecraftai.ai.utils.BlockArea;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

/**
 * This task attempts to destroy any log in a given area.
 * 
 *
 */
public class DestroyLogInRange extends DestroyInRangeTask {

	private static final BlockSet LEAVES_OR_LOGS = BlockSets.LEAVES.unionWith(BlockSets.LOGS).unionWith(new BlockSet(Blocks.vine));

	public DestroyLogInRange(BlockArea range) {
		super(range);
	}

	@Override
	protected boolean noDestructionRequired(WorldData world, int x, int y, int z) {
		if (super.noDestructionRequired(world, x, y, z)) {
			return true;
		} else {
			return !BlockSets.LOGS.isAt(world, x, y, z);
		}
	}

	protected boolean isAcceptedFacingPos(AIHelper h, BlockPos n, BlockPos pos) {
		return (LEAVES_OR_LOGS.isAt(
						h.getWorld(), pos) && BlockSets.LOGS.isAt(h.getWorld(), n));
	}
}
