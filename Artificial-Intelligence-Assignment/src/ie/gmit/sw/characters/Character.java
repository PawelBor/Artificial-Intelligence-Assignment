package ie.gmit.sw.characters;

import ie.gmit.sw.node.Health;

public class Character extends Health{
	
	public void takeDamage(int damage)
	{
		setHealth(getHealth() - damage);
	}
}
