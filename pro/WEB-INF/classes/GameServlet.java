import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.json.JSONArray;

public class GameServlet extends HttpServlet {
    
    public static String colsuit = null;
    public static ArrayList <String> player1 = new ArrayList<String>();
    public static ArrayList <String> player2 = new ArrayList<String>();
    public static ArrayList <String> player3 = new ArrayList<String>();
    public static ArrayList <String> player4 = new ArrayList<String>();
    
     private static  String [] cards = { "cards/0_1.png","cards/0_2.png","cards/0_3.png","cards/0_4.png","cards/0_5.png","cards/0_6.png","cards/0_7.png","cards/0_8.png","cards/0_9.png","cards/0_10.png","cards/0_11.png","cards/0_12.png","cards/0_13.png",
                                        "cards/1_1.png","cards/1_2.png","cards/1_3.png","cards/1_4.png","cards/1_5.png","cards/1_6.png","cards/1_7.png","cards/1_8.png","cards/1_9.png","cards/1_10.png","cards/1_11.png","cards/1_12.png","cards/1_13.png",
                                        "cards/2_1.png","cards/2_2.png","cards/2_3.png","cards/2_4.png","cards/2_5.png","cards/2_6.png","cards/2_7.png","cards/2_8.png","cards/2_9.png","cards/2_10.png","cards/2_11.png","cards/2_12.png","cards/2_13.png",
                                        "cards/3_1.png","cards/3_2.png","cards/3_3.png","cards/3_4.png","cards/3_5.png","cards/3_6.png","cards/3_7.png","cards/3_8.png","cards/3_9.png","cards/3_10.png","cards/3_11.png","cards/3_12.png","cards/3_13.png"};
     
    private static String [] collecs = {"cards/0_1.png","cards/1_1.png","cards/2_1.png","cards/3_1.png"};
   
   
    
    public static void shuffleCard(){
        
        ArrayList<String> list= new ArrayList<String>(Arrays.asList(cards));
        cards  = list.toArray(new String[list.size()]);
        Collections.shuffle(list);
        
        for(int i = 0 ; i< cards.length ; i++){
            if(i<13)
                player1.add(cards[i]);
            else if(i>=13 && i<26)
                player2.add(cards[i]);
            else if(i>= 26 && i< 39)
                player3.add(cards[i]);
            else
                player4.add(cards[i]);               
        }
        colsuit = collecs[Character.getNumericValue(cards[cards.length-1].charAt(6))];    
    }
    
    public static String createJsonObject(ArrayList<String> list){        
        JSONObject jsobj1 = new JSONObject();
        JSONArray  jsarray1 = new JSONArray();
             
        try{           
            for(int i= 0; i < list.size() ; i++){  
                JSONObject image = new JSONObject();
                image.put("image", list.get(i) );
                jsarray1.put(image);         
            }
            jsobj1.put("cards",jsarray1);
            jsobj1.put("showHand" , true);
            jsobj1.put("showCards", true);
            jsobj1.put("colsuit", colsuit);
            jsobj1.put("message","let's play game");         
        
        }catch(Exception e){
            System.out.println("e");
        }        
       return jsobj1.toString();   
    }
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/event-stream;charset=UTF-8");
       
        if(player1.size() <= 0)
             shuffleCard();
        
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        String jsobj1 = null ;
         
        if(session.getId() == CheckConnection.players[0])
            jsobj1 = createJsonObject(player1);
        else if(session.getId() == CheckConnection.players[1])
            jsobj1 = createJsonObject(player2);
        else if(session.getId() == CheckConnection.players[2])
             jsobj1 = createJsonObject(player3);
        else
             jsobj1 = createJsonObject(player4);
        
        String jsonString = "data:"+jsobj1+"\n\n";
        try {
           out.write(jsonString);
        } finally {
            out.close();
        }
    }
	/**
     * Handles the HTTP <code>GET</code> method.
     
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


}
