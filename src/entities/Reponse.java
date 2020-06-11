/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author youssef
 */
public class Reponse {
    private int id;
    private String reponse;
    private int QuestionId;

    public Reponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public int getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(int QuestionId) {
        this.QuestionId = QuestionId;
    }

    public Reponse(int id, String reponse, int QuestionId) {
        this.id = id;
        this.reponse = reponse;
        this.QuestionId = QuestionId;
    }
    
}
