package fr.statuettegenerator.main.skin;

import fr.statuettegenerator.utils.Point;

/**
 * Les 26 armorstands (chaque petite tête)
 * 
 * @author Cactuscata
 *
 */

public enum SkinCuboid {

	HEAD_TOP_LEFT_FRONT(SkinCuboid.SkinOverlayDeplacement.HEAD, new Point(8, 4), null, new Point(4, 8), new Point(8, 8), null, null),
	HEAD_TOP_RIGHT_FRONT(SkinCuboid.SkinOverlayDeplacement.HEAD, new Point(12, 4), null, null, new Point(12, 8), new Point(16, 8), null),
	HEAD_BOT_LEFT_FRONT(SkinCuboid.SkinOverlayDeplacement.HEAD, null, new Point(16, 4), new Point(4, 12), new Point(8, 12), null, null),
	HEAD_BOT_RIGHT_FRONT(SkinCuboid.SkinOverlayDeplacement.HEAD, null, new Point(20, 4), null, new Point(12, 12), new Point(16, 12), null),

	HEAD_TOP_LEFT_BEHIND(SkinCuboid.SkinOverlayDeplacement.HEAD, new Point(8, 0), null, new Point(0, 8), null, null, new Point(28, 8)),
	HEAD_TOP_RIGHT_BEHIND(SkinCuboid.SkinOverlayDeplacement.HEAD, new Point(12, 0), null, null, null, new Point(20, 8), new Point(24, 8)),
	HEAD_BOT_LEFT_BEHIND(SkinCuboid.SkinOverlayDeplacement.HEAD, null, new Point(16, 0), new Point(0, 12), null, null, new Point(28, 12)),
	HEAD_BOT_RIGHT_BEHIND(SkinCuboid.SkinOverlayDeplacement.HEAD, null, new Point(20, 0), null, null, new Point(20, 12), new Point(24, 12)),

	HAND_LEFT_TOP(SkinCuboid.SkinOverlayDeplacement.LEFT_HAND, new Point(44, 16), null, new Point(40, 20), new Point(44, 20), null, new Point(52, 20)),
	HAND_LEFT_MID(SkinCuboid.SkinOverlayDeplacement.LEFT_HAND, null, null, new Point(40, 24), new Point(44, 24), null, new Point(52, 24)),
	HAND_LEFT_BOT(SkinCuboid.SkinOverlayDeplacement.LEFT_HAND, null, new Point(48, 16), new Point(40, 28), new Point(44, 28), null, new Point(52, 28)),

	HAND_RIGHT_TOP(SkinCuboid.SkinOverlayDeplacement.RIGHT_HAND, new Point(36, 48), null, null, new Point(36, 52), new Point(40, 52), new Point(44, 52)),
	HAND_RIGHT_MID(SkinCuboid.SkinOverlayDeplacement.RIGHT_HAND, null, null, null, new Point(36, 56), new Point(40, 56), new Point(44, 56)),
	HAND_RIGHT_BOT(SkinCuboid.SkinOverlayDeplacement.RIGHT_HAND, null, new Point(40, 48), null, new Point(36, 60), new Point(40, 60), new Point(44, 60)),

	CHEST_LEFT_TOP(SkinCuboid.SkinOverlayDeplacement.CHEST, new Point(20, 16), null, null, new Point(20, 20), null, new Point(32, 20)),
	CHEST_LEFT_MID(SkinCuboid.SkinOverlayDeplacement.CHEST, null, null, null, new Point(20, 24), null, new Point(32, 24)),
	CHEST_LEFT_BOT(SkinCuboid.SkinOverlayDeplacement.CHEST, null, null, null, new Point(20, 28), null, new Point(32, 28)),

	CHEST_RIGHT_TOP(SkinCuboid.SkinOverlayDeplacement.CHEST, new Point(24, 16), null, null, new Point(24, 20), null, new Point(36, 20)),
	CHEST_RIGHT_MID(SkinCuboid.SkinOverlayDeplacement.CHEST, null, null, null, new Point(24, 24), null, new Point(36, 24)),
	CHEST_RIGHT_BOT(SkinCuboid.SkinOverlayDeplacement.CHEST, null, null, null, new Point(24, 28), null, new Point(36, 28)),

	LEGS_LEFT_TOP(SkinCuboid.SkinOverlayDeplacement.LEFT_LEGS, null, null, new Point(0, 20), new Point(4, 20), null, new Point(12, 20)),
	LEGS_LEFT_MID(SkinCuboid.SkinOverlayDeplacement.LEFT_LEGS, null, null, new Point(0, 24), new Point(4, 24), null, new Point(12, 24)),
	LEGS_LEFT_BOT(SkinCuboid.SkinOverlayDeplacement.LEFT_LEGS, null, new Point(8, 16), new Point(0, 28), new Point(4, 28), null, new Point(12, 28)),

	LEGS_RIGHT_TOP(SkinCuboid.SkinOverlayDeplacement.RIGHT_LEGS, null, null, null, new Point(20, 52), new Point(24, 52), new Point(28, 52)),
	LEGS_RIGHT_MID(SkinCuboid.SkinOverlayDeplacement.RIGHT_LEGS, null, null, null, new Point(20, 56), new Point(24, 56), new Point(28, 56)),
	LEGS_RIGHT_BOT(SkinCuboid.SkinOverlayDeplacement.RIGHT_LEGS, null, new Point(24, 48), null, new Point(20, 60), new Point(24, 60), new Point(28, 60));

	private final Point[] points;
	private final SkinCuboid.SkinOverlayDeplacement translation;
	
	/**
	 * 
	 * @param translation 
	 * 		translation pour aller des coordonnées de la partie du skin à la sur-couche.
	 * @param pointTop
	 * @param pointBot
	 * @param pointLeft
	 * @param pointFrontOff
	 * @param pointRight
	 * @param pointBehind
	 */
	private SkinCuboid(SkinCuboid.SkinOverlayDeplacement translation, Point pointTop, Point pointBot, Point pointLeft, Point pointFrontOff, Point pointRight, Point pointBehind) {
		this.points = new Point[] { pointTop, pointBot, pointLeft, pointFrontOff, pointRight, pointBehind };
		this.translation = translation;

	}

	/**
	 * @return La liste de tous les points appartenant à l'armorstand (petit tête)
	 */
	public final Point[] getPoints() {return this.points;}

	/**
	 * @return translation pour aller des coordonnées de la partie du skin à la sur-couche.
	 */
	public SkinCuboid.SkinOverlayDeplacement getTranslation() {return this.translation;}
	
	/**
	 * @return Le nom attribué à l'armorstand tete.
	 */
	@Override
	public final String toString() {
		return Character.toUpperCase(super.name().charAt(0)) + super.name().substring(1, super.name().length()).toLowerCase();
	}

//	public static SkinCuboid getByName(String name) {
//		for (SkinCuboid skin : SkinCuboid.values())
//			if (skin.toString().equalsIgnoreCase(name))
//				return skin;
//		return null;
//	}

	/**
	 * 
	 * Translation des points pour la couche supérieur.
	 * 
	 * @author CactusCata
	 *
	 */
	public enum SkinOverlayDeplacement {

		HEAD(32, 0),
		RIGHT_HAND(16, 0),
		LEFT_HAND(0, 16),
		RIGHT_LEGS(-16, 0),
		LEFT_LEGS(0, 16),
		CHEST(0, 16);

		private final int xAdded, yAdded;

		private SkinOverlayDeplacement(int xAdded, int yAdded) {
			this.xAdded = xAdded;
			this.yAdded = yAdded;
		}

		public int getXAdded() {return this.xAdded;}
		public int getYAdded() {return this.yAdded;}

	}

}
