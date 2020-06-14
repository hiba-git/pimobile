/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.larvard.Service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.larvard.Utils.Statics;
import com.larvard.Entity.Accessoire;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mouhamedahed
 */
public class ServiceAccessoire {
        public ArrayList<Accessoire> Products;
    
    public static ServiceAccessoire instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceAccessoire() {
         req = new ConnectionRequest();
    }

    public static ServiceAccessoire getInstance() {
        if (instance == null) {
            instance = new ServiceAccessoire();
        }
        return instance;
    }
        int id=Accessoire.getId_courant();

   /* public boolean addTask(Product t) {
        String url = Statics.BASE_URL + "/tasks/" + t.getName() + "/" + t.getStatus(); //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de 
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si 
                la réponse reçue correspond à une autre URL(get par exemple)*/
                
          //  }
      //  });
       // NetworkManager.getInstance().addToQueueAndWait(req);
     //   return resultOK;
    //}

    public ArrayList<Accessoire> parseProducts(String jsonText){
        try {
            Products=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json
//ddddd
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            //ddddd           
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            ///ddddd
                    
            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                
                
                //Création des tâches et récupération de leurs données
                Accessoire t = new Accessoire();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setName((obj.get("name").toString()));
                float price = Float.parseFloat(obj.get("price").toString());
                t.setPrice(price);
                
                t.setModele((obj.get("modele").toString()));
                t.setDescription((obj.get("description").toString()));
                t.setImage((obj.get("image").toString()));
                
                float rating = Float.parseFloat(obj.get("rating").toString());
                t.setRating((int)rating);
                float stock = Float.parseFloat(obj.get("stock").toString());
                t.setStock((int)stock);
                float quantite = Float.parseFloat(obj.get("quantite").toString());
                t.setQuantite((int)quantite);



                
                //Ajouter la tâche extraite de la réponse Json à la liste
                Products.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
         /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
        */
        return Products;
    }
    
    public ArrayList<Accessoire> getAllProduct(){
        String url = Statics.BASE_URL+"/mobileAllAccessoire";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Products = parseProducts(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Products;
    }
    
    
    public ArrayList<Accessoire> getListProduit() {
        ArrayList<Accessoire> listproduit = new ArrayList<>();
           ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://127.0.0.1:8000/mobileAllAccessoire");
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                
                try {
                    Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
                    for (Map<String, Object> result : list) {
                        Accessoire p = new Accessoire();
                        float id = Float.parseFloat(result.get("id").toString());
                        p.setId((int) id);
            
             float quantite=Float.parseFloat(result.get("quantite").toString());
                    p.setQuantite((int)quantite);
             float price=Float.parseFloat(result.get("price").toString());
                    p.setPrice((int)price);
                   
            
              
            p.setName(result.get("name").toString());
            
            p.setDescription(result.get("description").toString());
            p.setModele(result.get("modele").toString());
            p.setImage(result.get("image").toString());
                       
            listproduit.add(p);
                                

                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        System.out.println("list "+listproduit);
        return listproduit;
         
    }
}
