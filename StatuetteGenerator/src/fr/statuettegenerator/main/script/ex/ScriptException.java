package fr.statuettegenerator.main.script.ex;

import fr.cactuscata.smartapplication.msg.Message;

public abstract class ScriptException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	protected ScriptException(String messageFormat, Object... objects) {
		super(String.format(messageFormat, objects));
	}

	public final void sendMessage() {
		Message.getMessageInstance().sendMessage(super.getMessage());
	}

}
