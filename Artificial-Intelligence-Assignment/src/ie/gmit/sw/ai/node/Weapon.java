package ie.gmit.sw.ai.node;

public class Weapon extends Item{
	private int weaponDamage;
	private int weaponCount;
	private NodeType weaponType;
	
	public Weapon(int weaponDamage, NodeType weaponType){
		super(weaponType);
		addWeaponCount();
		setWeaponDamage(weaponDamage);
		setWeaponType(weaponType);
	}

	//Accessor Methods
	
	//get set weaponDamage
	public int getWeaponDamage() {
		return weaponDamage;
	}

	public void setWeaponDamage(int weaponDamage) {
		this.weaponDamage = weaponDamage;
	}
	
	//get set weaponCount
	public int getWeaponCount() {
		return weaponCount;
	}

	public void addWeaponCount() {
		this.weaponCount++;
	}
	
	//get set weaponType
	public NodeType getWeaponType() {
		return weaponType;
	}

	public void setWeaponType(NodeType weaponType) {
		this.weaponType = weaponType;
	}
}