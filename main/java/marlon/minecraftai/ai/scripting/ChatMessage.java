package marlon.minecraftai.ai.scripting;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import marlon.minecraftai.ai.net.MinebotNetHandler.PersistentChat;

public class ChatMessage {

	public final Object time;
	public final String text;
	public final String textFormatted;
	public final boolean isChat;

	public ChatMessage(PersistentChat m, ScriptEngine engine)
			throws ScriptException {
		time = engine.eval("new Date(" + m.getTime() + ")");
		text = m.getMessage().getUnformattedText();
		textFormatted = m.getMessage().getFormattedText();
		isChat = m.isChat();
	}

	@Override
	public String toString() {
		return "ChatMessage [text=" + text + "]";
	}
	
}
