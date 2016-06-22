import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class PhoneBook extends HttpServlet {
 
  private static Map<String,String> database ;
	
	/*
	** initialize hashmap to store data
	*/
 	 public void init() throws ServletException{
	     database = new HashMap<String,String>();
	  }
	  
	/*
	** Set response content type
	** write response
	*/
	  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	      	response.setContentType("text/html");
	    	String userName = request.getParameter("userName");      
	      	PrintWriter out = response.getWriter();
		out.println("<h1>" + get(userName) + "</h1>");
	  }
	  /*
	  ** Set response content type
	  ** get user enter data and put in database
	  ** print successfull message
	  */
	   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String userName = request.getParameter("userName");
		String number = request.getParameter("number");
		put(userName,number);
	      	response.setContentType("text/html");	 	 
	     	PrintWriter out = response.getWriter();
		out.println("<h1>" +" Successfully Saved!" + "</h1>");
		}
	/*
	** synchronize saving data in database to prevent data race
	*/
	  private void put(String name , String number){
		synchronized (database){
			database.put(name,number);
		}
  	}	
  
  	/*
	** synchronize saved data in database to get data
	*/
	  private String get(String name){
		synchronized (database){
			return database.get(name);
		}
	    }	
}