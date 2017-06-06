package marlon.minecraftai.ai.utils;

import marlon.minecraftai.ai.path.world.WorldData;

/**
 * An area that is empty.
 * 
 *
 */
public class BlockAreaEmpty extends BlockArea {
	public static final BlockAreaEmpty INSTANCE = new BlockAreaEmpty();

	private BlockAreaEmpty() {
	}

	@Override
	public boolean contains(WorldData world, int x, int y, int z) {
		return false;
	}
	
	@Override
	public void accept(AreaVisitor v, WorldData world) {
		// nothing to visit.
	}
}
