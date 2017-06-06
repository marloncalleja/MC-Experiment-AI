package marlon.minecraftai.ai.strategy;

import marlon.minecraftai.ai.AIHelper;
import net.minecraft.util.Vec3;

/**
 * Look at the given position.
 * 
 *
 */
public class LookAtStrategy extends AIStrategy{

	private Vec3 lookAt;

	public LookAtStrategy(Vec3 lookAt) {
		this.lookAt = lookAt;
	}

	@Override
	protected TickResult onGameTick(AIHelper helper) {
		if (helper.isFacing(lookAt)) {
			return TickResult.NO_MORE_WORK;
		} else {
			helper.face(lookAt);
			return TickResult.TICK_HANDLED;
		}
	}

	@Override
	public String toString() {
		return "LookAtStrategy [lookAt=" + lookAt + "]";
	}
	
	
}
