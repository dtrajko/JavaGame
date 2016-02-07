package com.dtrajko.java.game.level;

import com.dtrajko.java.game.util.Vector2i;

public class Node {

	public Vector2i tile;
	public Node parent;
	public double fCost; // total cost, calculated length between start and end node
	public double gCost; // the cost (length) of the already calculated path, between start and current node
	public double hCost; // distance between current node and the end node

	public Node(Vector2i tile, Node parent, double gCost, double hCost) {
		this.tile = tile;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = this.gCost + this.hCost;
	}
}
 