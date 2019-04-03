package fr.statuettegenerator.main.script.list;

import fr.statuettegenerator.main.script.ex.ScriptCommandException;
import fr.statuettegenerator.main.script.ex.ScriptException;
import fr.statuettegenerator.main.script.ex.ScriptValidator;

public enum ScriptType {

	BUILDER(new ScriptSkinBuilder(), "build"),
	DOWNLOADER(new ScriptSkinDownloader(), "download"),
	UPLOADER(new ScriptSkinUploader(), "upload"),
	GENERATE(new ScriptSkinGenerator(), "generate");

	private final IScript script;
	private final String scriptName;

	private ScriptType(IScript script, String scriptName) {
		this.script = script;
		this.scriptName = scriptName;
	}

	@Override
	public final String toString() {
		return this.scriptName;
	}

	public static IScript getScriptByName(String name) throws ScriptTypeNotFoundException {
		try {
			return ScriptValidator.getByName(ScriptType.class, name).script;
		} catch (ScriptCommandException e) {
			throw new ScriptTypeNotFoundException(String.format("Le script %s n'a pas été trouvé !", name));
		}
	}

	public static class ScriptTypeNotFoundException extends ScriptException {

		private static final long serialVersionUID = 1L;

		public ScriptTypeNotFoundException(String message) {
			super(message);
		}

	}

}
