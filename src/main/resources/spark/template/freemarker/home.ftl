<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="5 ">
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">
  
    <h1>Web Checkers</h1>
    
    <div class="navigation">
      <a href="/">My Home</a>
      <#if currentPlayer??>
      <a href="/signout">Sign out [${currentPlayer.username}]</a>
      <#else>
      <a href="/signin">Sign-in</a>
      </#if>
    </div>
    
    <div class="body">
      <p>Welcome to the wonderful world of online Checkers.</p>

      <#if currentPlayer??>
	    <p>Current player: ${currentPlayer.username}</p><br />
	  
        <#if message??>
        	<p>${message}<p>
        <#else>
            <p>To start a game, select an opponent below and press 
        	the play button.</p>
    	</#if>
    	
        <form action="./game" method="GET">
        	<#list allPlayers as player>
          		<#if player != currentPlayer.username>
            		<input type="radio" name="opponent" value=${player} /> ${player} <br />
          		</#if>
        	</#list> <br />
        	<button type="submit">Play!</button>
        </form>
        <br />


        <form action="./spectate" method="GET">
            <#if numGames == 0 >
                <p>No games are currently being played.</p>
            <#else>
                <#list allGames as game>
                    <input type="radio" name="spectate" value=${game} /> ${game} <br />
                </#list> <br />
                <button type="submit">Spectate!</button>
            </#if>
        </form>
        <br />

        <#if completedGames??>
        <form action="./replay" method="GET">
            <#if numOfCompletedGames == 0 >
               <p>No replay available (you haven't played a game yet).</p>
            <#else>
               <#assign x = 0>
               <#list completedGames as game>
                   <input type="radio" name="replay" value=x /> ${game} <br />
                   <#assign x += 1>
               </#list> <br />
               <button type="submit">Replay!</button>
            </#if>
        </form>
        <br />
        </#if>
      
        <form action="./signout" method="GET">
          <button>Sign Out</button>
        </form>

      <#else>

        <p>Players online: ${numPlayersOnline}</p>

        <form action="./signin" method="GET">
          <button>Sign In</button>
        </form>
      </#if>

    </div>
    
  </div>
</body>
</html>
