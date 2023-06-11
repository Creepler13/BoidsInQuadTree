package core;

public class Vector {

	public double x, y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector(Position position) {
		this.x = position.x;
		this.y = position.y;
	}

	public Vector(Position a, Position b) {
		this.x = b.x - a.x;
		this.y = b.y - a.y;
	}

	public Vector fromLength(double lenght) {
		return new Vector(x, y);
	}

	public Position pointFrom(Position a) {
		return new Position(a.x + x, a.y + y);
	}

	public Vector subtract(Vector b) {
		return new Vector(x - b.x, y - b.y);
	}

	public Vector add(Vector b) {
		return new Vector(x + b.x, y + b.y);
	}

	public Vector multiply(double b) {
		return new Vector(x * b, y * b);
	}

	public Vector divide(double b) {
		return new Vector(x / b, y / b);
	}

	public void subtractME(Vector b) {
		x = x - b.x;
		y = y - b.y;
	}

	public void addME(Vector b) {
		x = x + b.x;
		y = y + b.y;
	}

	public void multiplyME(double b) {
		x = x * b;
		y = y * b;
	}

	public void divideME(double b) {
		x = x / b;
		y = y / b;
	}

	public double getLength() {
		return Math.abs(Math.sqrt(x * x + y * y));
	}

	
	
	public NormalizedVector normalize() {
		return new NormalizedVector(x, y);
	}

	public void clear() {
		this.x = 0;
		this.y = 0;
	}

	public Vector clone() {
		return new Vector(x, y);
	}

	@Override
	public String toString() {
		return "[" + this.x + "," + this.y + "->" + getLength() + "]";
	}

}
