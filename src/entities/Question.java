/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

/**
 *
 * @author youssef
 */
public class Question {
    private int id;
    private int idUser;
    private Date dateQuestion;
    private String nom;
    private String type;
    private String email;
    private int phone;
    private String message;
    private String status;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public Question()
    {
        
    }
    public Question(int id, int idUser, Date dateQuestion, String nom, String type, String email, int phone, String message) {
        this.id = id;
        this.idUser = idUser;
        this.dateQuestion = dateQuestion;
        this.nom = nom;
        this.type = type;
        this.email = email;
        this.phone = phone;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public Question(int idUser, Date dateQuestion, String nom, String type, String email, int phone, String message) {
        this.idUser = idUser;
        this.dateQuestion = dateQuestion;
        this.nom = nom;
        this.type = type;
        this.email = email;
        this.phone = phone;
        this.message = message;
    }

    
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Date getDateQuestion() {
        return dateQuestion;
    }

    public void setDateQuestion(Date dateQuestion) {
        this.dateQuestion = dateQuestion;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Question{" + "idUser=" + idUser + ", dateQuestion=" + dateQuestion + ", nom=" + nom + ", type=" + type + ", email=" + email + ", phone=" + phone + ", message=" + message + '}';
    }
}
