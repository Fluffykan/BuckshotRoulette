Last Updated On : 7/5/2024

Hi there, I see you have wandered into this google drive...
Hopefully you clicked into this file before you started digging for wherever the hell "app.Main.java" is...
So the main file is in the folder called src, just click in and you'll find it.

Basically if you haven't heard of it, this game is a ripoff of Buckshot Roulette, a 1P game where you play against a Computer.

How to run the game:
I realised after a while that nobody knows how to run the game, so heres the instructions...
Firstly you have to get your hands on an IDE, basically anything that allows you to look at code and compile it
Some common examples are IntelliJ and VS Code, which you can just find on the internet and download.
Then open the file called app.Main.java and hit the run button to run the java file, and everything should be good to go
Lmk if theres any problems

Objective:
Stay alive and kill the dealer for 3 rounds by any means possible (at least that's the default setting, you can change it if you want to)

About the game:
If Buckshot Roulette has somehow avoided you on Youtube / Twitch, or because you simply don't go on Youtube / Twitch, its a game where you try to kill the Computer aka. app.Dealer for 3 rounds.
There is a gun which is loaded with blanks (bullets that do no damage) and live rounds (bullets that do damage) in an unknown order, BUT you do know how many of each are loaded every time.
So you are basically trying to guess what the next bullet is (blank / live) and aim to only shoot the dealer with live rounds to kill it.

If you shoot yourself with a blank, you get another turn, i.e. you can use more items and decide whether to shoot the dealer or yourself with the next bullet.
So let's say you know the next round is a blank, obviously you won't want to shoot the app.Dealer with something that does no damage, so you will instead shoot yourself with a blank.
HOWEVER... if you shoot yourself with a live round, your turn ends, and its the dealer's turn to make a move (i.e. use items / shoot).
MOREOVER... if you choose to shoot the dealer, regardless of what comes out (blank / live) your turn ends.

If you select "Exit Game" the game autosaves.


The line below will indicate what the current version of the game is, so look for the details of the game under the relevant version of the game below
-----CURRENT VERSION: 1.0-----

Ver 1.0 Contents:

Turn order: 
You will ALWAYS take the turn before the dealer.

Tools to help you kill the dealer / stay alive (which are also accessible by the dealer):
1. Cigarette : gain 1HP on use
2. Beer : ejects the next round from the gun
3. Handcuff : causes the app.Dealer to be unable to act (i.e. no shooting / using items) for 1 turn, BUT opponent will break free if you shoot it with a live round.
4. Saw : doubles the damage of the next shot (yes... 2 * 0 = 0 so if you fire a blank, the target still takes no damage)
5. Magnifying Glass : tells you what the next bullet is (i.e. blank or live)

You can use MULTIPLE items in one turn, and they are non-refundable, 
i.e. if you use magnifying glass twice the game will not stop you, and you have basically checked the next round twice,
i.e. if you handcuff the dealer twice in one turn the game will not stop you, and you have effectively wasted one handcuff.

Difficulty Mode: INSANE
The app.Dealer knows what the next bullet is going to be, so it will ALWAYS make the best move (at least thats what I think it will do).
So you just have to rely on pure skill and abit of item drawing luck I guess :)
Also there is no way to turn the difficulty down currently :)

Game Specifics:
app.Dealer health: 5
Your health: 5
Items per round: 6
Items gained on every gun reload: 1

Saving game progress: only when you select "Save Game" or "Exit Game"
Clearing previous save data: only when you win the game i.e. beat the app.Dealer for 3 rounds, or if you choose to not load the previous game progress

That should be about it for V1.0... GLHF :)
