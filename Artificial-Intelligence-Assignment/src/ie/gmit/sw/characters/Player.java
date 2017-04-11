package ie.gmit.sw.characters;

import java.util.ArrayList;
import java.util.List;
import ie.gmit.sw.node.*;

public class Player{
	
	private List<Weapon> items = new ArrayList<>();
	private Weapon playerWeapon;
	Health playerHealth;
	
	public void Player(){
		playerHealth  = new Health();
		playerHealth.setHealth(100);
	}
	
	public void Player(int health){
		playerHealth  = new Health();
		playerHealth.setHealth(health);
	}
	
	//Accessor methods
	
	public void setWeapon(Weapon playerWeapon){
		this.playerWeapon = playerWeapon;
	}
	
	public Weapon getWeapon(){
		return playerWeapon;
	}
	
	public void takeDamage(int damage){
		playerHealth.setHealth(playerHealth.getHealth() - damage);
	}
}
