/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sanjewa
 */
public class CheckConnection extends HttpServlet {

       public static HashMap <String,String> playersId  = new HashMap<String,String>();
    public static String [] players = new String[4]; 
    public static int connectedPlayers = 0 ;
    
    //......check whether player has joined or not,.............//    
    public static boolean isUserExists(String id){   
        
        if(playersId.containsKey(id))
            return true;   
        
        return false;
    }
    
    //..........while checking playes connected untill 4 players............//
    public static void checkConnectedPlayers(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException{
        
        
        HttpSession session = request.getSession(false);
        String userName = request.getParameter("username");
        String page = request.getParameter("page");
          
        if(connectedPlayers < 4 && userName != null){      
            if (session == null) {                
                session = request.getSession();
                playersId.put(session.getId(), userName);
                players[connectedPlayers++] =  session.getId();       
                response.sendRedirect("waiting.jsp");                
            } 
            else if( !isUserExists(session.getId()) ){
               
                playersId.put(session.getId(), userName);
                players[connectedPlayers++] =  session.getId();
                response.sendRedirect("waiting.jsp");
            }               
        }                     
    } 
    //....handle get and post method...........//
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       	//content type must be set to text/event-stream
		response.setContentType("text/event-stream");	
		//encoding must be set to UTF-8
		response.setCharacterEncoding("UTF-8");
        
         checkConnectedPlayers(request, response);
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           //response.sendRedirect("waiting.jsp");
            if(connectedPlayers == 0){
                out.write("data: Waiting for others to connect. "+ connectedPlayers + " players connected .. \n\n");
                out.flush();
                
            }
            else if(connectedPlayers == 4){
                out.write("data: four players are cannected.. Please click the Enter to start ..  <span> <a href=\"intGame.jsp\"> <br>Enter </a> </span><br/>\n\n");                
                out.flush();
            }
            else{
                out.write("data: Waiting for others to connect. Only "+ connectedPlayers + " players connected .. \n\n");            
               out.flush();
            }
        out.close();  
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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

}
