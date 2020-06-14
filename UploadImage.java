/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.larvard.Service;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 *
 * @author mouhamedahed
 */
public class UploadImage {
   
     
    
    public static void imageupload(String image,String imageName){
                             
        
         StringBuffer str = new StringBuffer();
        ConnectionRequest con = new ConnectionRequest();
        con.setHttpMethod("POST");
                    con.setUrl("http://localhost/medahed/web/app_dev.php/medahed/upload");
                    con.addArgument("imagename", imageName);
                    con.addArgument("image", image);
                                  con.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                                      con.addResponseListener(new ActionListener<NetworkEvent>() {
                        @Override
                        public void actionPerformed(NetworkEvent evt) {
                                 byte[] data = con.getResponseData();
                        JSONParser parser = new JSONParser();
                        Map map = null;
                        try {
                            map = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(data), "UTF-8"));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        String info = (String) map.get("info");

                        System.out.println(info);
                            
                        }});
                                  NetworkManager.getInstance().addToQueue(con);
}}
