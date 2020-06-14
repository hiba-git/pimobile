/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.larvard.Service;

import com.larvard.Entity.Bonplan;
import com.larvard.Entity.RatingBonplan;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Amine
 */
public class ServiceBonplan {
    ConnectionRequest cr;
    String message;
    public ArrayList<Bonplan> getList() {
        ArrayList<Bonplan> listBonplans = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/PiMobile/AllBonplans.php");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp = new JSONParser();
                                try {
                    Map<String, Object> Bonplans = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    System.out.println(Bonplans);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) Bonplans.get("result");
                    for (Map<String, Object> obj : list) {
                        Bonplan Bonplan = new Bonplan();
                       float id = Float.parseFloat(obj.get("Id").toString());
                        
                        Bonplan.setId((int) id);
                        Bonplan.setRegion(obj.get("Region").toString());
                        Bonplan.setDescription(obj.get("Desc").toString());
                        Bonplan.setNom(obj.get("Nom").toString());
                        Bonplan.setAdresse(obj.get("Adresse").toString());
                        Bonplan.setNom_image(obj.get("Image").toString());
                        float rat = Float.parseFloat(obj.get("Rat").toString());
                        float lng = Float.parseFloat(obj.get("lng").toString());
                        float lat = Float.parseFloat(obj.get("lat").toString());
                        
                        Bonplan.setRat(rat);
                        Bonplan.setLat(lat);
                        Bonplan.setLng(lng);
                        listBonplans.add(Bonplan);

                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listBonplans;
    }
    public void ajouterrat(int idb,int note) {
     String url="http://localhost/PiMobile/ratingbonplan.php?idb="+idb+"&note="+note+"";
         cr=new ConnectionRequest(url);
                    cr.addResponseListener(new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent evt) {
                         try { 
                             message= new String(cr.getResponseData(), "utf-8");
                                if(message.equals("ok")){
                                    Dialog.show("Scucces","Rating terminée ","ok",null);
                                 
                                }

                                     

                             } catch (UnsupportedEncodingException ex) {
                             }
                     }
                 });
                                NetworkManager.getInstance().addToQueue(cr);
    }
}
