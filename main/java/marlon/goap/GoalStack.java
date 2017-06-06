package marlon.goap;

import java.util.HashMap;
import java.util.Queue;

import marlon.goap.actions.Action;
import marlon.minecraftai.ai.command.SafeStrategyRule;
import marlon.minecraftai.ai.strategy.AIStrategy;
import marlon.minecraftai.ai.strategy.StackStrategy;
import marlon.minecraftai.ai.strategy.StrategyStack;

public class GoalStack {
	
	public static HashMap<String,Boolean> goal;
	
	public AIStrategy makeSafe(AIStrategy strategy, SafeStrategyRule safeRule) {  //Load AI strategies on stack	
		
	    final StrategyStack stack = new StrategyStack();		
		Queue<Action> stackActions = new Agent(goal).loadData();		
		
		for (Action a : stackActions) {				
			
			for(AIStrategy s : a.strategy())
			{
				stack.addStrategy(s);	
			}			
		}				
		
		return new StackStrategy(stack);	
	}

}
