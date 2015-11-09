package ua.com.ukrelektro.passwordrec.model;


import java.util.ArrayList;

public class CodeChecker {

    private static int currentCountNumber = Singleton.getInstance().getCurrentCountNumber();
    private static ArrayList<Code> codesList = Singleton.getInstance().getCodesList();
    private static int sumCount = Singleton.getInstance().getSumCount();
    private static int sumPassCount = Singleton.getInstance().getSumPassCount();

    public static void checkCode(Status result) {
        codesList.get(currentCountNumber).setStatus(result);
        sumPassCount += getCurrentCode().getCount();
        currentCountNumber++;

    }

    public static Code getCurrentCode() {
        return codesList.get(currentCountNumber);//codesList.get(currentCountNumber);
    }

    public static double getPercentPassCodes() {
        return (sumPassCount * 100.0) / sumCount;
    }

    public static int getCurrentCountNumber() {
        return currentCountNumber;
    }

}
