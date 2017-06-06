package marlon.minecraftai.ai.task;

import marlon.minecraftai.ai.AIHelper;

public class ConditionalWaitTask extends WaitTask {
	public interface WaitCondition {
		public boolean shouldWait();
	}
	
	private WaitCondition condition;
	public ConditionalWaitTask(int time, WaitCondition condition) {
		super(time);
		this.condition = condition;
	}
	
	@Override
	public boolean isFinished(AIHelper h) {
		return !condition.shouldWait() || super.isFinished(h);
	}
}
