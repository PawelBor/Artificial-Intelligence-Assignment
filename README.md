
## *Artificial Intelligence Project 2017*

**Name:** Gediminas Saparauskas, Pawel Borzym</br>
**College:** Galway-Mayo Institute of Technology </br>
**Course:** Software Development </br>
**Module:** Artificial Intelligence </br>
**Lecturer:** Dr.John Healy </br>
**Lang:** Java

## Controlling Game Characters with Neural Networks and Fuzzy Logic

### How to Run

**Eclipse**

1. Download this repository
2. Start your IDE
3. Import the project
4. Make sure that jFuzzyLogic.jar is added correctly as path in the project.
5. Run the Game.

**Jar**

1. Download this reository
2. Add jFuzzyLogic.jar to your path.
3. Run the .jar file.

### Overview

There is one goal, find the exit within the maze. During the game you will be
met with multiple spider enemies that will try to kill you and each spider behaves differently. 
The enemies behave differently due to each of spiders using different path finding algorithms to find the goal node (The Player).
The player has a choice of controlling the hero or let it find the exit himself by selecting the AI option before the game begins.
This way the user can just watch as the hero clears the game himself.

### Spider Nodes
***Black Spider***: Uses Random Walk Algorith and is not really much of a threat unless encountered. The Damage is set to be random and sometimes they can hit really hard.
***Blue Spider***: Uses A* Search Algorithm to find the player Node. It's extrely effective and fast.<br>
*A* is an iterative, ordered search that maintains a set of open states to
explore in an attempt to reach the goal state.*<br>
***Brown Spider***: Depth Limited DFS Search Algorith to find the player (Heuristic).
This is implemented but results in duplicate spiders being spawned (can be uncommented to see how it works).

### Weapon Nodes
***Sword***: Deals random value damage to enemy spider. WIthout it player cannot deal damage to spider.

### Exit Node
***Exit Door***: You must find this to win the game and exit the maze.

### Fuzzy Logic
We're using this to display the score.
In the Encounter.java class we're getting data like enemy damage,  weapon damage and health.
Then we set the variables and evaluate them and display the score based on the .FCL file.


### Neural Network

Decides what happens when player encounteres a spider and goes to fight. 
It decides whether the player should continue to fight the spider or flee.
We used two input nodes, 2 hidden nodes and 2 output nodes.

We used a large dataset to train the neural network with data such as:

If Health is Low and Sword is None Run.
If Health is High and Sword is None Run.
If Health is Low and Sword is True Run.
If Health is High and Sword is True Attack.

The Neural Network can also be toggled using the 'x' keyboard key.

### Threads

All spiders are threaded using a thread pool. 
Each one has its own worker thread and behave independently of each other.

### Conclusion

We learned how Neural Networks, Fuzzy Logic and different path finding algorithms work and how to implement them.
Due to limited time and pressure from other modules, we did not get a chance to polish the game to perfection.
