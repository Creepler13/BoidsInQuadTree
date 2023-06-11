package core;

public class Boundary {

	public Position a, b;
	public Vector direction;

	public Boundary(Position a, Position b) {
		this.a = a;
		this.b = b;
		this.direction = new Vector(a, b);
	}

	public Boundary(double x, double y, double x2, double y2) {
		this.a = new Position(x, y);
		this.b = new Position(x2, y2);
		this.direction = new Vector(a, b);
	}

	public Boundary(Position a, Vector direction) {
		this.a = a;
		this.b = direction.pointFrom(a);
		this.direction = direction;
	}

}
