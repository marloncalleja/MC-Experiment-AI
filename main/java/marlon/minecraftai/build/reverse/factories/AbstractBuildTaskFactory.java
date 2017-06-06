package marlon.minecraftai.build.reverse.factories;

import marlon.minecraftai.ai.command.BlockWithData;
import marlon.minecraftai.ai.command.BlockWithDataOrDontcare;
import marlon.minecraftai.ai.path.world.BlockSet;
import marlon.minecraftai.ai.path.world.WorldData;
import marlon.minecraftai.build.blockbuild.BuildTask;
import marlon.minecraftai.build.reverse.TaskDescription;
import marlon.minecraftai.build.reverse.UnsupportedBlockException;
import net.minecraft.util.BlockPos;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractBuildTaskFactory implements BuildTaskFactory {

	@Override
	public TaskDescription getTaskDescription(WorldData world, BlockPos position)
			throws UnsupportedBlockException {
		if (getSupportedBlocks().isAt(world, position)) {
			BlockWithData block = world.getBlock(position);
			BuildTask task = getTaskImpl(position, block);
			try {
				Object[] args = task.getCommandArguments();
				return new TaskDescription(StringUtils.join(args, " "),
						task.getStandablePlaces());
			} catch (UnsupportedOperationException uoe) {
				throw new UnsupportedBlockException(world, position,
						"Task could not be converted: " + task);
			}
		}
		return null;
	}
	
	@Override
	public BuildTask getTask(BlockPos position, BlockWithDataOrDontcare forBlock) {
		if (getSupportedBlocks().contains(forBlock)) {
			return getTaskImpl(position, forBlock);
		} else {
			return null;
		}
	}

	protected abstract BuildTask getTaskImpl(BlockPos position, BlockWithDataOrDontcare block);

	public abstract BlockSet getSupportedBlocks();

}
