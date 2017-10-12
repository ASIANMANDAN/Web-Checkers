<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
	<div class="page">
		<h1>Web Checkers</h1>
    
    <div class="navigation">
      <a href="/">My Home</a>
      <a href="/signin">Sign-in</a>
    </div>
    
    <div class="body">

      <#if message??>
      <p>${message}</p>

      <#else>
      <p>Choose a username so that friends and opponents can find you!
      When you're finished, click the Sign In button to start playing!</p>
      </#if>
      

      <form action="./signin" method="POST">
      	<input name="username" />
        <button type="submit">Sign In</button>
      </form>

    </div>
    
  </div>
</body>
</html>