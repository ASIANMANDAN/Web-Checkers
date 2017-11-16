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
*Spectator Mode - Being able to watch a game that is currently in progress.
*Asynchronous Play - The players in a game can access and play a game over the course of 		   	 	multiple logins.


## Application Domain

This section describes the application domain.

###Overview of Major Domain Areas
![Domain Analysis](https://lh3.googleusercontent.com/--WYhHesJGOA/WgGIojUN2EI/AAAAAAAAADA/1NbjWNeL9cwiF4wTF-sD5jSmR5bsjPiwACLcBGAs/s0/Domain+Analysis+-+Page+1.png "Domain Analysis.png")
*Fig 1. Domain Analysis of the WebCheckers project*

Some key domain areas to mention include the Game Board, Rule Set, and Game entities.

### Details of each Domain Area

The Game Board entity is tasked with keeping track of the current state of the game such as whose turn it currently is. The board itself must accurately reflect changes made to it through player moves. The Board contains all 64 Squares and the starting configuration of a new checkers game, places 24 total Pieces (12 of each color) in the proper start position. As the Game is played, more Pieces will be removed until either one of the Players has no Pieces left, or none of that Players Pieces can move.

The Rule Set entity serves to ensure that the Game is played according to standard American rules. As such, this entity is responsible for stopping Players from making Moves which do not adhere to those rules.


## Architecture

This section describes the application architecture.

### Summary
The WebCheckers webapp uses a Java-based web server. The Spark web micro framework and the Freemarker template engine are utilized to handle HTTP requests and generate HTML responses. The architecture of the project is formed in the Tiers and Layers pattern, and consists of UI, Application, and Model tiers. The User of the webapp interacts with the UI tier, which in turn interacts with the Application and Model tiers. The Application tier contains the logic that controls the application flow, while the Model tier contains the business logic.

![Arch Model](https://lh3.googleusercontent.com/-0lhWO-ppJys/WgF1H8IgA9I/AAAAAAAAABs/hlBFifip6P8oA8b5F14SLDnlDiWmqV19ACLcBGAs/s0/Arch+Model+-+Page+1+%25282%2529.png "Arch Model.png")

*Fig 2. Diagram detailing what tier each component is part of*


### Overview of User Interface
The application's user interface is comprised of three main states: Home, Sign-In, and Game.  Upon establishing an HTTP connection, it renders the home page, which displays the number of players online and provides a link to sign-in. Users can then sign-in on the sign-in screen. Signing-in with a unique username leads them back to the home page. This page now displays a selectable list of players, which does not include the current user.  The current user can then select a player to challenge to a game. If that player is not available, then the user is returned to the home page. Otherwise, both players are taken into the game view. The players remain in the in-game state until the game is terminated, whether it be by resignation, sign-out, or a player winning. The players are then returned to the home view.

![UI State Chart](https://lh3.googleusercontent.com/-_YCHddV_aq4/WgGoHEpH8PI/AAAAAAAAADU/FcVOFqJaSR0pzopn1hjhlKtJo4fsmK2mACLcBGAs/s0/Sprint+2+State+Diagram+-+Page+1+%25282%2529.png "Sprint 2 State Diagram.png")
*Fig 3. State chart illustrating the different views and their HTTP Routes*

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
	 - PostSigninRoute
	 - WebServer

### Tier: Model
The Model tier contains the the board components. This tier is responsible for rendering the internal representation of the Board, Piece, and Space components. All of these components are located within the board package within the model (com.webcheckers.model.board)

Within the Board class, we have implemented our own custom way of representing the checker board such that the logic can be performed with ease by the validation checks we're implementing in the future. With this representation of the board, each area on the board is represented by a space.

The Space class is what is used for representing an area on a board. Each Space contains information like the row/column location, the color of the space, and the piece that sits on the space (if there is a piece). The piece itself is represented in the Model tier as well. 

The Piece class serves as a way to determine the color and type of piece. The types of a pieces include a single piece or a king piece. This later gives the ability for a validation to determine the legality of a move based off what type of piece it is. 

Outside of the board package, there is also a Player class that represents an individual Player. The Player class works with application tier services to perform actions like signing in/leaving a game/creating a game/picking an opponent. 

### Tier: Application

The components of the Application tier work to manage information and logic which span the entirety of the application. Additionally, these components are responsible for providing client specific services needed by the UI tier.

The Application Tier consists of the CurrentGames, Game, and PlayerLobby component. The first two components are responsible for managing information associated with all ongoing games such as the players within a given game, as well as the current configuration of the board. These components provide services related to starting and ending a game. The PlayerLobby component is responsible for keeping a list of every signed-in user and has services which log-in and log-out users. All of the mentioned components have a shared responsibility of providing information to the UI tier in order to generate the appropriate view.

##Sub-system: CurrentGames

### Purpose of the sub-system
The CurrentGames sub-system is a system within our WebCheckers application that is responsible for the collection of all ongoing games. This system is responsible for many services throughout the application like providing the GetHomeRoute with the information to deny users from entering games with players who are already in a game, as well as provide all information necessary to carry out the functionality of playing a game.  With the total collection of Games, you can keep track of the states of all games at once. Any sort of move made or player action is both carried out and recorded by the CurrentGames sub-system. 

### Static model

![CurrentGames Class](https://lh3.googleusercontent.com/-VshS1RFQmS0/WgF_sN5ay8I/AAAAAAAAACs/HcyTfw0HLCM9S1EJEO9XKbEhdDsSE9w5gCLcBGAs/s0/CurrentGames+Sub+System+-+Page+1.png "CurrentGames Sub System.png")
*Fig 4. Class diagram of the CurrentGames sub system*

### Dynamic model
![CurrentGames State Chart](https://lh3.googleusercontent.com/-M0STlyMncko/WgF5-3yy4GI/AAAAAAAAACU/9vYFxr0ztHcA-a6VifUtr-vLPDfij4EUACLcBGAs/s0/CurrentGamesStateChart+-+Page+1.png "CurrentGamesStateChart.png")
*Fig 5. State chart diagram of the CurrentGames sub system*

## Sub-system: Player Sign-in/Sign-out

This section describes the detail design of the Player Sign-in/Sign-out sub system.

### Purpose of the sub-system

The Player Sign-in/Sign-out sub system delivers the functionality needed to sign-in under a desired username and then later sign-out when the player chooses. Once signed-in, the Players information is stored in a collection of players within the PlayerLobby component. This component is used by various UI routes to determine what information should be displayed. A signed-in player at the home page will see a list of other signed-in players which is used to select an opponent and start a game. When the player decides to, they can sign-out and release their username for other players to use.

### Static models

![Signin/Signout Class](https://lh3.googleusercontent.com/-JN6AK3Au1xo/WgE_z8IvncI/AAAAAAAAAAM/rDWrEKmhHxkPPAwakf8Qkg_afwIeq3C3gCLcBGAs/s0/SigninSignout+Sub+System+-+Page+1.png "SigninSignout Sub System.png")
*Fig 6. Class diagram of the Player Signin/Signout sub system*

### Dynamic models

![Signin/Signout Sub System Dynamic](https://lh3.googleusercontent.com/-SFpSnHwJYCU/WgFREFVdzmI/AAAAAAAAAAg/S5iz0MtOQCApko98uVpaq3JKJRsWQFhDgCLcBGAs/s0/SignInSignOutDynamic+-+Page+1.png "SignInSignOutDynamic.png")
*Fig 7. State chart diagram of the Player Signin/Signout sub system*

## Sub-system: Start a Game

This section describes the detail design of the Start a Game sub system.

### Purpose of the sub-system

>This sub system is composed of two smaller sub systems, Player Sign-in/Sign-out and CurrentGames. For more detailed information of those sub systems, checkout their dedicated sections of this document.

The Start a Game sub system contains the functionality required to connect two players together in a new game of checkers. One player, at the home page, will select from a list of opponents which is generated by the PlayerLobby component of the Sign-in/Sign-out sub system. Assuming the selected opponent is not already in a game, the CurrentGames component of the CurrentGames sub system will create a new Game object consisting of the two players and a newly configured Board component. The Game View is created using information from CurrentGames and a reformatted representation of the Board data which is created by the BoardView and Row components. Once all of the necessary information is entered and the correct Game View is generated, both players are redirected to the game, thus starting the match.

### Static models
![StartGame Class](https://lh3.googleusercontent.com/-FZ7VRx_1Hgs/WgFk4w5vnBI/AAAAAAAAABE/j5D7ER0TpR471Qh-GuOLhLTtfadV4a3jACLcBGAs/s0/Create+Game+System+-+Page+1.png "Create Game System.png")
*Fig 8. Class diagram of the Start a Game sub system*

### Dynamic models
![StartGame dynamic](https://lh3.googleusercontent.com/-qMelZA2Sth8/WgFjKffivQI/AAAAAAAAAA0/Mx5zDPva0lgsKDNTxfTUM21dPpm8jKMiwCLcBGAs/s0/StartGame+sub+system+dynamic+-+Page+1.png "StartGame sub system dynamic.png")
*Fig 9. State chart diagram of the Start a Game sub system*

## Sub-system: Validate a Move

This section describes the detail design of the Validate a Move sub system.

### Purpose of the sub-system

The Validate a Move sub system allows a user generated move to be passed in through an AJAX route in order for the model to determine if the move is legal given the standard, American rules of checkers. Within the PostValidateMoveRoute, a Move object is gotten from the client before it is passed into the CurrentGames service. From here, CurrentGames finds the game associated with that client and then creates a copy of the current board configuration to pass to the Validate model tier component. This class, given a move and a board config, checks that the proposed move does not violate the rules of checkers. Should the move be declared invalid, the Validate component will return a message indicating what rule has been broken. This message gets returned to the PostValidateRoute so that the system can display it to the user, allowing them to understand why they are unable to make said move. 

The Validate component contains a series of checks that enforce different rules. An example of this is the check that is performed for if a jump can be made. The system iterates through the board model, finding each piece that belongs to the player whose turn it is. When a piece is encountered, that has an opponent adjacent to it, it then sees if a jump can be made. Valid jump moves are saved and then used to determine if the player has made a jump. If the jump isn't in the list, the Validate component will return a message stating that no valid jumps currently exist.

### Static models
![Validate Class](https://lh3.googleusercontent.com/-oaQTpndHosA/WgzGvfZ7AuI/AAAAAAAAADs/Zoakc1k6evYOEiF5E1X170RV9-G8R6LUACLcBGAs/s0/Validate+Move+System+-+Page+1+%25282%2529.png "Validate Move System.png")
*Fig 10. Class diagram of the Validate sub system*

### Dynamic models
![Validate Sequence](https://lh3.googleusercontent.com/-9nG-RZNI0kg/WgzRWjU-FVI/AAAAAAAAAEA/oLvzA5iipy8TWlIASt-l4MZrLNfK9RemQCLcBGAs/s0/Validate+Sub+System+Sequence+-+Page+1.png "Validate Sub System Sequence.png")
*Fig 11. Sequence diagram of the Validate sub system*