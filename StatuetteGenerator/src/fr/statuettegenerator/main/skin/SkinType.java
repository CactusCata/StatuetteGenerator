package fr.statuettegenerator.main.skin;

public enum SkinType {

	DEFAULT();

	@Override
	public final String toString() {
		return "_" + Character.toUpperCase(super.name().charAt(0))
				+ super.name().substring(1, super.name().length()).toLowerCase();
	}

}
