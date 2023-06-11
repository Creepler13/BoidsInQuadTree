package Octree;

import java.util.ArrayList;

public class OctreeNode {

	private int x, y, width, height, distanceToTop = 0;

	Octree octree;

	private OctreeNode parent;

	public OctreeNode(int x, int y, int width, int height, Octree octree, OctreeNode parent) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.octree = octree;
		this.parent = parent;

		if (parent != null) // means its the octree
			distanceToTop++;

	}

	private ArrayList<OctreeObject> objects = new ArrayList<>();

	private ArrayList<OctreeNode> children = new ArrayList<>();

	public void add(OctreeObject obj) {
		octree.addFull(obj);
	}

	void addFull(OctreeObject obj) {
		obj.setOctree(octree);

		OctreeNode node = getQuadrant(obj);
		
		//means no children (this node)
		if (node == null) {
			objects.add(obj);
			obj.setOctreeNode(this);
			obj.setOctreeParentNode(parent);
		} else {
			node.addFull(obj);
		}

		if (!hasChildren() && objects.size() >= octree.maxNodeSize && distanceToTop < octree.maxNodes
				&& this.width > octree.minWidth && this.height > octree.minHeight) {
			children = newNodes(x, y, width, height, octree, this);

			for (OctreeObject object : objects) {
				OctreeNode temp = getQuadrant(object);
				temp.addFull(object);
			}

			objects.clear();

		}

	}

	public void remove(OctreeObject object) {
		if (hasChildren())
			for (OctreeNode octreeNode : children) {
				octreeNode.remove(object);
			}
		else
			objects.remove(object);

		if (hasChildren() && objects.size() < octree.maxNodeSize) {

			objects.addAll(getAllObjects());

			children.clear();
		}
	}

	private OctreeNode getQuadrant(OctreeObject object) {

		if (!hasChildren())
			return null;

		for (OctreeNode octreeNode : children) {

			if (object.getX() >= octreeNode.getX() && object.getY() >= octreeNode.getY()
					&& object.getX() <= octreeNode.getX() + octreeNode.getWidth()
					&& object.getY() <= octreeNode.getY() + octreeNode.getHeight())
				return octreeNode;
		}

		// maybe problematisch
		// if (parent != null)
		// return parent.getQuadrant(object);

		return children.get(0);
	}

	
	
	public boolean hasChildren() {
		return !children.isEmpty();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Octree getOctree() {
		return octree;
	}

	public OctreeNode getParent() {
		return parent;
	}

	public ArrayList<OctreeObject> getObjects() {
		return (ArrayList<OctreeObject>) objects.clone();
	}

	public ArrayList<OctreeObject> getAllObjects() {

		ArrayList<OctreeObject> c = getObjects();

		for (OctreeNode octreeNode : getChildren()) {
			c.addAll(octreeNode.getAllObjects());
		}

		return c;
	}

	public ArrayList<OctreeNode> getChildren() {
		return (ArrayList<OctreeNode>) children.clone();
	}

	public ArrayList<OctreeNode> getAllChildren() {

		ArrayList<OctreeNode> c = getChildren();

		for (OctreeNode octreeNode : getChildren()) {
			c.addAll(octreeNode.getAllChildren());
		}

		return c;
	}

	public int size() {
		return objects.size();
	}

	private static ArrayList<OctreeNode> newNodes(int parenX, int parentY, int parentWidth, int parentHeight,
			Octree octree, OctreeNode parent) {
		ArrayList<OctreeNode> nodes = new ArrayList<>();

		int width = parentWidth / 2;
		int height = parentHeight / 2;

		nodes.add(new OctreeNode(parenX, parentY, width, height, octree, parent));
		nodes.add(new OctreeNode(parenX + width, parentY, width, height, octree, parent));
		nodes.add(new OctreeNode(parenX, parentY + height, width, height, octree, parent));
		nodes.add(new OctreeNode(parenX + width, parentY + height, width, height, octree, parent));

		return nodes;

	}

	@Override
	public String toString() {
		String s = this.x + " " + this.y + " " + this.width + " " + this.height + " [ allChildren:"
				+ getAllChildren().size() + ", objects:" + getObjects().size() + ", allObjects:"
				+ getAllObjects().size() + " ]";

		return s;
	}

}
