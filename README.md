# Tic-Tac-Toe
Made in Java and Java Swing UI
 Functionalities
------------------------------------------------------
1.  a) play a local game against a friend
    b) play a local game against an AI player
    c) launch a network game
    d) join a network game

2. Local game against friend
    Pick the P vs P mode to play in this game mode.

3. Game logic/flow
    Game plays as a normal Tic Tac Toe game with all normal logics

4. Win/draw algorithm
    Game checks Winning and Draw conditions without any issue.

5. Play again
    A promt is given to the user if he/she is willing to continue playing.

6. User interface: Display information
    The game window will show the Player names, player scores, Draw score and the Which turn is currently now.

7. User interface: Controls
    All the menu windows are linked correclty and user can chose any way of playing.

8. Local game against AI player
    As a basic AI first implimented a simple random turn AI.

9. Random first player
    Begining of each round the first player is chosen randomly and Displayed on the Players Details panel with a Blue Rectangle.

10. Player scores
    10 points is added to players score when he win the game
    5 points is add extra if he got another combination of winning strike other than the first one. (Improvement)
    Tie score represent how many game was Draw.

11. Game logs
    A log file is generated whenever user start the new game until he decided to quit the game.
    logs are stored in \Game Logs\Log_yyyymmddhhmmss.txt format.
    if there is no Game Logs folder the game will automatically create that folder.

12. Variable board size
    Player can chose 3x3 , 4x4 , 5x5 Board size and is available in P Vs AI and P Vs P modes
    The game board is capable of handling any game size bigger than 3, its created dynamically and if we want to implement-
       game size 10 x 10 all the algoithm will work without any problem.

13. Sound is added to every window and game play, there is sound seperate for winning, lossing and tie.

14. Game Logo images and background image is added for the Menu.

15. Each game window is created properly and used proper alignment and fonts.
