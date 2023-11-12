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

