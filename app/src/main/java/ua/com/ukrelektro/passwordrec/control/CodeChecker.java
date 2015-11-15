package ua.com.ukrelektro.passwordrec.control;


import java.util.ArrayList;
import java.util.Date;

import ua.com.ukrelektro.passwordrec.model.Code;
import ua.com.ukrelektro.passwordrec.model.CodesSingleton;
import ua.com.ukrelektro.passwordrec.model.State;

public class CodeChecker {

    /**
     * Check current code and update data in CodesSingleton
     * @param result check status
     */
    public static void checkCode(State result) {
        int currentCountNumber = CodesSingleton.getInstance().getCurrentCountNumber();
        ArrayList<Code> codesList = CodesSingleton.getInstance().getCodesList();

        codesList.get(currentCountNumber).setStatus(result);
        codesList.get(currentCountNumber).setDate(new Date());

        int sumPassCount = CodesSingleton.getInstance().getSumPassCount() + codesList.get(currentCountNumber).getCount();
        CodesSingleton.getInstance().setSumPassCount(sumPassCount);
        CodesSingleton.getInstance().setCurrentCountNumber(++currentCountNumber);

    }

    /**
     * Return current code from list of Codes
     *
     * @return current code
     */
    public static Code getCurrentCode() {

        int currentCountNumber = CodesSingleton.getInstance().getCurrentCountNumber();
        return CodesSingleton.getInstance().getCodesList().get(currentCountNumber);
    }

    /**
     * Calculates and returns percent coverage
     *
     * @return percent coverage
     */
    public static double getPercentPassCodes() {
        return (CodesSingleton.getInstance().getSumPassCount() * 100.0) / CodesSingleton.getInstance().getSumCount();
    }

    /**
     * Return list of tested codes.
     *
     * @return list of tested codes.
     */
    public static ArrayList<Code> getHistoryList() {
        ArrayList<Code> historyList = new ArrayList<>();
        for (int i = 0; i < CodesSingleton.getInstance().getCodesList().size(); i++) {
            Code code = CodesSingleton.getInstance().getCodesList().get(i);
            if (code.getStatus() == State.NOT_CHECK) {
                return historyList;
            } else {
                historyList.add(code);
            }
        }
        return historyList;
    }

    /**
     * Clears the counters in CodesSingleton and starts checking from the beginning.
     */
    public static void restartCount() {
        ArrayList<Code> codeList = CodesSingleton.getInstance().getCodesList();
        for (int i = 0; i < codeList.size(); i++) {
            if (codeList.get(i).getStatus() != State.NOT_CHECK) {
                codeList.get(i).setStatus(State.NOT_CHECK);
                codeList.get(i).setDate(null);
            }
        }
        CodesSingleton.getInstance().setCodesList(codeList);
        CodesSingleton.getInstance().setCurrentCountNumber(0);
        CodesSingleton.getInstance().setSumPassCount(0);
    }
}
