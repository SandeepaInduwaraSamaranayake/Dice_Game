# Dice Game
##  A dice game Android application and according to the game rules and the implementation details below.

### @copyright Sandeepa Induwara Samaranayake - MOBILE APPLICATION DEVELOPMENT - Assignment 1 - Informatics Institute of Technology affiliated with the University of Westminster, UK.


###### Application Description
This program will be displaying images of the dice thrown by the two players: the human and the computer. The images found in the following URL is used for this project:
https://developer.android.com/reference/ <br>

## The rules of the game

>+ A human player competes against the computer.
>+ Both players throw 5 dice at the same time.
>+ The score of each throw for each player is the sum of the numbers shown on the faces of the
dice.
>+ The objective of the game is to reach a score of 101 or more (instead of 101 another target
can be set by the human before play starts) by throwing 5 dice as many times as necessary.
>+ After a roll, each player may either score it or take up to two optional rerolls.
>+ For each reroll, they may reroll all of the dice or select any of the dice to keep and only reroll the remainder.
>+ A player may score at any time, thus ending their current throw; after the second reroll (three rolls in total) they must score it.
>+ After both players score their rolls, the procedure is repeated until one of the players reach
101 or more points by summing all of their scores.
>+ If both players reach 101 or more with the same number of attempts (a single attempt is considered as one roll followed by 2 optional
rerolls) the player with the highest score wins.
>+ As an example, assume that the human player scores 30 points in the first attempt (a roll followed by 2 optional rerolls), 25 in the second, 11 in the third, 28 in the fourth and 15 in the fifth achieving a total score of 109 in five attempts.
>+ If the computer did not score 101 in four attempts or more than 109 in five attempts, the human wins.
>+ In the case that both the computer and the human achieved the same score in the five attempts (i.e. 109 in the example), each player throws for a last time all five dice and the player with the highest sum in that roll wins (no optional rerolls are allowed in this case).
>+ In the case of a tie again, both players keep rethrowing all five dice until one of them wins.

1. When the application starts, it presents the user with 2 buttons labelled New Game, and About.

<img src="https://github.com/SandeepaInduwaraSamaranayake/Dice_Game/assets/95087710/607322d0-052e-4bf1-bf8e-25ce9d44ec5f" style="display: inline-block; width: 40%;" />

2. Clicking on the About button should present the user with a popup window which describes the author (student id and name) and the message:

"I confirm that I understand what plagiarism is and have read and understood the section on Assessment Offences in the Essential Information for Students. The work that I have submitted is entirely my own. Any work from other authors is duly referenced and acknowledged."

<img src="https://github.com/SandeepaInduwaraSamaranayake/Dice_Game/assets/95087710/ecf11076-a2b4-405d-ab75-bdc9580df1fe" style="display: inline-block; width: 40%;" />

3. Clicking on the New Game button, the user will be presented with the game screen which they interact with. The screen contains 2 buttons labelled Throw and Score. Each time the Throw button is pressed, a simulation of throwing 5 dice by both the human player and the computer is performed simultaneously. The images of the five dice rolled by the human player and the five dice rolled by the computer are displayed. Each of the dice images should be selected randomly with an outcome of a number between 1 to 6. Not all of them need to be unique, as one roll of 5 dice may result in 6, 1, 4, 4, 2, i.e. the same outcome representing 4 on the face of a die could be generated for two different dice.

<img src="https://github.com/SandeepaInduwaraSamaranayake/Dice_Game/assets/95087710/a3b2e665-3d0d-4fe4-b4fb-4cc12333df99" style="display: inline-block; width: 40%;" />


4. The human player may choose to score by pressing a button Score or take up to two optional rerolls (see below). As soon as the player clicks on Score the total score for the current game should be updated for both the human player and the computer player (also the dice for the computer player should be updated according to the computer player strategy, see below).<br>

5. The current total score for the game (both human and computer) is displayed on the top right of the screen. (You can change the target score by tapping the edit button next to the target score)

<img src="https://github.com/SandeepaInduwaraSamaranayake/Dice_Game/assets/95087710/8bb1df4c-3f2f-44ef-8fdd-6318b1eca85d" style="display: inline-block; width: 40%;" />

6. If the user performs the maximum of 3 rolls for that turn, the score is updated automatically without the need to press the Score button (see the rules of the games described above).

<img src="https://github.com/SandeepaInduwaraSamaranayake/Dice_Game/assets/95087710/ada03785-9d61-428c-b53a-29e310651092" style="display: inline-block; width: 40%;" />

7. For each of the 2 optional rerolls, the human player should be able to select (it is left up to you to design the appropriate user interface for this) which dice (if any) he would like to keep for that roll. After selecting this, the human player should press the Throw button again and the dice which have not been selected for keeping should be rerolled.

<img src="https://github.com/SandeepaInduwaraSamaranayake/Dice_Game/assets/95087710/2614091b-a137-44f6-abc1-95beeb4762dd" style="display: inline-block; width: 40%;" />

8. The computer player follows a random strategy. I.e. first it decides randomly whether it would like to reroll (up to a maximum of 3 rolls per time) and if this is the case it decides randomly which dice to keep. A single (first) roll for the computer player occurs and it is displayed only after the human player clicks on the Throw button. If the human player clicks on the Score button, the computer player uses all of its remaining rolls for that turn according to the random strategy, i.e. the final result of the five dice is displayed after the computer has used (optionally based on the random strategy) the 2 rerolls (for a total maximum of 3 rolls).

9. When a player (human or computer) reaches 101 or more points a pop up window with the message “You win!” is displayed in green colour (if the human wins) or “You lose” in red colour (if the computer wins). You should implement the exact rules of the game described in the Section “The rules of the game” above.

<img src="https://github.com/SandeepaInduwaraSamaranayake/Dice_Game/assets/95087710/296c96bf-6fd6-40c8-8650-bb173e0fe1ed" style="display: inline-block; width: 40%;" />

10. As soon as a winner is determined, the game is not playable any more. In order to start a new game, the user needs to press the Android “Back” button to move to the initial screen of the application from where a new game can be started by pressing the New Game button.
11. The 2 players keep rolling until the tie is broken.
12. when the device is rotated from portrait to landscape and back to portrait mode. I.e. the application resumes from exactly the same point (same screen and data) when the orientation changes. The rotation of the device does not change what the user was seeing before the rotation, and the state of the application, including the score, will be fully restored.
