package ie.gmit.sw.characters;

import java.util.ArrayList;
import java.util.List;
import ie.gmit.sw.node.*;

public class Player{
	
	private List<Weapon> items = new ArrayList<>();
	private Weapon playerWeapon;
	
	public void Player(int health){
		Health playerHealth = new Health();
		playerHealth.setHealth(health);
		
		
	}
	
	//Accessor methods
	
	public void setWeapon(Weapon playerWeapon){
		this.playerWeapon = playerWeapon;
	}
	
	public Weapon getWeapon(){
		return playerWeapon;
	}
}
