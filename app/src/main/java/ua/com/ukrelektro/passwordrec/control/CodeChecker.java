package ua.com.ukrelektro.passwordrec.control;


import java.util.ArrayList;
import java.util.Date;

import ua.com.ukrelektro.passwordrec.model.Code;
import ua.com.ukrelektro.passwordrec.model.Singleton;
import ua.com.ukrelektro.passwordrec.model.State;

public class CodeChecker {

    /**
     * Check current code. Set status
     *
     * @param result check status
     */
    public static void checkCode(State result) {
        int currentCountNumber = Singleton.getInstance().getCurrentCountNumber();
        ArrayList<Code> codesList = Singleton.getInstance().getCodesList();

        codesList.get(currentCountNumber).setStatus(result);
        codesList.get(currentCountNumber).setDate(new Date());

        int sumPassCount = Singleton.getInstance().getSumPassCount() + codesList.get(currentCountNumber).getCount();
        Singleton.getInstance().setSumPassCount(sumPassCount);
        Singleton.getInstance().setCurrentCountNumber(++currentCountNumber);

    }

    public static Code getCurrentCode() {

        int currentCountNumber = Singleton.getInstance().getCurrentCountNumber();
        return Singleton.getInstance().getCodesList().get(currentCountNumber);
    }

    public static double getPercentPassCodes() {
        return (Singleton.getInstance().getSumPassCount() * 100.0) / Singleton.getInstance().getSumCount();
    }


    public static ArrayList<Code> getHistoryList() {
        ArrayList<Code> historyList = new ArrayList<>();
        for (int i = 0; i < Singleton.getInstance().getCodesList().size(); i++) {
            Code code = Singleton.getInstance().getCodesList().get(i);
            if (code.getStatus() == State.NOT_CHECK) {
                return historyList;
            } else {
                historyList.add(code);
            }
        }
        return historyList;
    }

    public static void restartCount() {
        ArrayList<Code> codeList = Singleton.getInstance().getCodesList();
        for (int i = 0; i < codeList.size(); i++) {
            if (codeList.get(i).getStatus() != State.NOT_CHECK) {
                codeList.get(i).setStatus(State.NOT_CHECK);
                codeList.get(i).setDate(null);
            }
        }
        Singleton.getInstance().setCodesList(codeList);
        Singleton.getInstance().setCurrentCountNumber(0);
        Singleton.getInstance().setSumPassCount(0);
    }
}
