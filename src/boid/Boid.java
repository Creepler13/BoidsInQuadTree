package boid;

import java.util.ArrayList;

import Octree.Octree;
import Octree.OctreeObject;
import core.Boundary;
import core.Position;
import core.Vector;
import processing.core.PApplet;

public class Boid extends OctreeObject {

	public Vector velocity = new Vector(1, 1);
	public Vector direction = new Vector(3, 0);

	public static int COLOR_RED = 16711680;
	public static int COLOR_GREEN = 65280;
	public static int COLOR_BLUE = 255;
	
	
	
	private PApplet p5;

	public Boid(int x, int y, Octree world, PApplet p5) {
		setPosition(x, y);
		this.world = world;
		this.p5 = p5;
	}

	public Octree world;

	public int color = 16777215;
	
	public boolean drawDebug = false;

	public static double edgeValue = 100;

	public static double turnFactor = 0.3;
	public static double avoidFactor = 0.05;
	public static double matchingFactor = 0.05;
	public static double centeringFactor = 0.00005;
	public static double minSpeed = 3, maxSpeed = 8;
	public static double protectedRange = 8;
	public static double visibleRange = 40;

	public static double randomMovementAmount = 0.01;

	public void update() {

		double boidX = getX(), boidY = getY();

		updateBoidBehavoir(boidX, boidY);

		randomMovement();
		min_maxSpeed();
		walls(boidX, boidY);

		setX(getX() + velocity.x);
		setY(getY() + velocity.y);
		
		if(drawDebug) {			
			p5.noFill();
			p5.circle((int) getX(), (int) getY(), (int) protectedRange);
			p5.circle((int) getX(), (int) getY(), (int) visibleRange);
			p5.fill(255);
		}

	}

	private void min_maxSpeed() {
		double speed = Math.sqrt(velocity.x * velocity.x + velocity.y * velocity.y);

		if (speed > maxSpeed) {
			velocity.x = (velocity.x / speed) * maxSpeed;
			velocity.y = (velocity.y / speed) * maxSpeed;
		}
		if (speed < minSpeed) {
			velocity.x = (velocity.x / speed) * minSpeed;
			velocity.y = (velocity.y / speed) * minSpeed;
		}
	}

	private void randomMovement() {
		double rand = Math.random();
		if (rand > 0.5)
			velocity.x = velocity.x + randomMovementAmount * rand;
		else
			velocity.y = velocity.y + randomMovementAmount * rand;

	}

	private void updateBoidBehavoir(double boidX, double boidY) {
		xvel_avg = 0;
		yvel_avg = 0;
		xpos_avg = 0;
		ypos_avg = 0;

		neighboring_boids = 0;
		otherBoid_dx = 0;
		otherBoid_dy = 0;

		for (OctreeObject ob : world.getAllObjects()) {
			Boid otherBoid = (Boid) ob;

			double dist = new Vector(new Position(boidX, boidY), new Position(otherBoid.getX(), otherBoid.getY()))
					.getLength();

			if (dist < protectedRange) {

				otherBoid.color=COLOR_RED;
				
				// seperation
				otherBoid_dx += boidX - otherBoid.getX();
				otherBoid_dy += boidY - otherBoid.getY();
			}

			if (dist < visibleRange && dist > protectedRange) {
				
				otherBoid.color=COLOR_GREEN;
				
				// cohesion
				xpos_avg += boidX - otherBoid.getX();
				ypos_avg += boidY - otherBoid.getY();

				// allignment
				xvel_avg = xvel_avg + otherBoid.velocity.x;
				yvel_avg = yvel_avg + otherBoid.velocity.y;

				neighboring_boids++;

			}
		}

	
		endBehavoirUpdate();

	}

	private void endBehavoirUpdate() {

		if (neighboring_boids > 0) {

			// cohesion
			xpos_avg = xpos_avg / neighboring_boids;
			ypos_avg = ypos_avg / neighboring_boids;

			// alignment
			xvel_avg = xvel_avg / neighboring_boids;
			yvel_avg = yvel_avg / neighboring_boids;
		}

		// cohesion
		velocity.x += (xpos_avg - velocity.x) * centeringFactor;
		velocity.y += (ypos_avg - velocity.y) * centeringFactor;

		// alignment
		velocity.x += (xvel_avg - velocity.x) * matchingFactor;
		velocity.y += (yvel_avg - velocity.y) * matchingFactor;

		// seperation
		velocity.x = velocity.x + otherBoid_dx * avoidFactor;
		velocity.y = velocity.y + otherBoid_dy * avoidFactor;
	}

	double xvel_avg = 0, yvel_avg = 0, neighboring_boids = 0;
	double otherBoid_dx = 0, otherBoid_dy = 0, xpos_avg = 0, ypos_avg = 0;

	private void walls(double boidX, double boidY) {
		if (boidX < edgeValue)
			velocity.x = velocity.x + turnFactor;
		if (boidX > world.getWidth() - edgeValue)
			velocity.x = velocity.x - turnFactor;
		if (boidY < edgeValue)
			velocity.y = velocity.y + turnFactor;
		if (boidY > world.getHeight() - edgeValue)
			velocity.y = velocity.y - turnFactor;

	}

	private double wallForceStart = 150, maxBoundaryForce = 1, wfs = maxBoundaryForce / wallForceStart;

	public Boundary hitBoundary(ArrayList<Boundary> walls) {

		Position position = new Position(getX(), getY());

		Boundary hit = null;

		for (Boundary boundary : walls) {

			// double l = (y3 * x4+ x1* y4 - x3* y4 - y1*x4)/ ( y2 *x4 - x2* y4)
			double l = (boundary.a.y * boundary.direction.x + position.x * boundary.direction.y
					- boundary.a.x * boundary.direction.y - position.y * boundary.direction.x)
					/ (direction.y * boundary.direction.x - direction.x * boundary.direction.y);

			double dirLength = direction.getLength();

			if (l < dirLength)
				if (dirLength >= 0 && l >= 0 || dirLength <= 0 && l <= 0) {

					double sx = (position.x + l * direction.x - boundary.a.x) / boundary.direction.x;
					double sy = (position.y + l * direction.y - boundary.a.y) / boundary.direction.y;

					if (sx >= 0 || sy >= 0)
						if (sx <= boundary.direction.getLength() || sy <= boundary.direction.getLength()) {
							dirLength = l;
							hit = boundary;
						}
				}
		}

		return hit;
	}

}
