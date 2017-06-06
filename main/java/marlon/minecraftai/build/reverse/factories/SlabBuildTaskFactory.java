package marlon.minecraftai.build.reverse.factories;

import marlon.minecraftai.ai.command.BlockWithDataOrDontcare;
import marlon.minecraftai.ai.path.world.BlockSet;
import marlon.minecraftai.build.blockbuild.BuildTask;
import marlon.minecraftai.build.blockbuild.SlabBuildTask;
import net.minecraft.util.BlockPos;

public class SlabBuildTaskFactory extends AbstractBuildTaskFactory {

	@Override
	protected BuildTask getTaskImpl(BlockPos position,
			BlockWithDataOrDontcare block) {
		return new SlabBuildTask(position, block);
	}

	@Override
	public BlockSet getSupportedBlocks() {
		return SlabBuildTask.BLOCKS;
	}
}
