package ie.gmit.sw.ai;

import javax.swing.JOptionPane;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
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

	public int getScore(int enemy_damage, int weapon_damage, int health){
				
        fis.setVariable("enemy_damage", enemy_damage);
        fis.setVariable("weapon_damage", weapon_damage);
        fis.setVariable("health", health);
        fis.evaluate();        
        Variable score = functionBlock.getVariable("score");
        
        System.out.println("Enemy Damage: " + enemy_damage
        			+ " Weapon Damage: " + weapon_damage 
        			+ " Health: " + health);

        // Spartan takes damage
        Game.spartan.takeDamage((enemy_damage*2));
        
        // Spider takes damage
        Maze.enemyArray.get(1).takeDamage(weapon_damage);
        
        if(Game.spartan.getHealth() < 0)
        	infoBox("You Died", "Game Over");
        
		System.out.println("Score: " + score.getValue()+" New health: "+Game.spartan.getHealth());
		
		return weapon_damage;
	}
	
	public static void infoBox(String infoMessage, String titleBar)
    {
		Object[] options = {"Retry", "Exit"};
        int n = JOptionPane.showOptionDialog(Game.f,
                        infoMessage,
                        titleBar,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);
        if (n == JOptionPane.YES_OPTION) {
        	
        } else if (n == JOptionPane.NO_OPTION) {
    		Game.f.dispose();
        } else {

        }
    }
	
	
}
