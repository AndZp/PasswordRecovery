package ua.com.ukrelektro.passwordrec.control;


import java.util.ArrayList;
import java.util.Date;

import ua.com.ukrelektro.passwordrec.model.Code;
import ua.com.ukrelektro.passwordrec.model.Singleton;
import ua.com.ukrelektro.passwordrec.model.Status;

public class CodeChecker {

    private static int currentCountNumber = Singleton.getInstance().getCurrentCountNumber();
    private static ArrayList<Code> codesList = Singleton.getInstance().getCodesList();
    private static int sumCount = Singleton.getInstance().getSumCount();
    private static int sumPassCount = Singleton.getInstance().getSumPassCount();

    public static void checkCode(Status result) {
        codesList.get(currentCountNumber).setStatus(result);
        codesList.get(currentCountNumber).setDate(new Date());
        sumPassCount += getCurrentCode().getCount();
        Singleton.getInstance().setSumPassCount(sumPassCount);
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

    public static ArrayList<Code> getHistoryList() {
        ArrayList<Code> historyList = new ArrayList<>();
        for (int i = 0; i < Singleton.getInstance().getCodesList().size(); i++) {
            Code code = Singleton.getInstance().getCodesList().get(i);
            if (code.getStatus() != Status.NOT_CHECK) {
                historyList.add(code);
            }
        }
        return historyList;
    }

}
