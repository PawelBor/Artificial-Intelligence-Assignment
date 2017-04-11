package ie.gmit.sw.weapons;

import ie.gmit.sw.node.NodeType;
import ie.gmit.sw.node.Weapon;

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
