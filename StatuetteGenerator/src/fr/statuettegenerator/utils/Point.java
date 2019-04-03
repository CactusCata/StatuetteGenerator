package fr.statuettegenerator.utils;

public class Point {

	private final int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	@Override
	public final String toString() {
		return String.format("Point:[x:%d, y:%d]", this.x, this.y);
	}
	
}
