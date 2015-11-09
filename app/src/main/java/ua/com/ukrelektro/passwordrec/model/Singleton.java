package ua.com.ukrelektro.passwordrec.model;

import java.util.ArrayList;

public class Singleton {


    private ArrayList<Code> codesList;
    private int currentCountNumber = 0;
    private int sumCount = 0;
    private int sumCode = 0;


    private int sumPassCount;
    private static Singleton ourInstance = new Singleton();


    public static Singleton getInstance() {
        if (ourInstance == null)
            ourInstance = new Singleton();
        return ourInstance;
    }

    private Singleton() {

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
