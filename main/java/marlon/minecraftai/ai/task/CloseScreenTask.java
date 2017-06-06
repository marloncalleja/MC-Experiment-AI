package marlon.minecraftai.ai.task;


import marlon.minecraftai.ai.AIHelper;
import marlon.minecraftai.ai.task.AITask;
import marlon.minecraftai.ai.task.TaskOperations;
import net.minecraft.client.gui.GuiScreen;

public class CloseScreenTask extends AITask{
	
	@Override
	public boolean isFinished(AIHelper h) {
		return h.getMinecraft().currentScreen == null;
	}

	@Override
	public void runTick(AIHelper h, TaskOperations o) {
		h.getMinecraft().displayGuiScreen((GuiScreen) null);
	}

}
