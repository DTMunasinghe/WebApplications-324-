<%-- 
    Document   : waiting
    Created on : Apr 22, 2016, 8:41:26 PM
    Author     : Lakshitha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body onload="start()">
        
        
        <h1>Hello World!</h1>
        <br><br>
        
        message: <span id="foo"></span>
        <br><br>
        
        <form method=GET action="OmiServlet">
            
            <input type="hidden" name="page" value="game.jsp" />            
            <input type="submit" value="Enter Game" />
               
      </form> 
        
       
     
    
     
    <script type="text/javascript">
    function start() {
 
        var eventSource = new EventSource("OmiServlet");
         
        eventSource.onmessage = function(event) {
         
            document.getElementById('foo').innerHTML = event.data;
         
        };
         
    }
    </script>
    
    </body>
</html>
