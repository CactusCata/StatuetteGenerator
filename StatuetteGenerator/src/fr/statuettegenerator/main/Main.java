package fr.statuettegenerator.main;

import fr.cactuscata.smartapplication.SmartApplicationBox;
import fr.cactuscata.smartapplication.msg.MessageType;
import fr.statuettegenerator.main.script.ex.ScriptException;
import fr.statuettegenerator.main.script.list.ScriptSkinGenerator;

public class Main {

	public static void main(String[] args) {
		
		new SmartApplicationBox("Statuette Generator", MessageType.CONSOLE);
		
		new Quest
		
		try {
			new ScriptSkinGenerator().startScript();
			//new ScriptSkinBuilder().startScript();
//			ScriptType.getScriptByName(Question.getAnswer("Veuillez choisir une action entre %s !",
//					StringUtil.join(ScriptType.values(), ", "))).startScript();
		} catch (ScriptException e) {
			e.sendMessage();
		}

	}

}
