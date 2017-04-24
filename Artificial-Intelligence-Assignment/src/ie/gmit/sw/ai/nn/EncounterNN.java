package ie.gmit.sw.ai.nn;

import ie.gmit.sw.ai.nn.activator.*;

public class EncounterNN {

	private NeuralNetwork nn = null;

    public EncounterNN(){
        nn = new NeuralNetwork(Activator.ActivationFunction.Sigmoid, 2, 2, 2);
        Trainator trainer = new BackpropagationTrainer(nn);
        trainer.train(data, expected, 0.1, 1000000);
    }

    // SWORD 1 = HAS, 0 = HASN'T
    // HEALTH 1 = HIGH HEALTH, 0.5 = OK HEALTH, 0 = LOW HEALTH
    
    private static double[][] data = { // { <health> , <sword> }
			{ 1.0 , 0.0 }, { 1, 0 }, { 1.0 , 0.0 }, { 1.0 , 0.0 },
			{ 1.0 , 1.0  }, {1.0 , 1.0  }, { 0.5,0.0 }, { 0.5, 0.0 },
			{ 0.5, 0.0 }, { 0.5, 0 }, { 0.5, 1.0  }, { 0.5, 1.0  },
			{ 0.0, 0.0 }, { 0.0, 0.0 }, { 0.0, 0.0 }, { 0.0, 0.0}, 
			{ 0, 1.0  }, { 0, 1.0  } };
	
	private static double[][] expected = { // { <attack>, <run>
			{ 0.0, 1.0 }, { 0.0, 1.0 }, { 0.0, 1.0 }, { 0.0, 1.0 }, 
			{ 1.0, 0.0 }, { 1.0, 0.0 }, { 0.0, 1.0 }, { 0.0, 1.0 }, 
			{ 0.0, 1.0 }, { 0.0, 1.0 }, { 1.0, 0.0 }, { 0.0, 1.0 }, 
			{ 0.0, 1.0 }, { 0.0, 1.0 }, { 0.0, 1.0 }, { 0.0, 1.0 }, 
			{ 0.0, 1.0 }, { 0.0, 1.0 } };

	
    public boolean action(double health, double sword) throws Exception{

        double[] params = { health, sword };

        double[] result = nn.process(params);

        for(double val : result){
            System.out.println(val);
        }

        int output = (Utils.getMaxIndex(result) + 1);

        switch(output){
            case 1:
                return true;
            default:
                return false;
        }
    }

}
