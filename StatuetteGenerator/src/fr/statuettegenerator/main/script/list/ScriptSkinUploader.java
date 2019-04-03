package fr.statuettegenerator.main.script.list;

import java.io.File;
import java.util.UUID;

import fr.cactuscata.smartapplication.msg.Message;
import fr.statuettegenerator.apiuploader.SkinChangeParams;
import fr.statuettegenerator.apiuploader.SkinChanger;
import fr.statuettegenerator.apiuploader.SkinModel;
import fr.statuettegenerator.main.script.ex.ScriptCommandException;
import fr.statuettegenerator.main.script.ex.ScriptValidator;
import fr.statuettegenerator.main.skin.SkinCuboid;
import fr.statuettegenerator.utils.FileRegisterer;

public final class ScriptSkinUploader implements IScript {

	@Override
	public void startScript() throws ScriptCommandException {

		//UUID uuid = ScriptValidator.getUUID();
		UUID uuid = UUID.fromString("9b8d31d5-420c-4f0c-80f0-de834b737a99");
		SkinCuboid skinCuboid = ScriptValidator.getByName(SkinCuboid.class,
				Message.getMessageInstance().getInput("Veuillez séléctionner une partie de skin."));
		File file = FileRegisterer.createFile(uuid.toString(), skinCuboid.toString() + ".png");
		SkinChanger.changeSkin(new SkinChangeParams("compte1.1999@gmail.com", "[$C6LPWli72", file, SkinModel.STEVE,
				"52.84.213.104", 25565));

	}

}
