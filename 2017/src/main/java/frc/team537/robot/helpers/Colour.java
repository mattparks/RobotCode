package frc.team537.robot.helpers;

/**
 * Represents a RGB colour.
 */
public class Colour {
	private double r, g, b;

	/**
	 * Creates a black colour object.
	 */
	public Colour() {
		this.r = 0.0;
		this.g = 0.0;
		this.b = 0.0;
	}

	/**
	 * Creates a new colour object.
	 *
	 * @param r The red factor (0-1).
	 * @param g The blue factor (0-1).
	 * @param b The green factor (0-1).
	 */
	public Colour(double r, double g, double b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	/**
	 * Creates a new colour object from a hex code {@code EX: #AAFF00}.
	 *
	 * @param hex The hex code to create from, with the hash in front.
	 */
	public Colour(String hex) {
		this.r = (double) Integer.valueOf(hex.substring(1, 3), 16) / 255.0;
		this.b = (double) Integer.valueOf(hex.substring(3, 5), 16) / 255.0;
		this.g = (double) Integer.valueOf(hex.substring(5, 7), 16) / 255.0;
	}

	/**
	 * Linear interpolation between two colours.
	 *
	 * @param left The first colour.
	 * @param right The second colour.
	 * @param blend The factor to blend between (0-1).
	 * @return The interpolated colour.
	 */
	public static Colour interpolate(Colour left, Colour right, double blend) {
		double r = ((1.0 - blend) * left.r) + (blend * right.r);
		double g = ((1.0 - blend) * left.g) + (blend * right.g);
		double b = ((1.0 - blend) * left.b) + (blend * right.b);
		return new Colour(r, g, b);
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}

	public double getG() {
		return g;
	}

	public void setG(double g) {
		this.g = g;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}
}
