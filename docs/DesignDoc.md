---
geometry: margin=1in
---
# WebCheckers Design Documentation

# Team Information
* Team name: Grade A Slackers
* Team members
    * Kevin Paradis
    * Dan Wang
    * Emily Lederman
    * Nathan Farrell

## Executive Summary

WebCheckers, as the name suggests, is a web based application which allows users to play a fully functional game of checkers with other signed-in players. The project utilizes the Spark micro web framework and FreeMarker Template Engine to circumvent the burden of writing boilerplate code, which allows functionality to be the primary focus. The high level architecture chosen for the project is a multi-tier pattern which consists of the UI, Model, and Application tiers to provide a logical separation of class responsibility. The Agile development cycle was used to incrementally implement the different functionalities of the MVP and additional features.

#### Purpose
The WebCheckers application aims to create a browser based environment where users can play a game of checkers with their peers. The gameplay must adhere to the standard American rules.

### Glossary and Acronyms

| Term | Definition |
|------|------------|
| VO | Value Object |
| MVP | Minimum Viable Product|
| UI | User Interface|
| AJAX | Asynchronous JavaScript and XML |


## Requirements

This section describes the features of the application.

### Definition of MVP
The MVP is an application where users can sign in to the application, choose an opponent, and then play a fully functional game of Web Checkers in accordance with the standard American Rules of Checkers. At any time, a user currently in a game may choose to resign, thus ending the game early.

### MVP Features
 - Sign In: Players can enter the application with a username. The system will reserve their name until the user chooses to sign-out
 - Resignation: Resign from an active game, the user who resigns forfeits the match
 - Start a Game: The user will choose an opponent from a list of signed-in players. Once chosen, if the selected opponent is not currently in a game, then the two players will be redirected to the Game View and a game of checkers will start
 - Validating Moves: This epic details the validation of moves given by a player to ensure that they do not contradict the rules of the game
 - Moving Pieces: This epic covers the functionality required to move a piece, capture pieces, and make jumps
 - Ending a Game: Another epic, this one details the conditions for which a game would end, whether by player resignation or by a player victory



### Roadmap of Enhancements
To enhance our product, we intend on adding:

 - Spectator Mode: The user will choose from a list of ongoing games. Upon selecting a game, the user will be able to view the games progress in real-time
 - Replay Mode: The user will choose from a list of their previously played games to re-watch. During the replay, the user can iterate through each move made by them and their opponent, with the option of going backwards and forwards through the moves

## Application Domain

This section describes the application domain.

###Overview of Major Domain Areas

![Domain Analysis](https://lh3.googleusercontent.com/--WYhHesJGOA/WgGIojUN2EI/AAAAAAAAADA/1NbjWNeL9cwiF4wTF-sD5jSmR5bsjPiwACLcBGAs/s0/Domain+Analysis+-+Page+1.png "Domain Analysis.png")
*Fig 1. Domain Analysis of the WebCheckers project*

Some key domain areas to mention include the Game Board, Rule Set, and Game entities.

### Details of each Domain Area

The Game Board entity is tasked with keeping track of the current state of the game such as whose turn it currently is. The board is where the physical game takes place; where Squares are organized and where Pieces sit. This entity is perhaps the most important in our domain as it details vital information about the state of the Game, such as where Pieces are in relation to one another. This information helps the Player to decide what is the best Move to make during the Game. The Board contains all 64 Squares (8 rows of 8 Squares) and the starting configuration of a new checkers game places 24 total Pieces (12 of each color) in the proper start position. As the Game is played, more Pieces will be removed until either one of the Players has no Pieces left, or none of that Players Pieces can move.

The Rule Set entity serves to ensure that the Game is played according to standard American rules. As such, this entity is responsible for stopping Players from making Moves which do not adhere to those rules.


## Architecture

This section describes the application architecture.

### Summary
The WebCheckers webapp uses a Java-based web server. The Spark web micro framework and the Freemarker template engine are utilized to handle HTTP requests and generate HTML responses. The architecture of the project is formed in the Tiers and Layers pattern, and consists of UI, Application, and Model tiers. The User of the webapp interacts with the UI tier, which in turn interacts with the Application and Model tiers. The Application tier contains the logic that controls the application flow, while the Model tier contains business logic and data associated with the application.

![Architectural Model](https://lh3.googleusercontent.com/-B8mAliA3WZg/WiWNnLxW-iI/AAAAAAAAAFE/vxFoCe1B-SEGoOSvIGikZ3AA1IDOlN3AgCLcBGAs/s0/Arch+model+New+-+Page+1.png "Architectural model")
*Fig 2. Diagram of the WebCheckers architectural structure with examples of each component*


### Overview of the User Interface
The application's user interface is comprised of three main states: Home, Sign-In, and Game.  Upon establishing an HTTP connection, it renders the home page, which displays the number of players online and provides a link to sign-in. Users can then sign-in on the sign-in screen. Signing-in with a unique username leads them back to the home page. This page now displays a selectable list of players, which does not include the current user.  The current user can then select a player to challenge to a game. If that player is not available, then the user is returned to the home page. Otherwise, both players are taken into the game view. The players remain in the in-game state until the game is terminated, whether it be by resignation, sign-out, or a player winning. The players are then returned to the home view. Alternatively, the player can choose to spectate an ongoing game from a list of games being played. The player is sent to the game view of the red player in that match, however they are unable to interact with the board in any way. If the game ends, they are sent back to the home screen. If they are challenged to a game while spectating, they can return to home to be sent to that game.

![User Interface State Chart](https://lh3.googleusercontent.com/-6tT6jnjV6xg/WiYKdxM_XyI/AAAAAAAAAJQ/SUEaMZeluOY-HOnMy7QQyIKM8KHxFTc6ACLcBGAs/s0/User+Interface+%25281%2529.png "User Interface")
*Fig 3. State chart illustrating the different UI Views*

### Tier: UI
The components of the UI Tier, as a whole, work to provide the client with an interface to view content and enter data. The content that is displayed by the UI is drawn from other tiers, however in some cases, conversion of this content must be performed. The UI tier is responsible for this conversion as well as for initiating requests given by the user.

The UI Tier of the WebCheckers project consists of several packages.

 - AjaxRoutes: These routes allow the client's browser to exchange information with the server without rendering a new page each time. These routes are only used during gameplay and help ensure a smoother experience in which the client is not forced to load a new page for every action (i.e. validating a move, checking the turn, undoing a proposed move).
	 - PostBackupMove
	 - PostCheckTurnRoute
	 - PostResignRoute
	 - PostSubmitTurnRoute
	 - PostValidateMoveRoute
	 
 - BoardView: The components of this package are used to transform information from other tiers into a format necessary to display the game to the players. For example, the classes BoardView and Row take in the Board model and return an iterable collection that is used to display the board in-game. The other components help to format the information that is received by the client so that the server can perform operations on said data.
	 - BoardView
	 - Message
	 - Position
	 - Move
	 - Row
	 
 - Routes: The HTTP request routes are responsible for displaying views (a GET request) as well as for exchanging data from the client (a POST request). Unlike the Ajax routes, these require a new view to be generated each time. An example of this would be in the GetGameRoute which gathers all of the information the server has about a players game from other tiers and turns that into the GameView which is where the player would see the graphical interface of a checkers game.
	 - GetGameRoute
	 - GetHomeRoute
	 - GetSigninRoute
	 - GetSignoutRoute
	 - GetSpectateRoute
	 - PostSigninRoute
	 - WebServer

### Tier: Model
The Model tier contains the components which make up the Board entity. Unlike the aforementioned BoardView components, entities in this tier actually hold data, in order to maintain the state of the game as well as to effect change based on user input. This tier is responsible for rendering the internal representation of the Board, Piece, and Space components. All of these components are located within the board package within the model (com.webcheckers.model.board).

![Structural Class Model](https://lh3.googleusercontent.com/-yJi1anB6FbA/WiWaDA2ClcI/AAAAAAAAAFY/ANNLF31B6foushqBaGIpq-A3brpvg2vzwCLcBGAs/s0/Board+Class+Structure+-+Page+1.png "Board Class Structure")
*Fig 4. Structural Class Model of a Board*

The Space class is what is used for representing an area on a board. Each Space contains information like the row/column location, the color of the Space, and the Piece that sits on the Space (if present). The Piece itself is represented in the Model tier as well. 

The Piece class has little functionality, but holds data regarding what type of Piece it is or to what color Player it belongs to. This information is used to determine how a Piece can be moved across the Board.

Outside of the board package, there is also a Player class that represents an individual Player. The Player class works with application tier services to perform actions like such as signing-in and selecting an opponent to play against.

A Validate class is also included outside of the board package. This class is assigned the responsibility of ensuring that proposed moves adhere to the rules of checkers before they are allowed to be made. Another responsibility held by Validate is that it checks a given board configuration to see if a player has won the  game. While it can be argued that this class should not take on that responsibility, we chose to put it within this class as it allowed us to maintain a high level of cohesion and single responsibility within our other classes. Additionally, we did not feel that it was the responsibility of the Board class to enforce the rules, but rather to serve as a representation of the current game space. 

### Tier: Application

The components of the Application tier work to manage information and logic which span the entirety of the application. Additionally, these components are responsible for providing client specific services needed by the UI tier.

The Application Tier consists of the CurrentGames, Game, and PlayerLobby component. The first two components are responsible for managing information associated with all ongoing games such as the players within a given game, as well as the current configuration of the board. These components provide services related to starting and ending a game as well as spectating. The PlayerLobby component is responsible for keeping a list of every signed-in user and has services which log-in and log-out users. All of the mentioned components have a shared responsibility of providing information to the UI tier in order to generate the appropriate view.


##Sub-system: CurrentGames

### Purpose of the sub-system
The CurrentGames sub-system is a system within our WebCheckers application that is responsible for the collection of all ongoing games. This system is responsible for many services throughout the application like providing the GetHomeRoute with the information to deny users from entering games with players who are already in a game, as well as provide all information necessary to carry out the functionality of playing a game.  With the total collection of Games, you can keep track of the states of all games at once. Any sort of move made or player action is both carried out and recorded by the CurrentGames sub-system. 

### Static model

![CurrentGames Sub-system](https://lh3.googleusercontent.com/-Fv-FTtKVTxs/WiWzIZnE6GI/AAAAAAAAAFs/u-yuZA_oWaYmUj5gvnNJbOpQpz4TPRIDACLcBGAs/s0/CurrentGames+Sub+System+-+Page+1+%25281%2529.png "CurrentGames Sub System")
*Fig 5. Structural Class Model of the CurrentGames sub system*

### Dynamic model
![CurrentGames-Route Interaction](https://lh3.googleusercontent.com/-AYGmmr1AovM/WiXT4T3H5RI/AAAAAAAAAG0/uobg4CeOc4o0jG2njisVfR95Hx3OfQXrgCLcBGAs/s0/Blank+Diagram+-+Page+1.png "CurrentGames Route Interaction")
*Fig 6. Diagram detailing the interactions between sub-system and UI Routes*

![CurrentGames State Chart](https://lh3.googleusercontent.com/-QGUp9nmcTZo/WiXEEqJxFUI/AAAAAAAAAGA/iJ9aZkW2-AAKVyykDLKYpqt44JFwv25CACLcBGAs/s0/CurrentGamesStateChart+-+Page+1+%25281%2529.png "CurrentGames State Chart")
*Fig 7. State chart diagram detailing how CurrentGames is utilized during a user's gameplay experience. Each user that signs-in and plays a game will be using CurrentGames in this manner.*

![CurrentGames Sequence Diagram](https://lh3.googleusercontent.com/-HMH4Xcwl3XM/WiXSRnU238I/AAAAAAAAAGg/YMhf0K-xiTE9yFpYhb71S_6fHzU3vkSXACLcBGAs/s0/CurrentGames+Sequence+-+Page+1+%25281%2529.png "CurrentGames Sequence Diagram")
*Fig 8. Sequence diagram detailing how CurrentGames works in the case for when a player selects an opponent who is not in a game*

###Other Designs Considered
While not a radically different design, one of the other means of gaining the same functionality we considered was to have Players track their own ongoing games instead of CurrentGames doing it. This would mean getting rid of CurrentGames entirely. The benefits of this approach would be that we didn't have to increase the amount of dependencies by creating an additional class. The downside to this approach is that the functionality of PlayerLobby would have to be extended in order to get the needed information from a player to start a game. In the end, we decided to create create CurrentGames to promote high cohesion between both the Player and PlayerLobby classes. We decided that it was also not the responsibility of the PlayerLobby class to retrieve all of the information needed by the GameView and that it should be focused on tracking signed-in Players and handling sign-in/sign-out requests.

## Sub-system: Player Sign-in/Sign-out

This section describes the detail design of the Player Sign-in/Sign-out sub system.

### Purpose of the sub-system

The Player Sign-in/Sign-out sub system delivers the functionality needed to sign-in under a desired username and then later sign-out when the player chooses. Once signed-in, the Players information is stored in a collection of players within the PlayerLobby component. This component is used by various UI routes to determine what information should be displayed. A signed-in player at the home page will see a list of other signed-in players which is used to select an opponent and start a game. When the player decides to, they can sign-out and release their username for other players to use.

### Static models

![Signin/Signout Class](https://lh3.googleusercontent.com/-V72WrmKUss0/WiXUqlcF2AI/AAAAAAAAACA/tp26tGz0U_Mi9yk0QzRC2l5xCxTI106AwCLcBGAs/s0/Blank+Diagram+-+Page+1%25281%2529.png "Blank Diagram - Page 1&#40;1&#41;.png")
*Fig 9. Structural Class diagram of the Player Signin/Signout sub system*

### Dynamic models

![Signin/Signout Sub System](https://lh3.googleusercontent.com/-5eJwo52iVGY/WiYBI3-gTmI/AAAAAAAAAI8/n-FSch1GZgog7rkuxBoEsfpkAOmFkBirwCLcBGAs/s0/SigninSignout+Sub+System+-+Page+1+%25281%2529.png "SigninSignout Sub System")
*Fig 10. Diagram detailing the interactions between sub-system and UI Routes*

![Signin/Signout Sub System Dynamic](https://lh3.googleusercontent.com/-SFpSnHwJYCU/WgFREFVdzmI/AAAAAAAAAAg/S5iz0MtOQCApko98uVpaq3JKJRsWQFhDgCLcBGAs/s0/SignInSignOutDynamic+-+Page+1.png "SignInSignOutDynamic.png")
*Fig 11. State chart diagram of the Player Signin/Signout sub system*

![enter image description here](https://lh3.googleusercontent.com/-K_ATPhVzBgs/WiX6CjjirGI/AAAAAAAAACY/D1eXLDuZ9koxrfdYs3Vqbtr4Hu6gIawugCLcBGAs/s0/PlayerSignin_Out+Sequence+Diagram+-+Page+1.png "PlayerSignin_Out Sequence Diagram - Page 1.png")
*Fig 12. Sequence diagram of the Player Signin/Signout sub system. The sequence shows a user signing into the system and then signing-out right after*


## Sub-system: Start a Game

This section describes the detail design of the Start a Game sub system.

### Purpose of the sub-system

>This sub system is composed of two smaller sub systems, Player Sign-in/Sign-out and CurrentGames. For more detailed information of those sub systems, checkout their dedicated sections of this document.

The Start a Game sub system contains the functionality required to connect two players together in a new game of checkers. One player, at the home page, will select from a list of opponents which is generated by the PlayerLobby component of the Sign-in/Sign-out sub system. Assuming the selected opponent is not already in a game, the CurrentGames component of the CurrentGames sub system will create a new Game object consisting of the two players and a newly configured Board component. The Game View is created using information from CurrentGames and a reformatted representation of the Board data which is created by the BoardView and Row components. Once all of the necessary information is entered and the correct Game View is generated, both players are redirected to the game, thus starting the match.

### Static models
![Start a Game Class Structure](https://lh3.googleusercontent.com/-tFAMX61VGDA/WiXdIjae5II/AAAAAAAAAHk/a1ICCnzAdXMcAtWy7oIdca7JojGljWEjwCLcBGAs/s0/Start+a+Game+Class+Structure+-+Page+1.png "Start a Game Class Structure")
*Fig 13. Structural Class Model of the Start a Game sub system*

### Dynamic models
![Start a Game](https://lh3.googleusercontent.com/-HvxTmqGmzCM/WiXgS-U6ubI/AAAAAAAAAII/KnnU0PU79DI7n4rmfBngj8hJ358i8XsZwCLcBGAs/s0/Create+Game+System+-+Page+1+%25282%2529.png "Create Game System")
*Fig 14. Diagram detailing the interactions between sub-system and UI Routes*

![StartGame dynamic](https://lh3.googleusercontent.com/-qMelZA2Sth8/WgFjKffivQI/AAAAAAAAAA0/Mx5zDPva0lgsKDNTxfTUM21dPpm8jKMiwCLcBGAs/s0/StartGame+sub+system+dynamic+-+Page+1.png "StartGame sub system dynamic.png")
*Fig 15. State chart diagram of the Start a Game sub system*

![Start a Game Sequence Diagram](https://lh3.googleusercontent.com/-2qmq7q6tf98/WiXbkXbziMI/AAAAAAAAAHU/h6lVXwMa2o82n6Gl1ySj6_t0aveI_Ia5wCLcBGAs/s0/CurrentGames+Sequence+-+Page+1+%25282%2529.png "Start a Game Subsystem")
*Fig 16. Sequence Diagram for starting a game when the selected opponent is not currently in a game.*

## Sub-system: Validate a Move

This section describes the detail design of the Validate a Move sub system.

### Purpose of the sub-system

The Validate a Move sub system allows a user generated move to be passed in through an AJAX route in order for the model to determine if the move is legal given the standard, American rules of checkers. Within the PostValidateMoveRoute, a Move object is gotten from the client before it is passed into the CurrentGames service. From here, CurrentGames finds the game associated with that client and then creates a copy of the current board configuration to pass to the Validate model tier component. This class, given a move and a board config, checks that the proposed move does not violate the rules of checkers. Should the move be declared invalid, the Validate component will return a message indicating what rule has been broken. This message gets returned to the PostValidateRoute so that the system can display it to the user, allowing them to understand why they are unable to make said move. 

The Validate component contains a series of checks that enforce different rules. An example of this is the check that is performed for if a jump can be made. The system iterates through the board model, finding each piece that belongs to the player whose turn it is. When a piece is encountered, that has an opponent adjacent to it, it then sees if a jump can be made. Valid jump moves are saved and then used to determine if the player has made a jump. If the jump isn't in the list, the Validate component will return a message stating that no valid jumps currently exist.

### Static models
![Validate Class Structure](https://lh3.googleusercontent.com/-OMMjspFHyLs/WiXjGK1u94I/AAAAAAAAAIc/9BMR-fSfr-oENXr4txyo5YAQKUTqonx8wCLcBGAs/s0/Blank+Diagram+-+Page+1+%25281%2529.png "Validate Move Subsystem")
*Fig 17. Structural Class Model of the Validate sub system*

### Dynamic models
![Validate Class](https://lh3.googleusercontent.com/-oaQTpndHosA/WgzGvfZ7AuI/AAAAAAAAADs/Zoakc1k6evYOEiF5E1X170RV9-G8R6LUACLcBGAs/s0/Validate+Move+System+-+Page+1+%25282%2529.png "Validate Move System.png")
*Fig 18. Diagram detailing the interactions between sub-system and UI Routes. The full class structure was shown to make apparent how CurrentGames and Validate interact to check Moves submitted from a route*

![Validate Sequence](https://lh3.googleusercontent.com/-qu-Cwxd8iGQ/WhjShqy1MKI/AAAAAAAAAEk/brp0CMVaucAK5oSwUNm2E3AANYIukgb1QCLcBGAs/s0/Validate+Sub+System+Sequence+-+Page+1+%25281%2529.png "Validate Sub System Sequence.png")
*Fig 19. Sequence diagram of the Validate sub system*

###Other Designs Considered
There were numerous designs considered for implementing the functionality gained by the Validate subsystem. One such design would be to remove our Validate class entirely and turn the Piece class into an interface. Then create a Single and King class which implements the Piece interface. Each class would then have their own methods for validating since different Pieces can move in different ways. This approach makes great use of inheritance and would have less coupling than is in our system. While this was certainly a smart way of handling move validation, it would require a very timely overhaul of our system and how Pieces are tracked on a board. In the end we decided against it as we did not have the time nor did we want to risk breaking other, previously implemented functionality by refactoring our code.

Another approach would have been to create an interface called RuleBook and then rename Validate to AmericanRuleSet which would implement RuleBook. Then, when CurrentGames is instantiated at the servers run time, we can inject the rule set into the constructor. This method would allow other rule sets to be easily added to the system later down line. We could also extend the system so that Players can choose which rule set to start a game with but that is out of scope. Unlike the first alternative design, this one did not require other components to be refactored. The drawback to this approach is that injecting a rule set brought up some architectural complexities that we simply did not have the time to explore. Had there been more time, we would have gone with this approach. 


##Code Metrics Analysis

This section details our analysis of code metrics generated by MetricsReloaded

###Chidamber-Kemerer Metrics
During the previous sprints, adherence to Object Oriented design principles was a concern when designing the system. As such, we tried to obey principles like low coupling and high cohesion. According to the code metrics test we ran, our design did not exceed the default Metrics Reloaded threshold. However, Player, CurrentGames, and Board should be noted in future development because their coupling was significantly higher than average, ranging in the high twenties to low thirties while the average was 8.59. 

We contribute the high coupling within those three classes to their role as components of the CurrentGames subsystem. Nearly every route in our project uses CurrentGames and by extension, Player and Board, for gathering the necessary information to build the view model. We also make copies of a given Board each time they have to be passed between methods in order to maintain encapsulation. This may be a contributing factor for the higher than average coupling of the Board class.

###Javadoc Coverage Metrics
Unit testing was not a major issue for the project. For the most part, the team stayed on top of unit testing throughout the development cycle. As such, no areas of major concern arose during our analysis.

###Lines of Code Metrics
Lines of code were heavily contained in the UI tier, with 2.3 thousand lines of code, followed by the model tier with two thousand lines of code, and the application tier with 1.4 thousand lines of code. No areas were highlighted by the metrics as needing to be reworked. 

###Martin Package Metrics
None of the packages in our project surpass the expected threshold. However, the Board package’s afferent coupling is at a 34, which is noticeably greater than the average of 13. The UI and Ajax Route packages should also be monitored as their efferent couplings are at 61 and 35 respectively while the average is 21.14.

In regards to the Board package’s high afferent coupling we have identified that the Board class contributes most to this metric.Similar to the Chidamber-Kemerer metrics, we attribute this high coupling to the creation of new Board objects when a passing information around the system as well as the component’s role in CurrentGames. Interestingly, the CurrentGames and Player class don’t show up as having high afferent coupling. The Board class is however, used in areas where Player and CurrentGames are not, which we believe to be the reason why they are not listed as being above the threshold.

The UI and AjaxRoutes’ high efferent coupling can be easily explained by their reliance on application tier services to gather the information necessary for building the view model. 


##Recommendations for Future Improvements
Based on what we have experienced working on the project and our analysis of code metrics, we recommend that the following areas be revisited; either to fix flawed design or address code metrics hot spots:

##Validate
One of the other designs that we considered for this class was to create an interface called RuleBook and then have our current Validate class implement said interface. This would allow us to add 
