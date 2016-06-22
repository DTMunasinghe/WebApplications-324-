

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Serialise and deserialise HTTP requests and responses.
 */
public class Message {
	final static String CRLF = "\r\n",
			CLEN = "content-length", CTYPE = "content-type";
	final static Pattern SEPARATOR = Pattern.compile("\\s*:\\s*");

	final String initial;
	final Map<String,String> headers;
	final byte[] body;

	public Message(String initial, Map<String,String> headers, byte[] body) {
		this.initial = initial;
		this.headers = headers;
		this.body = body;
	}

	public Message(DataInput in) throws IOException {
		initial = in.readLine();
		headers = readHeaders(in);

		if (headers.containsKey(CLEN)) {
			body = new byte[Integer.valueOf(headers.get(CLEN))];
			in.readFully(body);
		} else
			body = null;
	}

	protected Map<String,String> readHeaders(DataInput in) throws IOException {
		Map<String,String> headers = new HashMap<>();
		String line;
		while ((line = in.readLine())!=null
				       && line.length() > 0) {
			String[] kv = SEPARATOR.split(line);

			if (kv.length>1)
				headers.put(kv[0].toLowerCase(), kv[1]);
			else
				System.err.println("Invalid header "+ line);
		}
		return headers;
	}

	public void serialise(PrintStream out) throws IOException {
		out.println(initial);
      
		for (Map.Entry<String,String> kv: headers.entrySet() ) {
			out.println(kv.getKey() + ": " + kv.getValue());
		}

		out.print(CRLF);
		if (body!=null  ) out.write(body);
	}
	//here gose deserialize method 
	public void deserialise(PrintStream out, String val) throws IOException {
		out.println(initial);
      //print corresponding value
			out.println("The corresponding value is " + val);

		out.print(CRLF);
		
		if (body!=null  ) out.write(body);   
	}
	
/**
** method for get key..here split body store in array and get **required key
**/
	public String keys(){
	    String keys = new String(this.body);
	    String[] parts = keys.split("&");
	    
	     String[] key1 = parts[0].split("=");
	    
	return key1[1];
	}
/**
** method for get value..here split body store in array and get **required value according to provide key
**/
	public String vals(){
	    String keyss = new String(this.body);
	    String[] parts = keyss.split("&");
	    
	     String[] value1 = parts[1].split("=");
	    
	return value1[1];
	}
	
	public String settoHTML(String s) {
		if(s.equals("GET"))
		return null;
		else return s;
	}
}
