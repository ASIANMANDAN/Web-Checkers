<!DOCTYPE html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
 <meta http-equiv="refresh" content="5 ">
 <title>${title} | Web Checkers</title>
 <link rel="stylesheet" href="/css/style.css">
 <link rel="stylesheet" href="/css/game.css">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
 <script>
 window.gameState = { };
 window.alert = function() {};
 </script>
</head>
<body>
 <div class="page">
   <h1>Web Checkers</h1>

   <div class="navigation">
   <#if currentPlayer??>
     <a href="/">My Home</a>
     <a href="/signout">Sign out [${currentPlayer.username}]</a>
   <#else>
     <a href="/signin">Sign-in</a>
   </#if>
   </div>

  <div class="body">

    <#if selected??>
        <p>${selected}</p>
    </#if>

    <div>
        <div id="game-controls">

            <#assign x = moveIndex>
            <#if x < moveListSize>
                <form action="./replay" method="GET">
                    <input type="hidden" name="index" value=${x + 1} />
                    <button type="submit">Next Move</button>
                </form>
            </#if>

        <fieldset id="game-info">
           <legend>Info</legend>

          <#if message??>
            <div id="message" class="${message.type}">${message.text}</div>
          <#else>
            <div id="message" class="info" style="display:none">
             <!-- keep here for client-side messages -->
           </div>
          </#if>

          <div>
             <table data-color='RED'>
               <tr>
                 <td><img src="../img/single-piece-red.svg" /></td>
                 <td class="name">${redPlayer}</td>
               </tr>
             </table>
             <table data-color='WHITE'>
               <tr>
                 <td><img src="../img/single-piece-white.svg" /></td>
                 <td class="name">${whitePlayer}</td>
               </tr>
             </table>
           </div>
         </fieldset>

      </div>

      <div class="game-board">
         <table id="game-board">
           <tbody>
           <#list board.iterator() as row>
             <tr data-row="${row.index}">
             <#list row.iterator() as space>
               <td data-cell="${space.col}"
                   <#if space.isValid() >
                   class="Space"
                   </#if>
                   >
               <#if space.piece??>
                 <div class="Piece"
                      id="piece-${row.index}-${space.col}"
                      data-type="${space.piece.type}"
                      data-color="${space.piece.color}">
                 </div>
               </#if>
               </td>
             </#list>
             </tr>
           </#list>
           </tbody>
         </table>
       </div>
     </div>

  </div>
 </div>

<audio id="audio" src="http://www.soundjay.com/button/beep-07.mp3" autostart="false" ></audio>

<script data-main="js/game/index" src="js/require.js"></script>

</body>
</html>