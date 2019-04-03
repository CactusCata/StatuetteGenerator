package fr.statuettegenerator.main.script.ex;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import fr.cactuscata.smartapplication.msg.Message;

public class ScriptValidator {

	public static UUID getUUID() throws ScriptCommandException {
		final String uuid = getAnswer("Veuillez séléctionner une UUID:");
		try {
			return UUID.fromString(uuid);
		} catch (IllegalArgumentException e) {
			throw new ScriptCommandException("L'UUID %s n'est pas valide !", uuid);
		}
	}

	public static String getAnswer(String message) throws ScriptCommandException {
		final String answer = Message.getMessageInstance().getInput(message);
		if (answer == null || answer.isEmpty())
			throw new ScriptCommandException("Veuillez donner une chaîne de caractère valide !");
		return answer;
	}

	public static void isNull(Object object, String message) throws ScriptCommandException {
		if (object == null)
			throw new ScriptCommandException(message);
	}

	public static URL getURL(String string) throws ScriptCommandException {
		try {
			return new URL(string);
		} catch (MalformedURLException e) {
			throw new ScriptCommandException("L'URL %s n'est pas valide !");
		}
	}

	public static <T extends Enum<T>> T getByName(Class<T> enumeration, String name) throws ScriptCommandException {
		for (T t : enumeration.getEnumConstants()) {
			if (name.equalsIgnoreCase(t.toString()))
				return t;
		}
		throw new ScriptCommandException("Le type %s n'existe pas !", name);
	}

}
