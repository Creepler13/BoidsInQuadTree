package core;

public class NormalizedVector {

	public double x, y;
	public double lenght;

	public NormalizedVector(double x, double y) {
		this.x = x;
		this.y = y;
		normalize();
	}

	public NormalizedVector(Position position) {
		this.x = position.getX();
		this.y = position.getY();
		normalize();
	}

	public NormalizedVector(Position a, Position b) {
		this.x = b.getX() - a.getX();
		this.y = b.getY() - a.getY();
		normalize();

	}

	public NormalizedVector fromLength(double lenght) {
		return new NormalizedVector(x * lenght, y * lenght);
	}

	public Position pointFrom(Position a) {
		return new Position(a.x + x * lenght, a.y + y * lenght);
	}

	public NormalizedVector subtract(NormalizedVector b) {
		return new NormalizedVector(getX() - b.getX(), getY() - b.getY());
	}

	public NormalizedVector add(NormalizedVector b) {
		return new NormalizedVector(getX() + b.getX(), getY() + b.getY());
	}

	public NormalizedVector multiply(double b) {
		return new NormalizedVector(getX() * b, getY() * b);
	}

	public NormalizedVector divide(double b) {
		return new NormalizedVector(getX() / b, getY() / b);
	}

	public void subtractME(NormalizedVector b) {
		x = getX() - b.getX();
		y = getY() - b.getY();
		normalize();
	}

	public void addME(NormalizedVector b) {
		x = getX() + b.getX();
		y = getY() + b.getY();
		normalize();
	}

	public void multiplyME(double b) {
		x = getX() * b;
		y = getY() * b;
		normalize();
	}

	public void divideME(double b) {
		x = getX() / b;
		y = getY() / b;
		normalize();
	}

	public void normalize() {

		lenght = Math.abs(Math.sqrt(x * x + y * y));

		if (lenght == 0)
			return;

		x = x / lenght;
		y = y / lenght;

	}

	public double getX() {
		return (x * lenght);
	}

	public void setX(double x) {
		this.x = x;
		y = y * lenght;

		normalize();
	}

	public double getY() {
		return (y * lenght);
	}

	public void setY(double y) {
		this.y = y;

		x = x * lenght;

		normalize();
	}

	public void clear() {
		this.x = 0;
		this.y = 0;
		this.lenght = 0;
	}

	public NormalizedVector clone() {
		return new NormalizedVector(x * lenght, y * lenght);
	}

	@Override
	public String toString() {
		return "[" + this.x + "," + this.y + "->" + this.lenght + "]";
	}

}
