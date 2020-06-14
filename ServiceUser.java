/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.larvard.Service;


//import GUI.Login;
//import GUI.Menu;
import com.codename1.components.ToastBar;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.larvard.Gui.LoginForm;
import com.larvard.Gui.WalkthruForm;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Amine
 */
public class ServiceUser {
    public static  String nomuser;
    public static  String roleuser;
    public static  String mailuser;
    public static int userid;
            private final Resources theme = UIManager.initFirstTheme("/theme");

       ConnectionRequest cr;
    String message;
    
    public void login(String username,String password){
     
         String urll="http://localhost/Pimobile/auth.php?user="+username+"&password="+password+"";
                      cr=new ConnectionRequest(urll);
                    cr.addResponseListener(new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent evt) {
                         try { 
                             message= new String(cr.getResponseData(), "utf-8");
                                if(message.equals("ok")){   
                                    ConnectionRequest con = new ConnectionRequest();
                                con.setUrl("http://localhost/PiMobile/userconnecte.php?user="+username+"");
                                
                            con.addResponseListener(new ActionListener<NetworkEvent>() {
                                @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp = new JSONParser();
                                try {
                    Map<String, Object> Bonplans = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    System.out.println(Bonplans);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) Bonplans.get("result");
                    for (Map<String, Object> obj : list) {
                        nomuser=obj.get("username").toString();
                        roleuser=obj.get("role").toString();
                        mailuser=obj.get("email").toString();
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        
                        userid=(int) id; }
                                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
                                    System.out.println(userid);
                                 WalkthruForm m=new WalkthruForm(theme);
                                      //new HomeForm().show();
                              m.show();    
                             ToastBar.showErrorMessage("Bievenu dans all for kids", 20);
                                }else{
                                    Dialog.show("Error!","login ou mot de passe incorrecte ","ok",null);
                                          //new HomeForm().show();
                                    //afficheProduit aff=new afficheProduit(theme);
                                    //aff.show();
                                }

                                     

                             } catch (UnsupportedEncodingException ex) {
                             }
                     }
                 });
                                NetworkManager.getInstance().addToQueue(cr);
                               
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void register(String user,String mail,String pass,String role){
        String url="http://localhost/PiMobile/register.php?user="+user+"&email="+mail+"&password="+pass+"&roles="+role+"";
         cr=new ConnectionRequest(url);
                     cr.addResponseListener(new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent evt) {
                         try { 
                             message= new String(cr.getResponseData(), "utf-8");
                                if(message.equals("ok")){
                                    Dialog.show("Scucces","Inscription terminée ","ok",null);
              
                                    LoginForm l=new LoginForm(theme);
                                    l.show();
                                }
                                else if(message.equals("nom utilisateur invalide")){
                                    Dialog.show("Error !","nom utilisateur invalide ","ok",null);
                                   
                                }
                                
                                else if(message.equals("email invalide")){
                                    Dialog.show("Error !","email invalide ","ok",null);
                                     
                                }
                                else if(message.equals("password invalide")){
                                    Dialog.show("Error !","password invalide ","ok",null);
                                     
                                }
                                
                                else{
                                    Dialog.show("Error!","Formulaire incorrecte ","ok",null);
    
                                }

                                     

                             } catch (UnsupportedEncodingException ex) {
                             }
                     }
                 });
                                NetworkManager.getInstance().addToQueue(cr);
    }
}
