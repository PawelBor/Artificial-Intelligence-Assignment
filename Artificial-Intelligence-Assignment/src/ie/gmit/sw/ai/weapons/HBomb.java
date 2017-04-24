package ie.gmit.sw.ai.weapons;

import ie.gmit.sw.ai.node.NodeType;
import ie.gmit.sw.ai.node.Weapon;

public class HBomb extends Weapon{
	private int radius;
	
	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public HBomb(int weaponDamage, NodeType weaponType) {
		super(100, NodeType.HBomb);
		radius = 10;
	}

}
