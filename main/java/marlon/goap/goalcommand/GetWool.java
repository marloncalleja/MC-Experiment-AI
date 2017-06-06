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

@AICommand(helpText = "get wool", name = "minebot")
public class GetWool {	

		@AICommandInvocation(safeRule = SafeStrategyRule.DEFEND)
		public static AIStrategy run(AIHelper helper, @AICommandParameter(type = ParameterType.FIXED, fixedName = "getwool", description = "") String nameArg) {
						
			HashMap<String,Boolean> setGoal = new HashMap<String,Boolean>(); 
			setGoal.put("hasWool", true);
			
			GoalStack.goal = setGoal;		
			
			return new DoNotSuffocateStrategy();
		}
}

	


