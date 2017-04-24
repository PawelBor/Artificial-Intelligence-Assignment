package ie.gmit.sw.ai.weapons;

import ie.gmit.sw.ai.node.NodeType;
import ie.gmit.sw.ai.node.Weapon;

public class Bomb extends Weapon{
	
	private int radius;
	
	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public Bomb(int weaponDamage, NodeType weaponType) {
		super(80, NodeType.Bomb);
		radius = 10;
	}
}
