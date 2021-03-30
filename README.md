Mastermind GUI
=================
Project Description
------------
This project uses JavaFX to create GUI for our Mastermind game. 
We will construct a new MastermindGUIView that is a javafx.application.Application.
Here is a mockup of the layout:

When you win or lose, display a modal (showAndWait()) javafx.scene.control.Alert:
![image](https://user-images.githubusercontent.com/60068763/112902217-adca1f00-909a-11eb-8894-5bc7fd23190d.png)

After the game is over, do not allow any further guesses.
![image](https://user-images.githubusercontent.com/60068763/112902370-deaa5400-909a-11eb-878d-7c362b0df097.png)

Implementation
------------
  Main Board
  ------------
  The main stage will be 600 pixels (px) high and 400px wide. It should be a BorderPane with a center 
  and bottom. The center position will be a VBox, a container that stacks its contents vertically on top of 
  each other. Set the background color to Tan and have a 10px Inset around the outside of the VBox, with 
  10px of spacing between rows.

  The bottom pane will contain a GridPane with four Circle shapes, each initially Black. The fifth 
  column will contain a Button with the text “Guess”.

  When you click guess, a new GridPane will be added to the center VBox. Inside of this GridPane
  should be a Text shape with the guess number in a size 20 font. Next, a copy of the guess’s four colored 
  Circles should appear. The final column should contain a 4x4 GridPane that displays the Right Color, 
  Right Place as the appropriate number of Black Circles and the Right Color, Wrong Place as the 
  appropriate number of White Circles. There is no need to match positions in the 4x4 grid to the
  colored peg positions. Here is an example after one guess:
  ![image](https://user-images.githubusercontent.com/60068763/112902548-20d39580-909b-11eb-8049-cf1309fa7883.png)

  For the 4x4 feedback grid, have a 5px Inset around the outside and an Hgap (horizontal gap) and Vgap
  of 5px.

  For both the bottom GridPane as well as the guess history GridPane, we want all the columns to be 
  equally spaced. Use a ColumnConstraint to set the columns to 20% width for the bottom GridPane
  and 1/6=16.67% for the guess history GridPane. Set the horizontal alignment on the 
  ColumnConstraint to center.

  Event Handling
  ------------
  You will add a MouseClicked handler to each Circle. When you click a Circle, it should cycle 
  through the colors of the game: Red, Orange, Yellow, Green, Blue, Purple, and then back to Red.
  When the “Guess” Button is clicked (ActionEvent) take the current guess score it and display the results 
  in the VBox history. Reset the four pegs back to Black and have them cycle through the colors, starting 
  again with Red.

  If the Guess button is clicked with any peg still Black, display an alert box (the same as the win box 
  above) that informs the user they must pick four colors. Do not let them submit a guess until all four 
  pegs are set to a color.

  MVC 
  ------------
  You are required to continue to use the MVC architectural pattern. You must have the following 5 
  classes:
  
          1. Mastermind – This is the main class. When invoked with a command line argument of “-text”, 
          you will launch the text-oriented UI. When invoked with a command line argument 0f “-
          window” you’ll launch the GUI view. The default will be the GUI view.
          
          2. MastermindGUIView – This is the JavaFX GUI as shown above.
          
          3. MastermindTextView – This is the UI that we built in project 2, moved into a separate view 
          class.
          
          4. MastermindController – This class contains all the game logic and must be shared by the 
          textual and graphical UIs. You may not call into different controllers from the different UIs and 
          you shouldn’t change the methods from the ones we already found useful.
          
          5. MastermindModel – This class contains all the game state and must be also shared between 
          the two front ends.
