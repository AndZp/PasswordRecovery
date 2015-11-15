package ua.com.ukrelektro.passwordrec.model;

import java.util.ArrayList;

public class CodesSingleton {


    private ArrayList<Code> codesList;
    private int currentCountNumber = 0;
    private int sumCount = 0;
    private int sumCode = 0;



    private int sumPassCount;
    private static CodesSingleton ourInstance = new CodesSingleton();


    public static CodesSingleton getInstance() {
        if (ourInstance == null)
            ourInstance = new CodesSingleton();
        return ourInstance;
    }

    private CodesSingleton() {

    }

    public int getCurrentCountNumber() {
        return currentCountNumber;
    }

    public void setCurrentCountNumber(int currentCountNumber) {
        this.currentCountNumber = currentCountNumber;
    }

    public ArrayList<Code> getCodesList() {
        return codesList;
    }

    public void setCodesList(ArrayList<Code> codesList) {
        this.codesList = codesList;
    }

    public int getSumCount() {
        return sumCount;
    }

    public void setSumCount(int sumCount) {
        this.sumCount = sumCount;
    }

    public int getSumCode() {
        return sumCode;
    }

    public void setSumCode(int sumCode) {
        this.sumCode = sumCode;
    }

    public int getSumPassCount() {
        return sumPassCount;
    }

    public void setSumPassCount(int sumPassCount) {
        this.sumPassCount = sumPassCount;
    }


}
