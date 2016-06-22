
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body onload="start()">
        <h1>Hello Player Wait for Other Players</h1>
    </body>
    <br><br>
        
        message: <span id="foo"></span>
        <br><br>
    
     <script type="text/javascript">
    function start() {
 
        var eventSource = new EventSource("CheckConnection");
         
        eventSource.onmessage = function(event) {
         
            document.getElementById('foo').innerHTML = event.data;
        
        };
         
    }
    
    var auto_refresh = setInterval(
        function ()
        {start()
        }, 100); // autorefresh the content of the div after
                   //every 100 milliseconds(0.1sec)
    
    </script>
    
</html>
