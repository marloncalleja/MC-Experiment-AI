package marlon.minecraftai.build.commands;

import marlon.minecraftai.ai.AIHelper;
import marlon.minecraftai.ai.command.AIChatController;
import marlon.minecraftai.ai.command.AICommand;
import marlon.minecraftai.ai.command.AICommandInvocation;
import marlon.minecraftai.ai.command.AICommandParameter;
import marlon.minecraftai.ai.command.ParameterType;
import marlon.minecraftai.ai.path.world.Pos;
import marlon.minecraftai.ai.strategy.AIStrategy;
import marlon.minecraftai.ai.strategy.RunOnceStrategy;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;

@AICommand(helpText = "Expand the selected region.", name = "minebuild")
public class CommandExpand {
	private static final class ExpandStrategy extends RunOnceStrategy {
		private final EnumFacing direction;
		private final int amount;

		public ExpandStrategy(int amount, EnumFacing direction) {
			this.amount = amount;
			this.direction = direction;
		}

		@Override
		protected void singleRun(AIHelper helper) {
			BlockPos pos1 = helper.getPos1();
			BlockPos pos2 = helper.getPos2();
			if (pos1 == null && pos2 == null) {
				AIChatController.addChatLine("Please set positions first.");
				return;
			}
			if (pos1 == null) {
				pos1 = pos2;
			} else if (pos2 == null) {
				pos2 = pos1;
			}

			Vec3i dir = direction.getDirectionVec();

			boolean usePos2;
			if (dir.getX() != 0) {
				usePos2 = (pos1.getX() < pos2.getX() == dir.getX() > 0);
			} else if (dir.getY() != 0) {
				usePos2 = (pos1.getY() < pos2.getY() == dir.getY() > 0);
			} else {
				usePos2 = (pos1.getZ() < pos2.getZ() == dir.getZ() > 0);
			}
			BlockPos old;
			if (usePos2) {
				old = pos2;
			} else {
				old = pos1;
			}
			BlockPos newPos = old.offset(direction, amount);
			helper.setPosition(newPos, usePos2);
			AIChatController.addChatLine("Set position " + (usePos2 ? 2 : 1)
					+ " from " + Pos.niceString(old) + " to " + Pos.niceString(newPos));
		}
	}

	@AICommandInvocation()
	public static AIStrategy run(
			AIHelper helper,
			@AICommandParameter(type = ParameterType.FIXED, fixedName = "expand", description = "") String nameArg,
			@AICommandParameter(type = ParameterType.NUMBER, description = "How much", optional = true) Integer amount,
			@AICommandParameter(type = ParameterType.ENUM, description = "Direction", optional = true) EnumFacing direction) {
		if (direction == null) {
			System.out.println("Pitch: "
					+ helper.getMinecraft().thePlayer.rotationPitch);
			direction = helper.getLookDirection();
		}

		return new ExpandStrategy(amount == null ? 1 : amount, direction);
	}

}
