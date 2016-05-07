Jolly Rodger Gaming:
	Kelton Hislop - khislop@mymail.mines.edu
	Josh Snell - jsnell02@gmail.com
	Stephen Unger - stunger@mymail.mines.edu
	Trevor Worth - tworth@mymail.mines.edu

Final Project - There's An App For That - Beta Release

Description:
	Projectile Mandate allows the player to play a game that is reminicent of Atari's Missile Command. The player attempts to save their city ffrom the incoming missiles.
	The city is composed of four buildings, each of which is destroyed when they are struck by a missile. To shoot down the missiles the player taps the screen to send a missile of their own at the location.
	The player's missile explodes when it reaches the target location. The explosion expands, destroying any enemy missiles that it touches. The player gets a point for each missile that they shoot down. The top
	scores are saved into a local database, and the player can view the top ten scores on our leaderboard.

Usage:
	The user navigates the app though touching various buttons on the screen. The game starts in the MainActivity where there are four buttons to various locations in the app.
	The Exit button will finish the activity and take the user away from the app. 
	The Leader Board button will take the player to a screen that displays the top ten scores in the database in a table. If there are no scores, we just show a text view that says that no scores exist. 
		The player can leave the screen by pressing the back button on their device or the up button in the menu.
	The Options button will take the player to a screen that shows all of the various options that can be set. The player can set the color scheme and game difficulty by setting the corresponding radio butoon. 
		If the player wishes the reset the database, they can do so by pressing the Clear Database button. If the player wishes to return to the main menu, then can press the back button on their device, the up button in the menu, or the confirm button.
	The Start button will take the player to the actual ProjectileMandates. The player's city and turret is located on the bottom of the screen. Missiles will fall from the screen and the player will need to shoot them down.
		The turret can be fired by tapping a location on the screen. If the player taps too close to the ground, then the turret will not fire. The player's missile will move towards the location that they tapped, and will explode once it reaches its destination.
		This explosion is represented by an expanding circle, which will destroy any enemy missiles that it touches. The player can only have five missiles on the screen at one time and the turret will not fire if this limit is reached.
		The player gets one point for each missile that is destroyed, and the current score is shown in the TextView at the top of the screen. The houses that represent the player's city will be destroyed when they are struck by an enemy missile. 
		Once all of the houses are gone then the player will lose the game. If their score is one of the top ten scores, then a dialog will pop up asking the player to enter the name an submit it to the database. 
		Once the player answers this dialog they will see a dialog that asks them if they want to play again. Pressing "Yes" will restart the game while pressing "No" will return the player to the main menu.
		
Compilation:
	Our program does not require any special compilation instructions and should compile normally in Android Studio. 

Bugs:
	The only current issue with the application is that the game screen does not display as nice as we would have liked on low resolution screens. 
	The addition of Sound to our app is one of the later things that we did, so it is not as detailed as we would have wished. 
	It currently only plays a sound when one of your missiles explodes or an enemy missile hits the ground. Also it was rather loud when I tested it so it might be helpful to keep the volume low, especially if you are using headphones.
