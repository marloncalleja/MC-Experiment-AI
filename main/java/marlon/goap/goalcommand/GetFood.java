package marlon.goap.goalcommand;

import java.util.HashMap;

import marlon.goap.GoalStack;
import marlon.minecraftai.ai.AIHelper;
import marlon.minecraftai.ai.command.AICommand;
import marlon.minecraftai.ai.command.AICommandInvocation;
import marlon.minecraftai.ai.command.AICommandParameter;
import marlon.minecraftai.ai.command.ParameterType;
import marlon.minecraftai.ai.command.SafeStrategyRule;
import marlon.minecraftai.ai.strategy.AIStrategy;
import marlon.minecraftai.ai.strategy.DoNotSuffocateStrategy;

@AICommand(helpText = "get food", name = "minebot")
public class GetFood {
	
	@AICommandInvocation(safeRule = SafeStrategyRule.DEFEND)
	public static AIStrategy run(AIHelper helper, @AICommandParameter(type = ParameterType.FIXED, fixedName = "getfood", description = "") String nameArg) {
					
		HashMap<String,Boolean> setGoal = new HashMap<String,Boolean>(); 
		setGoal.put("hasFood", true);
		
		GoalStack.goal = setGoal;		
		
		return new DoNotSuffocateStrategy();
	}

}
