package fr.statuettegenerator.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.statuettegenerator.main.skin.SkinType;

public final class FileRegisterer {

	public static BufferedImage getFile(String playerUUID) throws IOException {
		return ImageIO.read(new File(playerUUID + "/" + playerUUID + SkinType.DEFAULT.toString() + ".png"));
	}

	public static File createDataFolder(String dataFolderName) {
		File folder = new File(dataFolderName);
		if (!folder.exists())
			folder.mkdir();
		return folder;
	}

	public static File createFile(String path, String name) {
		File file = new File(createDataFolder(path), name);
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return file;
	}

}
