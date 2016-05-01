import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.*;
import java.util.*;


public class CheckConnection extends HttpServlet {

	int conect;
	String sId;
	private static Map<String,String> database ;	
	  public static String [] players = new String[4]; 
	 public void init(){ 
	     // Reset hit counter.
	     conect = 0;
	     database = new HashMap<String,String>();
	  } 
		
	public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		response.setContentType("text/event-stream");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(true);
		String userName = request.getParameter("username");  
		PrintWriter out = response.getWriter();
		sId = session.getId();
		
		if (database.get(sId) == null){
			if(conect <4){
				conect++;
				players[conect] = sId;	
				put(sId,userName);	 
		  	 	  //if(conect ==4) response.sendRedirect("omigame.html"); 	
		  	 	  //else 
		  	 	  response.sendRedirect("omigame.html");
		  	 	  out.write("data: Waiting for others to connect. Only "+ conect + "players connected .. \n\n"); 	
			}else{
			  	out.println("You cannot connect. Connected number of "+ conect + " players");         	
			}
		}else out.println("You cannot connect."); 		
	}
	
	public int noConnection(){
		return conect;
	}
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
	/*
	** synchronize saving data in database to prevent data race
	*/
	  private void put(String id , String name){
		synchronized (database){
			database.put(id, name);
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

