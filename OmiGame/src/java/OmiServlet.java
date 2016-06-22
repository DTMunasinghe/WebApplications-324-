import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Lakshitha
 */
public class OmiServlet extends HttpServlet {
       
    public static HashMap <String,String> playersId  = new HashMap<String,String>();
    public static String [] players = new String[4]; 
    public static int connectedPlayers = 0 ;
    
          
    public static boolean isUserExists(String id){   
        
        if(playersId.containsKey(id))
            return true;   
        
        return false;
    }
    
    
    public static void checkConnectedPlayers(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException{
        
        
        HttpSession session = request.getSession(false);
        String userName = request.getParameter("userName");
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
        
        else if( connectedPlayers == 4 && isUserExists(session.getId()) && page!= null ){
            response.sendRedirect("game.jsp");
        }       
                  
    }       
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //content type must be set to text/event-stream
        response.setContentType("text/event-stream");  
        //encoding must be set to UTF-8
        response.setCharacterEncoding("UTF-8");
        
        checkConnectedPlayers(request, response);
                
        PrintWriter writer = response.getWriter();
        if(connectedPlayers == 0)
            writer.write("data: Waiting for others to connect. "+ connectedPlayers + " players connected .. \n\n");
        else if(connectedPlayers == 4)
            writer.write("data: four players are cannected press enter games to start .. \n\n");
        else
            writer.write("data: Waiting for others to connect. Only "+ connectedPlayers + " players connected .. \n\n");            
        
        writer.close();
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
