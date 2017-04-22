package ie.gmit.sw.traversor;

import ie.gmit.sw.characters.Enemy;
import ie.gmit.sw.node.Node;

public interface Traversator {
	public void traverse(Node[][] maze, Enemy enemy) throws InterruptedException;
}
