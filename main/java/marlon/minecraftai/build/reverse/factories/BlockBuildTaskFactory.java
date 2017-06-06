package marlon.minecraftai.build.reverse.factories;

import marlon.minecraftai.ai.command.BlockWithDataOrDontcare;
import marlon.minecraftai.ai.path.world.BlockSet;
import marlon.minecraftai.build.blockbuild.BlockBuildTask;
import marlon.minecraftai.build.blockbuild.BuildTask;
import net.minecraft.util.BlockPos;

public class BlockBuildTaskFactory extends AbstractBuildTaskFactory {
	
	@Override
	public BlockSet getSupportedBlocks() {
		return BlockBuildTask.BLOCKS;
	}
	
	@Override
	public BuildTask getTaskImpl(BlockPos position, BlockWithDataOrDontcare forBlock) {
		return new BlockBuildTask(position, forBlock);
	}
}
