<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">
  
    <h1>Web Checkers</h1>
    
    <div class="navigation">
      <a href="/">My Home</a>
    </div>
    
    <div class="body">
      <p>Welcome to the wonderful world of online Checkers.</p>

    <#if currentPlayer??>
	  <p>Current player: ${currentPlayer.getUsername()}</p>
	  
      <p>Players online:</p>
      <#list allPlayers as player>
        <p>${player}
      </#list>
	  
	  
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
