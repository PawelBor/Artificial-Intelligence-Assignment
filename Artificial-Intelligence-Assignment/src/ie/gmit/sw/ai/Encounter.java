package ie.gmit.sw.ai;

import javax.swing.JOptionPane;

import ie.gmit.sw.ai.node.NodeType;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class Encounter {
	private FIS fis;
	private FunctionBlock functionBlock;
	public static int spiderDead = -1;
	
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

	public int getScore(int enemy_damage, int weapon_damage, int health, int spiderIndex) throws Exception{
		
		// Default values
		double hasWeapon = 0.0; double hasHealth = 1.0;
		
		// Get the number of swords from the inventory
        int inventoryWeapons = Game.spartan.getInventoryCount(NodeType.Sword);
		
		if(inventoryWeapons > 0)
			hasWeapon = 1.0;
		else
			hasWeapon = 0.0;
		
		if(health <= 40)
			hasHealth = 0.0;
		else if (health > 60)
			hasHealth = 1.0;
			
    	boolean fight = Game.combatNet.action(hasHealth , hasWeapon);
    	
    	if(fight){
    		
    		Variable score = null;
    		
    		fis.setVariable("enemy_damage", enemy_damage);
            fis.setVariable("weapon_damage", weapon_damage);
            fis.setVariable("health", health);
            fis.evaluate();        
            score = functionBlock.getVariable("score");
            
            System.out.println("Enemy Damage: " + enemy_damage*4
            			+ " Weapon Damage: " + weapon_damage 
            			+ " Health: " + health);
            
            System.out.println("Fuzzy logic score: "+ score);

            // Player takes damage
            Game.spartan.takeDamage((enemy_damage*4));
            
            // Spider takes damage
            Maze.enemyArray.get(spiderIndex).takeDamage(weapon_damage);
            
            if(Maze.enemyArray.get(spiderIndex).getHealth() <= 0)
            {
            	spiderDead = spiderIndex;
            }
            
            System.out.println("Score: " + score.getValue()+" New health: "+Game.spartan.getHealth());
            
            Game.hp.setText(Integer.toString(Game.spartan.getHealth()));

    	}else{// Run away

    		if(Game.toggleNN){// if nn is on
    			Game.spartan.run(true);
    		}else{
    			Variable score = null;
        		
        		fis.setVariable("enemy_damage", enemy_damage);
                fis.setVariable("weapon_damage", weapon_damage);
                fis.setVariable("health", health);
                fis.evaluate();        
                score = functionBlock.getVariable("score");
                
                System.out.println("Enemy Damage: " + enemy_damage*4
                			+ " Weapon Damage: " + weapon_damage 
                			+ " Health: " + health);
                
                System.out.println("Fuzzy logic score: "+ score);

                // Player takes damage
                Game.spartan.takeDamage((enemy_damage*4));
                
                // Spider takes damage
                Maze.enemyArray.get(spiderIndex).takeDamage(weapon_damage);
                
                if(Maze.enemyArray.get(spiderIndex).getHealth() <= 0)
                {
                	spiderDead = spiderIndex;
                }
                
                System.out.println("Score: " + score.getValue()+" New health: "+Game.spartan.getHealth());
                
                Game.hp.setText(Integer.toString(Game.spartan.getHealth()));
    		}
    		
    	}

        if(Game.spartan.getHealth() < 0)
        	infoBox("You Died", "Game Over");
		
		return weapon_damage;
	}
	
	public static void infoBox(String infoMessage, String titleBar)
    {
		Object[] options = {"Exit"};
        int n = JOptionPane.showOptionDialog(Game.f,
                        infoMessage,
                        titleBar,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);
        if (n == JOptionPane.YES_OPTION) {
        	System.exit(0);
        } else if (n == JOptionPane.NO_OPTION) {
        } else {

        }
    }
	
	
}
