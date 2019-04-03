package fr.statuettegenerator.main.script.list;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import fr.statuettegenerator.main.script.ex.ScriptException;
import fr.statuettegenerator.main.script.ex.ScriptValidator;
import fr.statuettegenerator.main.skin.SkinType;
import fr.statuettegenerator.utils.FileRegisterer;

public class ScriptSkinDownloader implements IScript {

	@Override
	public void startScript() throws ScriptException {

		UUID uuid = ScriptValidator.getUUID();
		ScriptValidator.isNull(uuid, "L'UUID ne peut pas être nulle.");
		try {
			ImageIO.write(
					ImageIO.read(ScriptValidator.getURL(new JsonParser()
							.parse(new String(Base64.getDecoder().decode(new JsonParser()
									.parse(new InputStreamReader(ScriptValidator
											.getURL("https://sessionserver.mojang.com/session/minecraft/profile/"
													+ uuid.toString().replace("-", ""))
											.openStream()))
									.getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject()
									.get("value").getAsString()), "UTF-8"))
							.getAsJsonObject().getAsJsonObject("textures").getAsJsonObject("SKIN").get("url")
							.getAsString())),
					"png", FileRegisterer.createFile(uuid.toString(), uuid.toString() + SkinType.DEFAULT + ".png"));
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			e.printStackTrace();
		}

	}

}
