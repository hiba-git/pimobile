/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import entities.Question;
import entities.Reponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import utils.Statics;

/**
 *
 * @author youssef
 */
public class reclamationService {
    public ArrayList<Question> tasks;
    public static reclamationService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    public reclamationService() {
        req = new ConnectionRequest();
    }

    public static reclamationService getInstance() {
        if (instance == null) {
            instance = new reclamationService();
        }
        return instance;
    }
       public ArrayList<Question> parseTasks(String jsonText) {
        try {
            tasks = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Question t = new Question();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int) id);
                //t.setStatus(((int)Float.parseFloat(obj.get("status").toString())));
                t.setNom(obj.get("nom").toString());
                t.setType(obj.get("type").toString());
                t.setEmail(obj.get("email").toString());
                t.setMessage(obj.get("message").toString());
                t.setStatus(obj.get("status").toString());
               // t.setDateQuestion((Date) obj.get("date"));
                t.setPhone((int) Float.parseFloat(obj.get("phone").toString()));
                tasks.add(t);
            }

        } catch (IOException ex) {

        }
        return tasks;
    }
    
    public ArrayList<Question> getAllTasks() {
        String url = "http://127.0.0.1:8000/Api/afficherNouveauxQuestion";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }
    public ArrayList<Question> AncienQuestions(int id) {
        String url = "http://127.0.0.1:8000/Api/afficherAnciensQuestion";
        req.addArgument("idUser", String.valueOf(id));
        req.setUrl(url);
        
//        req.setPost(false);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }
    public void AjouterQuestion(Question q,String username) {
        String url = "http://127.0.0.1:8000/Api/ajouterQuestion";
        ConnectionRequest request = new ConnectionRequest(url, false);
        try {
            request.addArgument("type", q.getType());
            request.addArgument("message", q.getMessage());
            request.addArgument("username", username);
            NetworkManager.getInstance().addToQueueAndWait(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void SupprimerQuestion(int id) {
        String url = "http://127.0.0.1:8000/Api/supprimerReclamtion";
        ConnectionRequest request = new ConnectionRequest(url, false);
        try {
            request.addArgument("id", String.valueOf(id));
            NetworkManager.getInstance().addToQueueAndWait(request);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
     public void ModifierQuestion(Question q) {
        String url = "http://127.0.0.1:8000/Api/modifierQuestion";
        ConnectionRequest request = new ConnectionRequest(url, false);
        try {
            request.addArgument("type", q.getType());
            request.addArgument("id",String.valueOf(q.getId()));
            request.addArgument("message", q.getMessage());
            NetworkManager.getInstance().addToQueueAndWait(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
     public ArrayList<Question> afficherQuestionNonArchive() {
        String url = "http://127.0.0.1:8000/Api/afficherQuestionNonArchive";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }
     public ArrayList<Question> afficherQuestionnArchive() {
        String url = "http://127.0.0.1:8000/Api/afficherQuestionnArchive";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }
     public void RepondreQuestion(Reponse q,int idQuestion) {
        String url = "http://127.0.0.1:8000/Api/repondreQuestion";
        ConnectionRequest request = new ConnectionRequest(url, false);
        try {
            request.addArgument("idQuestion", String.valueOf(idQuestion));
            request.addArgument("reponse",q.getReponse());
            NetworkManager.getInstance().addToQueueAndWait(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
     public void archiver(int idQuestion) {
        String url = "http://127.0.0.1:8000/Api/archiver";
        ConnectionRequest request = new ConnectionRequest(url, false);
        try {
            request.addArgument("idQuestion", String.valueOf(idQuestion));
            NetworkManager.getInstance().addToQueueAndWait(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}   
