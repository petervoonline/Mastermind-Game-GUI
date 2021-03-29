CSC 335 Project 4: Mastermind GUI
  
=================
Project Description
------------
For this project, we will use JavaFX to create GUI for our Mastermind game. 
We will construct a new MastermindGUIView that is a javafx.application.Application.
Here is a mockup of the layout:

When you win or lose, display a modal (showAndWait()) javafx.scene.control.Alert:
![image](https://user-images.githubusercontent.com/60068763/112902217-adca1f00-909a-11eb-8894-5bc7fd23190d.png)

After the game is over, do not allow any further guesses.


Example Run
------------
	Enter a(n) Adjective: interesting
	Enter a(n) Noun: frog
	Enter a(n) Plural Noun: dogs
	
	There are many (INTERESTING) ways to choose a/an (FROG) to read. First, you
	could ask for recommendations from your friends and (DOGS).
	
	Puzzle complete! Would you like to play again?

Implementation
------------
