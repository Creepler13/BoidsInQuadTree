
import java.util.ArrayList;
import java.util.Iterator;

import Octree.Octree;
import Octree.OctreeNode;
import Octree.OctreeObject;
import boid.Boid;
import core.Boundary;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Main extends PApplet {

	public static void main(String[] args) {
		PApplet.main("Main");
	}

	Octree world = new Octree(1800, 900, 3, 5);
	ArrayList<Boundary> walls = new ArrayList<>();

	int boids = 60;

	public void settings() {
		size(world.getWidth(), world.getHeight());
	}

	public void setup() {

		for (int i = 0; i < boids; i++) {
			world.add(new Boid(200, 200, world, this));
		}

		walls.add(new Boundary(0, 0, world.getWidth(), 0));
		walls.add(new Boundary(0, 0, 0, world.getHeight()));
		walls.add(new Boundary(world.getWidth(), 0, world.getWidth(), world.getHeight()));
		walls.add(new Boundary(0, world.getHeight(), world.getWidth(), world.getHeight()));

	}

	public void draw() {

		if (paused)
			return;

		clear();
		background(255);

		ArrayList<OctreeNode> nodes = new ArrayList<>();
		nodes.add(world);
		nodes.addAll(world.getAllChildren());

		for (OctreeNode node : nodes) {
			int x = node.getX();
			int y = node.getY();
			int width = node.getWidth();
			int height = node.getHeight();

			line(x, y, x + width, y);
			line(x + width, y, x + width, y + height);
			line(x + width, y + height, x, y + height);
			line(x, y + height, x, y);

		}

		for (Boundary wall : walls) {
			line((int) wall.a.x, (int) wall.a.y, (int) wall.b.x, (int) wall.b.y);
		}

		ArrayList<OctreeObject> objects = world.getAllObjects();

		for (OctreeObject object : objects) {
			Boid boid = ((Boid) object);

			boid.update();

			line((int) boid.getX(), (int) boid.getY(), (int) (boid.getX() + boid.velocity.x * 10),
					(int) (boid.getY() + boid.velocity.y * 10));

			
			
			fill(((Boid)object).color);
			circle((int) object.getX(), (int) object.getY(), boidSize);

		}

	}

	boolean paused = false;

	@Override
	public void keyReleased(KeyEvent event) {
		switch (event.getKeyCode()) {
		case 32:
			paused = !paused;
			break;
		}
		;
	}

	Boid selectedBoid;

	int boidSize = 10;

	@Override
	public void mouseReleased(MouseEvent event) {
		int mouseX = event.getX();
		int mouseY = event.getY();

		System.out.println(mouseX + " " + mouseY);
		for (OctreeObject obj : world.getAllObjects()) {
			Boid boid = (Boid) obj;

			double x = boid.getX(), y = boid.getY();

			line((int) x, (int) y, (int) (x + boidSize), (int) (y + boidSize));
			if (mouseX >= x && mouseX < x + boidSize && mouseY >= y && mouseY < y + boidSize)
				boid.drawDebug = true;
			else
				boid.drawDebug = false;

		}

	}
}
