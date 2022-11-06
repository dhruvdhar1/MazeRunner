# Graphical Adventure Game
## _Project-5, CS-5010_
Developed by [Dhruv Dhar] [dd-github]


[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

# About/ Overview

This application represents a maze based game with a dungeon consisting of a network of caves and tunnels interconnected with eachother. This network of caves and tunnels must be travered by the player to reach from start to the end node. Some of the caves in the Dungeon contain Monsters which must be slayed with arrows to cross the cave. Entering a cave with an alive monster will lead to the player's death. The player will initially be given 3 arrows but can collect more arrows scattered around the cells of the dungeon. The player needs to shoot 2 arrows to kill the monster.

The player will only have knowledge of the starting location and the current location but will not be having any knowledge of the exit node. The maze uncovers as the player travels through the network. The player can move in 4 directions: North, South, East, West to traverse in the dungeon. The player can also collect treasures from caves along the way. The game comes to an end once the player reaches the exit node alive!

This java application provides a software implementation for this type of game. This application exposes a rich and interactive User interface that can be used to perform various actions in the game for the player to win.

## Features

- Creates a maze of user specified dimention (M x N).
- Rick and interactive user interface with option to use either keyboard or mouse input to play.
- User can choose to turn the maze into a wrapping maze.
- User can specify a custom interconnectivity value .
- User can specify the percentage of caves that can have treasure.
- User can specify the number of monsters present in the dungeon.
- User can specify the number of pits to add in the dungeon.
- User can specify the number of thieves to add in the dungeon.
- Treasure is randomly assigned to x% caves based on user input.
- Arrows are randomly assigned to x% cells based on user input.
- Caves can have same or different types of treasures
- Both Caves and Tunnels can have Arrows for player to collect.
- Caves can have 3 types of treasures: Diamond, Ruby and Sapphire.
- The player can choose to collect treasures from caves while traversing through the dungeon.
- The application ensures a minimum distance of 5 hops between the starting and ending nodes.
- Get a description of Player's collected treasures
- Get a description of Player's current location.
- Ability to choose between using console/ Graphical interface.

# How to use
``` sh
Clone the repository
Build the Java Project 
cd [root-foler]/res/
Steps for running Console based game.
    1. Launch terminal at path and run: 
    2. java -jar GraphicalAdventure.jar <row> <col> <interconnectivity> <numMonster> <treasurePercent> <wrap/nowrap>

    Example 1 (wrapping dungeon): java -jar TextAdventure.jar 5 5 2 3 30 wrap
    Example 2 (non-wrapping): java -jar TextAdventure.jar 5 5 2 6 30 nowrap
    
Steps for running GUI based game.
    1. Launch terminal at path and run: 
    2. java -jar GraphicalAdventure.jar
    
    Example 1: java -jar GraphicalAdventure.jar
```

# JUnit
```sh
JUnit Code coverage: 95%
```
#Description of Screenshots
```Run 1 (Screenshots/SS1.jpeg)```
- The screenshot shos the player navigating his way through the dungeon.
- The dungeon has not completely displayed and the cells uncover as the player moves into them.
- Treasures are displyed with red, blue and green icons, Arrows with white arrow icon
- Smell is displayed as a green stench overlay on the cell.
- A panel on the left represents the player bag and displayes the items currently pocessed by the player.
- A button panel on the bottom contains all the action buttons of the game.

```Run 2 (Screenshots/SS2.jpeg)```
- The screenshot shos the player navigating his way through the dungeon and getting killed.
- The player walks into a cave wiy=th Otyugh and gets killed instantly and the game is over.


```Run 3 (Screenshots/SS3.jpeg)```
- This run shows the player's ability to collect arrows from the dungeon.
- Upon reaching a cell with arrows, the player can either click the collect button or press 'c' key on the keyboard to get the popup.
- On this popup, the user can select tbe items to collect and then click on OK.

```Run 4 (Screenshots/SS4.jpeg)```
- This run shows the player's ability to collect treasures from the dungeon.
- Upon reaching a cell with treasure, the player can either click the collect button or press 'c' key on the keyboard to get the popup.
- On this popup, the user can select tbe items to collect and then click on OK.
 
```Run 5 (Screenshots/SS5.jpeg, SS6.jpeg)```
- This run shows the player's ability to shoot an arrow.
- The player can click the shoot button to get this popup.
- Upon getting this popup, he needs to select the direction and distance and click on OK to shoot the arrow in that direction.
- Alternatively, the player can also press [s + <arrow key>] to shoot arrow in that direction. 
- On pressing these keys, the player gets a popup to select direction. After selecting direction, player can click OK to shoot.

```Run 6 (Screenshots/SS7.jpeg)```
- This run shows the player's ability to fall into a pit.
- While navigating through the dungeon, the player can fall into a pit and get killed.
- At this point, the game is over.
- The player gets a warning message about the pit, 1 cave prious to the cave with the pit.

```Run 7 (Screenshots/SS8.jpeg)```
- This run shows the player's ability to get mugged by a thief.
- The thief can be present in any cave in the dungeon.
- Upon encountering a thief, all the player treasures are stolen by the thief.

```Run 8 (Screenshots/SS9.jpeg)```
- This run shows the initial setting window that is used to accept user input to set game parameters.
- Thes input fields include numRoes, numColumn, interconnectivity(default=0 ), numMonster, treasurePercent(default=0), numPits(default=0), numThief(default=0), wrapping(default=Yes)

# Design/ Model Changes
- Added a read-only model that is extended by the read-write model.
- Added interfaces and classes to render the view of the application.
- Created a new controller that can interact with the views.
- Added ability to put a pit and thieves to the dungeon in addition to Otyugh

# Assumptions
- Arrows can travel a maximum distance of 5.
- A cell can have single or multiple arrows (Max 5).
- At least 1 monster will be present in the dungeon.
- A thief does not change its location and can steal a player's treasures and arrows.
- A thief, pit and otyugh can be present in the same cave.

# Limitations 
- Minimum maze size needs to be (5 x 5).
- Minimum 1 monter needs to be added to the dungeon.

# Citations
- GeeksforGeeks. (2020, December 16). Shortest path in an unweighted graph. https://www.geeksforgeeks.org/shortest-path-unweighted-graph/
- Breadth first search or BFS for a graph. GeeksforGeeks. (2021, October 18). Retrieved November 2, 2021, from https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/. 
- Andre_Lopes, ReBirth, Davidc, &amp; StonePickaxes. (2013, March 18). How to listen multiple key presses? JVM Gaming. Retrieved December 9, 2021, from https://jvm-gaming.org/t/how-to-listen-multiple-key-presses/41272. 
- How to write a focus listener. How to Write a Focus Listener (The Java™ Tutorials &gt; Creating a GUI With Swing &gt; Writing Event Listeners). (n.d.). Retrieved December 9, 2021, from https://docs.oracle.com/javase/tutorial/uiswing/events/focuslistener.html. 

   [dd-github]: <https://www.github.com/dhruvdhar1>
