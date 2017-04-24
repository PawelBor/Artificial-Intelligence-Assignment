package ie.gmit.sw.ai.traversor;

import ie.gmit.sw.ai.characters.Enemy;
import ie.gmit.sw.ai.node.Node;

public interface Traversator {
	public void traverse(Node[][] maze, Enemy enemy) throws InterruptedException, Exception;
}
