package marlon.minecraftai.build.reverse.factories;

import marlon.minecraftai.ai.command.BlockWithDataOrDontcare;
import marlon.minecraftai.ai.path.world.BlockSet;
import marlon.minecraftai.build.blockbuild.BuildTask;
import marlon.minecraftai.build.blockbuild.LogBuildTask;
import net.minecraft.util.BlockPos;

public class LogBuildTaskFactory extends AbstractBuildTaskFactory {

	@Override
	protected BuildTask getTaskImpl(BlockPos position,
			BlockWithDataOrDontcare block) {
		return new LogBuildTask(position, block);
	}

	@Override
	public BlockSet getSupportedBlocks() {
		return LogBuildTask.NORMAL_LOGS;
	}

}
