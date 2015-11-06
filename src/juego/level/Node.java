package juego.level;

import juego.util.Vector2i;

public class Node {
	
	public Vector2i tile;
	public Node parent;
	public double fCost, gCost, hCost;
	
	// gCost is the cost of all of our node to node distances
	// hCost is the direct distance to the finish
	// fCost is the total
	public Node(Vector2i tile, Node parent, double gCost, double hCost){
		this.tile = tile;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = this.gCost + this.hCost;
	}
}
