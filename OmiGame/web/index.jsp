<%-- 
    Document   : index
    Created on : Apr 22, 2016, 3:31:45 AM
    Author     : Lakshitha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    
    <body onload="start()">
        
        
    <form method=GET action="OmiServlet">
        put your user name :<input type="text" name="userName"/>
        <input type="submit" value="Enter" />
                
    </form>       
        
        
    message: <span id="foo"></span>
     
    <br><br>
     
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
