package fr.statuettegenerator.main.script.list;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import fr.cactuscata.smartapplication.msg.Message;
import fr.statuettegenerator.main.script.ex.ScriptException;
import fr.statuettegenerator.main.skin.SkinCuboid;
import fr.statuettegenerator.utils.FileRegisterer;
import fr.statuettegenerator.utils.Point;

public final class ScriptSkinBuilder implements IScript {
	
	private int error;
	private final List<SkinCuboid> parts = new ArrayList<>();
	
	@Override
	public void startScript() throws ScriptException {

		try {
		
			// final UUID playerUUID = UUID.fromString(Question.getAnswer("Veuillez
			// séléctionner une uuid"));
			final UUID playerUUID = UUID.fromString("9b8d31d5-420c-4f0c-80f0-de834b737a99");
			new File(playerUUID.toString()).mkdir();
			BufferedImage bi = FileRegisterer.getFile(playerUUID.toString());


			// Fait passer tous les armorstands un à un (26).
			for (SkinCuboid skinCub : SkinCuboid.values()) {
				ImageIO.write(build(skinCub, bi), "png", new File(playerUUID + "/" + skinCub.toString() + ".png"));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		Message.getMessageInstance().sendMessage("Errors %d%n%s", this.error, this.parts);
	}

	private BufferedImage build(SkinCuboid skinCuboid, BufferedImage bi) {
		
		Message.getMessageInstance().sendMessage("-----enter-----");
		Message.getMessageInstance().sendMessage("skincuboid " + skinCuboid);
		
		// Translation pour avoir accès aux coordonées ajoutées pour la couche supérieur.
		SkinCuboid.SkinOverlayDeplacement translation = skinCuboid.getTranslation();
		
		// Valeurs des translations pour couches supérieurs.
		int xTranslationOverlay = translation.getXAdded();
		int yTranslationOverlay = translation.getYAdded();
		
		Message.getMessageInstance().sendMessage("xTranslationOverlay: " + xTranslationOverlay);
		Message.getMessageInstance().sendMessage("yTranslationOverlay: " + yTranslationOverlay);

		// 64 x 64 puisque c'est la taille d'un skin
		BufferedImage image = new BufferedImage(64, 64, bi.getType());

		// Passe chaque point de l'armorstands head un à un pour pouvoir le dessiner
		for (int i = 0; i < 6 /* Toujours 6 (une vrai head en a 6, comme un dé) */; i++) {
			
			Point pointArmorStand = skinCuboid.getPoints()[i];
			if (pointArmorStand == null)
				continue;
			
			Message.getMessageInstance().sendMessage("pointArmorStand " + pointArmorStand);
			
			int pointArmorStandX = pointArmorStand.getX();
			int pointArmorStandY = pointArmorStand.getY();
			
			// Pour redessiner dans le nouveau skin
			Point pointHead = HeadCoordonates.values()[i].point;
			
			int pointHeadX = pointHead.getX();
			int pointHeadY = pointHead.getY();
			
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 4; y++) {
					
					/**
					 * Dessiner un carré de la même couleur (2 x 2) 
					 * Couche simple
					 */
					int color = bi.getRGB(pointArmorStandX + x, pointArmorStandY + y);
					image.setRGB(pointHeadX + x * 2, pointHeadY + y * 2, color);
					image.setRGB(pointHeadX + 1 + x * 2, pointHeadY + y * 2, color);
					image.setRGB(pointHeadX + x * 2, pointHeadY + 1 + y * 2, color);
					image.setRGB(pointHeadX + 1 + x * 2, pointHeadY + 1 + y * 2, color);

					/**
					 * Dessiner un carré de la même couleur (2 x 2)
					 * Couche supérieur
					 */
					try {
						color = bi.getRGB(pointArmorStandX + x + xTranslationOverlay, pointArmorStandY + y + yTranslationOverlay);
					} catch (ArrayIndexOutOfBoundsException e) {
						Message.getMessageInstance().sendMessage("getColor = x get : %d | y get : %d", pointArmorStandX + x + xTranslationOverlay, pointArmorStandY + y + yTranslationOverlay);
						error++;
						if(!this.parts.contains(skinCuboid))
							this.parts.add(skinCuboid);
					}
					
					try {
						image.setRGB(pointHeadX + x * 2 + 32, pointHeadY + y * 2, color);
						image.setRGB(pointHeadX + 1 + x * 2 + 32, pointHeadY + y * 2, color);
						image.setRGB(pointHeadX + x * 2 + 32, pointHeadY + 1 + y * 2, color);
						image.setRGB(pointHeadX + 1 + x * 2 + 32, pointHeadY + 1 + y * 2, color);
					} catch (ArrayIndexOutOfBoundsException e) {
						Message.getMessageInstance().sendMessage("setColor = x get : %d | y get : %d", pointArmorStandX + x + xTranslationOverlay, pointArmorStandY + y + yTranslationOverlay);
					}

				}
			}
		}
		Message.getMessageInstance().sendMessage("-----ended-----");

		return image;
	}

	/**
	 * 
	 * Utilisée pour redessiner via les coordonées réeles d'une head normale.
	 * 
	 * @author CactusCata
	 *
	 */
	private enum HeadCoordonates {

		TOP(new Point(8, 0)),
		BOT(new Point(16, 8)),
		LEFT(new Point(0, 8)),
		FRONT_OF(new Point(8, 8)),
		RIGHT(new Point(16, 8)),
		BEHIND(new Point(24, 8));

		private final Point point;

		private HeadCoordonates(Point point) {
			this.point = point;
		}

	}

}
