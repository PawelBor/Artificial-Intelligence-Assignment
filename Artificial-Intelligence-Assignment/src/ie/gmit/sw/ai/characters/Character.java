package ie.gmit.sw.ai.characters;

import ie.gmit.sw.ai.node.Health;

public class Character extends Health{
	
	public void takeDamage(int damage)
	{
		setHealth(getHealth() - damage);
	}
	
	public boolean isAlive(){
		if(getHealth() < 0)
			return false;		
		return true;
	}
}
