package marlon.minecraftai.build.reverse.factories;

import java.util.ArrayList;

import marlon.minecraftai.ai.command.BlockWithDataOrDontcare;
import marlon.minecraftai.ai.path.world.BlockSet;
import marlon.minecraftai.ai.path.world.BlockSets;
import marlon.minecraftai.ai.path.world.WorldData;
import marlon.minecraftai.build.blockbuild.BuildTask;
import marlon.minecraftai.build.reverse.TaskDescription;
import marlon.minecraftai.build.reverse.UnsupportedBlockException;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class BuildTaskFactories {

	public static final BlockSet IGNORED_ON_RECONSTRUCT = BlockSets.AIR
			.unionWith(new BlockSet(Blocks.barrier));

	private static final ArrayList<BuildTaskFactory> factories = new ArrayList<BuildTaskFactory>();

	static {
		register(new BlockBuildTaskFactory());
		register(new LogBuildTaskFactory());
		register(new SlabBuildTaskFactory());
	}
	
	public static void register(BuildTaskFactory factory) {
		factories.add(factory);
	}
	
	public static BuildTask getTask(BlockPos position, BlockWithDataOrDontcare block) {
		for (BuildTaskFactory f : factories) {
			BuildTask res = f.getTask(position, block);
			if (res != null) {
				return res;
			}
		}

		throw new IllegalArgumentException("Cannot handle: " + block);
	}

	public static TaskDescription getTaskFor(WorldData world, BlockPos position)
			throws UnsupportedBlockException {
		if (IGNORED_ON_RECONSTRUCT.isAt(world, position)) {
			return null;
		}

		for (BuildTaskFactory f : factories) {
			TaskDescription res = f.getTaskDescription(world, position);
			if (res != null) {
				return res;
			}
		}

		throw new UnsupportedBlockException(world, position,
				"No handler found for that block.");
	}

}
