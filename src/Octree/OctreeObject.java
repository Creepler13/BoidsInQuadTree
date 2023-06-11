package Octree;

public class OctreeObject {

	private double x = 0, y = 0;
	private OctreeNode octreeNode, OctreeParentNode;
	private Octree octree;


	
	public double getX() {
		return x;
	};

	public double getY() {
		return y;
	};

	public void setPosition(int x, int y) {
		setX(x);
		setY(y);
	};

	public void setX(double x) {
		if (octree != null)
			this.x = (x <= 0) ? 0 : ((x > octree.getWidth()) ? octree.getWidth() : x);
		else
			this.x = x;
		updateTree();
	}

	public void setY(double y) {
		if (octree != null)
			this.y = (y <= 0) ? 0 : ((y > octree.getHeight()) ? octree.getHeight() : y);
		else
			this.y = y;
		updateTree();
	}

	public void setPosition(double x, double y) {
		setX(x);
		setY(y);
	};

	public void setX(int x) {
		setX((double) x);
	}

	public void setY(int y) {
		setY((double) y);
	}

	public OctreeNode getOctreeNode() {
		return octreeNode;
	}

	public OctreeNode getOctreeParentNode() {
		return OctreeParentNode;
	}

	void setOctreeNode(OctreeNode octreeNode) {
		this.octreeNode = octreeNode;
	}

	void setOctreeParentNode(OctreeNode octreeParentNode) {
		OctreeParentNode = octreeParentNode;
	}

	void setOctree(Octree octree) {
		this.octree = octree;
	}

	private void updateTree() {
		if (octree == null)
			return;
		octree.remove(this);
		octree.add(this);
	}
	
	@Override
	public String toString() {
		return getX()+ " "+ getY();
	}

}
