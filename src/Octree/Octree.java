package Octree;

public class Octree extends OctreeNode {

	public int maxNodeSize, maxNodes, minWidth, minHeight;

	public Octree(int width, int height, int maxNodeSize, int maxNodes, int minWidth, int minHeight) {
		super(0, 0, width, height, null, null);
		this.octree = this;
		this.maxNodeSize = maxNodeSize;
		this.maxNodes = maxNodes;
		this.minWidth = minWidth;
		this.minHeight = minHeight;
	}

	public Octree(int width, int height, int maxNodeSize, int maxNodes) {
		super(0, 0, width, height, null, null);
		this.octree = this;
		this.maxNodeSize = maxNodeSize;
		this.maxNodes = maxNodes;
		this.minWidth = 10;
		this.minHeight = 10;
	}

}
