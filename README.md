voogasalad
==========

The purpose of this project was to create a Tower Defense Game Engine. Using an interactive Game Authoring Environment, users can create a variety of different tower defense games. They can customize the map, the path enemies take, the enemies and their behaviors, the towers and their behaviors, as well as different levels and the enemies which appear in each level. 
Finally, the user can run simulations of the level to see what they would look like and can go back and make changes where necessary. After collecting the data necessary to create a Tower Defense Game, this data is written using GSON to a game directory of JSON data files. 
Game Player is an application for actually playing one of the games created by the Authoring Environment. Upon loading, the user is prompted with all of the games that were created. Upon selecting a game, this game directory path is passed to a Game Engine. The Engine's purpose is to build and manage the different actors of the game. With the game directory, the engine can load the towers, enemies, levels, settings, etc., it needs to begin running a game. The user is then prompted for options in the GUI of the player to buy, place, and upgrade towers as well as to start new levels.
Not only can a player engage in SinglePlayer games, but the user can also play online against other players. In our coop game mode which the user can select in GamePlayer, users play levels cooperately and team up to stop the enemies. During this game mode, users can strategize/socialize with each other using a chatroom. In contrast to single player mode where users can place towers during and before levels, coop provides a 30 second timer between levels for players to place towers. Once the timer ends, the level begins and the players cannot place towers until after the level ends. The communication between players is made possible by a Django Server hosted on Heroku. A python, django application was written to handle the different game play requests and ensure that chat messages would be delivered to each player as well as the state of the game (when one player places a tower the tower immediately shows up on the other player's screen). The repository for the Django application can be found in the following link:

https://github.com/akyker20/vooga_salad_server


Known Bugs:

There are some bugs in game authoring environment path building. The user cannot, for instance, build a path from an ending location to a starting location. Path components should be connected from the starting location toward the ending locations. 
If the user does make a mistake, however, they can simply reset the Path Building or select and delete components.

In the actual playing of a game, sometimes enemies from the next level spawn before the level is started.

Sometimes towers cannot be upgraded if their range circle goes over the upgrade pane (if the tower is placed close to the bottom of the screen).

When loading the game from a game save, you cannot click on the current towers.


