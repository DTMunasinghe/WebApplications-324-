

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP status codes
 */
public enum Status {
	CONTINUE(100, "Continue"),
	OK(200, "OK"),
	NOTFOUND(404, "Not Found"),
	FORBIDDEN(403, "Forbidden"),
	BADREQ(400, "Bad request");
   
	final static String version = "HTTP/1.0";

	final Message message;
	Status(int code, String s) {
		String initial = version + " " + code + " " + s;
		String body = toHTML(s);
       
		Map<String,String> headers = new HashMap<>();
		headers.put("Content-Type", "text/html");
		
		headers.put("Content-Length", Integer.toString(body.length()));
	

		message = new Message(initial, headers, body.getBytes());
	}
	public String toHTML(String s) {
		return "<HTML><BODY<H1>"
				       + s +"</H1></BODY><HTML>";
	}
	
	
}
