package fr.statuettegenerator.main.script.list;

import fr.cactuscata.smartapplication.msg.Message;
import fr.statuettegenerator.main.script.ex.ScriptCommandException;

public class ScriptSkinGenerator implements IScript {

	/**
	 * TODO:
	 * - Pouvoir choisir le type de taille de head (hand-small/hand-tall/head-small/head-tall)
	 * - Pouvoir choisir la direction
	 */
	
	@Override
	public void startScript() throws ScriptCommandException {
		
		Message.setInfiniteReturnIfIsNotGood(true);
		float x = Message.getMessageInstance().getFloat("Veuillez s�l�ctionner les coordon�es en X:");
		float y = Message.getMessageInstance().getFloat("Veuillez s�l�ctionner les coordon�es en Y:");
		float z = Message.getMessageInstance().getFloat("Veuillez s�l�ctionner les coordon�es en Z:");
		Message.setInfiniteReturnIfIsNotGood(false);
		
	}

}
