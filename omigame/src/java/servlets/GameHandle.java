package servlets;

import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 *
 * @author Lakshitha
 */
public class GameHandle extends HttpServlet {
    
      String json2 = null ;
     
     static String[] plcd =new String[4];
     //  static  JSONArray  array2 = new JSONArray();
     private static  String [] cards = { "cards/0_1.png","cards/0_2.png","cards/0_3.png","cards/0_4.png","cards/0_5.png","cards/0_6.png","cards/0_7.png","cards/0_8.png","cards/0_9.png","cards/0_10.png","cards/0_11.png","cards/0_12.png","cards/0_13.png",
                                        "cards/1_1.png","cards/1_2.png","cards/1_3.png","cards/1_4.png","cards/1_5.png","cards/1_6.png","cards/1_7.png","cards/1_8.png","cards/1_9.png","cards/1_10.png","cards/1_11.png","cards/1_12.png","cards/1_13.png",
                                        "cards/2_1.png","cards/2_2.png","cards/2_3.png","cards/2_4.png","cards/2_5.png","cards/2_6.png","cards/2_7.png","cards/2_8.png","cards/2_9.png","cards/2_10.png","cards/2_11.png","cards/2_12.png","cards/2_13.png",
                                        "cards/3_1.png","cards/3_2.png","cards/3_3.png","cards/3_4.png","cards/3_5.png","cards/3_6.png","cards/3_7.png","cards/3_8.png","cards/3_9.png","cards/3_10.png","cards/3_11.png","cards/3_12.png","cards/3_13.png"};
     
    private static final String [] trumps = {"cards/0_1.png","cards/1_1.png","cards/2_1.png","cards/3_1.png"};
   
    public static ArrayList <String> player1 = new ArrayList<>();
    public static ArrayList <String> player2 = new ArrayList<>();
    public static ArrayList <String> player3 = new ArrayList<>();
    public static ArrayList <String> player4 = new ArrayList<>();
   
    public static String trumpSuit = null;
    
    //.........card randomly divided among 4 players.....//
    public static void shuffleCard(){
        
        ArrayList<String> list= new ArrayList<>(Arrays.asList(cards));
        Collections.shuffle(list);
        cards  = list.toArray(new String[list.size()]);
        
        for(int i = 0 ; i< cards.length ; i++){
            if(i<13)
                player1.add(cards[i]);
            else if(i<26)
                player2.add(cards[i]);
            else if( i< 39)
                player3.add(cards[i]);
            else
                player4.add(cards[i]);               
        }
        trumpSuit = trumps[Character.getNumericValue(cards[cards.length-1].charAt(6))];
    
    }
    //............create json object to send ..........///
    public static String createJsonObject(ArrayList<String> list){
        
        JSONObject json = new JSONObject();
        JSONArray  array = new JSONArray();
     JSONArray  crd1 = new JSONArray();
     JSONArray  crd2 = new JSONArray();
     JSONArray  crd3 = new JSONArray();
     JSONArray  crd4 = new JSONArray();
             
        try{
           
            for(int i= 0; i < list.size() ; i++){  
                JSONObject image = new JSONObject();
                image.put("image", list.get(i) );
                array.put(image);         
            }
            
             setcrd(crd1, plcd[0]);
             setcrd(crd2, plcd[1]);
             setcrd(crd3, plcd[2]);
             setcrd(crd4, plcd[3]);
         
           json.put("cards",array);  
           json.put("card1",crd1);
           json.put("card2",crd2);
           json.put("card3",crd3);
            json.put("card4",crd4);
            json.put("showHand" , true);
            json.put("showCards", true);
            json.put("trumpSuit", trumpSuit);
            json.put("message","let's play Game adn have fun"); 
        
        
        }catch(Exception e){
            System.out.println("e");
        }
        
        return json.toString();   
       
    }
    
   
    //.............show players played card at their position.........///
    public static void setcrd(JSONArray array2,String plcd){
           
         try { 
                   JSONObject image= new JSONObject();             
                   if(plcd != null) {
                    image.put("image", plcd );            
                    array2.put(image); 
             
            }
            }catch(Exception e){
            System.out.println("e");
        }
    
    }
    //.........distribute card among players at the initial point.........//
    public void playerCard(HttpServletRequest request){
        if(player1.size() <= 0)
             shuffleCard();
      
        HttpSession session = request.getSession(false);
             
        if(session.getId().equals(CheckConnection.players[0]))
            json2 = createJsonObject(player1);
        else if(session.getId().equals(CheckConnection.players[1]))
            json2 = createJsonObject(player2);
        else if(session.getId().equals(CheckConnection.players[2]))
            json2 = createJsonObject(player3);
        else
            json2 = createJsonObject(player4);
    
    }
    
    //.....remove card from the hand which player played...........//
    public static void removeCard(String sID, String crd,PrintWriter out) {
        String[] plcd=new String[4];
         
        if(sID.equals(CheckConnection.players[0])){  
           plcd[0] = crd;
            player1.remove(crd);
          
        }else if(sID.equals(CheckConnection.players[1])){
            plcd[1] = crd;
            player2.remove(crd);
                
        }else if(sID.equals(CheckConnection.players[2])){ 
            plcd[2] = crd;
            player3.remove(crd);
            
        }else{ 
             plcd[3] = crd;
            player4.remove(crd);
               
        }
    }
    
    //........send json file......//
    public static void sendJson(HttpServletResponse response, String json2) throws IOException{
     
        String jsonString = "data:"+json2+"\n\n";
        
        try (PrintWriter out = response.getWriter()) {         
            
                   out.write(jsonString);
                    out.flush();
             
                out.close(); 
             }
    }
  

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String sendf=null;
        
        HttpSession session = request.getSession();
        String crd = request.getParameter("plcard");
        String sID =  session.getId();
        
        PrintWriter out = response.getWriter();
        
        removeCard(sID,crd,out);
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         response.setContentType("text/event-stream;charset=UTF-8");
        
        
        playerCard(request);             
        sendJson(response,json2);
 
    }

}
