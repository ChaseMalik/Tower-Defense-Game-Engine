Tower Defense
==========

Contributors: Brian Bolze, Duke Kim, Allan Kiplagat, Austin Kyker, Greg Lyons, Chase Malik, Timesh Patel, Scotty Shaw, and David Zhang

Roles: 

Front End Authoring Environment: Austin Kyker and David Zhang

Front End Gameplay Environment: Brian Bolze, Allan Kiplagat, Greg Lyons, and Scotty Shaw

Backend: Duke Kim, Chase Malik, and Timesh Patel

Start Date: November 5, 2014

End Date: December 14, 2014

This project tasked us with creating a Tower Defense Game Engine.  Allowing for users to both play and build tower defense games without any programming knowledge. 

By running src/gameAuthoring/AuthoringController.java you can experience the interactive Game Authoring Environment we created. Users can create a variety of different tower defense games. They can customize the map, the path enemies take, the enemies and their behaviors, the towers and their behaviors, as well as the number and difficulty of each level by controlling which enemies appear and how many of them appear. The user can then run simulations of each level to preview them, so that they can go back and make changes when it's necessary. After completing their game, the necessary data is written to a game directory of JSON data files using GSON.

To actually play a game, one must run src/gamePlayer/mainClasses/Main.java. Upon loading, the user is prompted with all of the games that have been created. When one has been selected, the game's directory path is passed to the Game Engine. The engine's purpose is to build and manage the different actors of the game. With the game directory, the engine can load the towers, enemies, levels, settings, etc., it needs to begin running the game. The user is then prompted for options in the GUI of the player to buy, place, and upgrade towers as well as to start new levels.

Not only can a player engage in SinglePlayer games, but the user can also play online against other players. In our coop game mode which the user can select in GamePlayer, users play levels cooperately and team up to stop the enemies. During this game mode, users can strategize/socialize with each other using a chatroom. In contrast to single player mode where users can place towers during and before levels, coop provides a 30 second timer between levels for players to place towers. Once the timer ends, the level begins and the players cannot place towers until after the level ends. The communication between players is made possible by a Django Server hosted on Heroku. A python, django application was written to handle the different game play requests and ensure that chat messages would be delivered to each player as well as the state of the game (when one player places a tower the tower immediately shows up on the other player's screen). The repository for the Django application can be found in the following link: https://github.com/akyker20/vooga_salad_server

Known Bugs:

There are some bugs in the game authoring environment's path building operation. The user cannot, for instance, build a path from an ending location to a starting location. Path components must be connected from the starting location toward the ending locations. If the user does make a mistake, however, they can simply reset the Path Building function or select and delete the troublesome components.

In the actual playing of a game, sometimes enemies from the next level spawn appear at the start location before the level has started.

Sometimes towers cannot be upgraded if their range circle goes over the upgrade pane (if the tower is placed close to the bottom of the screen).

When loading the game from a game save, you cannot click on the already constructed towers.
