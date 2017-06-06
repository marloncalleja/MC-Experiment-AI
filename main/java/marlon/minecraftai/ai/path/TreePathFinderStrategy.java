package marlon.minecraftai.ai.path;

import marlon.minecraftai.ai.AIHelper;
import marlon.minecraftai.ai.strategy.PathFinderStrategy;

public class TreePathFinderStrategy extends PathFinderStrategy {

	private TreePathFinder treeFinder;

	public TreePathFinderStrategy(TreePathFinder pathFinder,
			String description) {
		super(pathFinder, description);
		treeFinder = pathFinder;
	}
	
	@Override
	public void searchTasks(AIHelper helper) {
		super.searchTasks(helper);
	}
} //added Marlon.C
