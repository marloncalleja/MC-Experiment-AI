package marlon.minecraftai.ai.strategy;

import marlon.minecraftai.ai.AIHelper;

/**
 * Same as the run once strategy but this strategy at least takes one tick to execute.
 *  
 *
 */
public abstract class RunOneTickStrategy extends RunOnceStrategy {

	protected TickResult doSingleRun(AIHelper helper) {
		this.singleRun(helper);
		return TickResult.TICK_HANDLED;
	}
}
