=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
Game Project README
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. You may copy and paste from your proposal
  document if you did not change the features you are implementing.

  1. 2D Array

- What specific feature of your game will be implemented using this concept?

The mine field will be implemented using a 2D array. This is a pretty clear and efficient choice as I will be able to easily access the different tiles in the rows and columns of the array which make up the mine field, checking whether they are revealed or not, and what type of tile they represent.

- Why does it make sense to implement this feature with this concept? Justify
  why this is a non-trivial application of the concept in question.

The mine field should not be able to be resized once the game starts, making the 2D array a perfect choice. Also, the ease of access to each cell using the indices of the array also make the 2D array a logical choice to represent the mine field.

  2. Recursive Algorithm

- What specific feature of your game will be implemented using this concept?

When you click on a tile that is not adjacent to any bombs, it clears out the whole area surrounding it plus it does the same for any surrounding tiles which are also not adjacent to any bombs. Therefore, as the algorithm is going through each tile surrounding the current tile, it will call itself on any tile that is also not adjacent to any bombs.

- Why does it make sense to implement this feature with this concept? Justify
  why this is a non-trivial application of the concept in question.

This makes sense because we want to clear the area around any isolated tiles, including ones we encounter while clearing the current one. As a result, it is extremely useful to use recursion to also clear any tiles that fit this specification while we are clearing the current one.

  3. Complex Search of the Game State

- What specific feature of your game will be implemented using this concept?

I will implement an AI of sorts, which will assist the user via a “Help” button (note: I will have a separate instructions button so this isn’t mistaken for help with controls or directions) during the game. This button will be useable only a limited amount of times during each game. The algorithm behind this function will search through all of the revealed tiles (i.e. it does not cheat for you, it “sees” what you see) and will identify, through the placement of a flag, where bombs are known to be. This will be useful when playing on a large grid and the player does not want to take the time to analyze each individual hidden tile to figure out whether a bomb is behind it.

- Why does it make sense to implement this feature with this concept? Justify
  why this is a non-trivial application of the concept in question.

The algorithm behind this “Help” button will need to do a complex search of the game state by searching through each tile in the grid. For each tile, it will see if it is revealed, then, if it is revealed, will begin the process of analyzing the surrounding tiles and their degrees, and well as whether they are revealed or not. It will use this information to determine whether or not there is clearly a bomb present. Clearly, this will be more useful in some situations then others. If the user has only a few tiles revealed, they will not accomplish much by asking for help. However, if they have a good bit revealed, they will have a lot of work done for them.

  4. Testable Component

- What specific feature of your game will be implemented using this concept?

I will be testing Core Concept 3; specifically, I will be testing the algorithm I created to give the positions of some bombs to the user based on their current situation in the game.

- Why does it make sense to implement this feature with this concept? Justify
  why this is a non-trivial application of the concept in question.

This makes sense to be tested because there are a large amount of edge cases this algorithm can face as it goes through the mine field, including corners and sides of the board, as well as the different possible degrees that can surround the tile being analyzed. Also, the decisions it makes regarding positions of bombs will be critical to test to make sure it doesn’t tell the user there is a bomb where there isn’t one; it must use only the information the user has to make its decisions and not access any information that is hidden to the user.


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

Tile - This class creates the tiles which make up the mine field. All tiles store state which tells other classes whether or not they are revealed, what their degree is (how many bombs are around this specific tile), and whether or not they have been flagged. There are setters and getters to access these fields, which keeps the private state encapsulated. Additionally, the paint component method of JPanel is overridden so I could specify how the tiles should draw them selves, based on their state.

Field - This class stores the 2d array of Tile instances which make up the field, as well as other pieces of state which dictate the gameplay. This is the class that ultimately controls everything in the game. The Field class contains the code to prepare the field, as well keep track of the time, win/lose conditions, and other pieces of the game. Finally, the paint component method of JPanel is overridden in Field so it can be drawn properly in the Frame created in Game.

Game - This class serves the same purpose as it did in mushroom of doom. It sets up the GUI with the widgets and buttons we need to pay the game. This class does not directly interact with the Tile class, as the Field class handles most of the action when it comes to gameplay and in doing so deals with the Tile class.

HelpTest - This class stores JUnit tests for the help() method in Field.

- Revisit your proposal document. What components of your plan did you end up
  keeping? What did you have to change? Why?

I kept all of the components of my game the same, except for Core Concept 2, which I switched from File I/O to a Recursive Algorithm. I did this because recursion fit perfectly as a solution into my implementation of clearing tiles which had no bombs surrounding them.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

Yes. I struggled in the beginning of my implementation to find a way to “prepare the tiles” to be placed in the field. I needed to create the Tile instances, assign bombs, assign degrees, and finally add mouse listeners. Initially I had these as three different helper functions when the field was set, and it was very slow. There were 6+ for each loops running to set the board. After refactoring, I was able to cut this down to one helper method, and only 2 for each loops.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

Yes. Each class (Field and Tile) encapsulate their private state, and only make it available through public methods. For example, although the Game class constructs the GUI which contains Tile instances, it never directly interacts nor has access to any state in Tile. The Field class handles these interactions, and it is allowed to access the state of Tile only through public methods, not through direct access to the fields of Tile.

I do not think I would refactor anymore than I already have at this point. I am happy with my design at this point. However, I am sure there are certain facets of my design that could be improved with the help of a fresh set of eyes looking over my code.

========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.

N/A
