package ie.gmit.sw.ai;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class Encounter {
	private FIS fis;
	private FunctionBlock functionBlock;
	
	// creating a singleton class
	private static Encounter instance;
	
	private Encounter() {
		String fileName = "fcl/Encounter.fcl";
        fis = FIS.load(fileName,true);
        
        functionBlock = fis.getFunctionBlock("Encounter");
	}
	
	public static Encounter getInstance(){
		
		if (instance == null){
			instance = new Encounter();
		}
		
		return instance;
	} 	

	public double getScore(int enemy_damage, int weapon_damage, int health){
				
        fis.setVariable("enemy_damage", enemy_damage);
        fis.setVariable("weapon_damage", weapon_damage);
        fis.setVariable("health", health);
        fis.evaluate();        
        Variable score = functionBlock.getVariable("score");
        
        System.out.println("Enemy Damage: " + enemy_damage 
        			+ " Weapon Damage: " + weapon_damage 
        			+ " Health: " + health);
		System.out.println("Score: " + score.getValue());
		return score.getValue();
	}
	
	
}
