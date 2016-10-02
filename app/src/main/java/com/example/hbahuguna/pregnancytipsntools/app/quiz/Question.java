package com.example.hbahuguna.pregnancytipsntools.app.quiz;

/**
 * Created by himanshu on 10/1/16.
 */
public class Question {
    private int ID;
    private String QUESTION;
    private String OPTA;
    private String OPTB;
    private String OPTC;
    private String OPTD;
    private String OPTE;
    private String OPTF;
    private String category;
    public Question()
    {
        ID=0;
        QUESTION="";
        OPTA="";
        OPTB="";
        OPTC="";
        OPTD="";
        OPTE="";
        category="";
        OPTF="";
    }
    public Question(int id,String question, String optA, String optB, String optC, String optD,
                    String optE, String category, String optF) {
        ID = id;
        QUESTION = question;
        OPTA = optA;
        OPTB = optB;
        OPTC = optC;
        OPTD = optD;
        OPTE = optE;
        this.category = category;
        OPTF = optF;
    }
    public int getID()
    {
        return ID;
    }
    public String getQUESTION() {
        return QUESTION;
    }
    public String getOPTA() {
        return OPTA;
    }
    public String getOPTB() { return OPTB; }
    public String getOPTC() {
        return OPTC;
    }
    public String getOPTD() {
        return OPTD;
    }
    public String getOPTE() {
        return OPTE;
    }
    public String getOPTF() {
        return OPTF;
    }
    public int getScore(String answer) {
        if(answer.equals(OPTA)) {
            return 1;
        } else if (answer.equals(OPTB)) {
            return 2;
        } else if (answer.equals(OPTC)) {
            return 3;
        } else if (answer.equals(OPTD)) {
            return 4;
        } else if (answer.equals(OPTE)) {
            return 5;
        } else if (answer.equals(OPTF)) {
            return 6;
        }
        return 1;
    }
    public String getCategory() {
        return category;
    }
    public void setID(int id)
    {
        ID=id;
    }
    public void setQUESTION(String question) {
        QUESTION = question;
    }
    public void setOPTA(String optA) {
        OPTA = optA;
    }
    public void setOPTB(String optB) {
        OPTB = optB;
    }
    public void setOPTC(String optC) { OPTC = optC; }
    public void setOPTD(String optD) {
        OPTD = optD;
    }
    public void setOPTE(String optE) {
        OPTE = optE;
    }
    public void setCategory(String category) { this.category = category;}
}
