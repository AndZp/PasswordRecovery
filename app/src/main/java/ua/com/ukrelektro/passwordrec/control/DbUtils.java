package ua.com.ukrelektro.passwordrec.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ua.com.ukrelektro.passwordrec.model.Code;
import ua.com.ukrelektro.passwordrec.model.Singleton;
import ua.com.ukrelektro.passwordrec.model.Status;
import ua.com.ukrelektro.passwordrec.ui.activity.MainActivity;

public class DbUtils {

    /**
     * Parse CSV file to List and add data to Singleton
     *
     * @param inputStream intputStream of CSV file
     * @return ArrayList of Code object from CSV file
     */
    public static ArrayList<Code> getListCodesFromCSV(InputStream inputStream) throws RuntimeException {


        ArrayList<Code> resultList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        int sumOfCodes = 0;
        int sumOfCounts = 0;

        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");

                int code = Integer.parseInt(row[0]);
                sumOfCodes++;

                int count = Integer.parseInt(row[1]);
                sumOfCounts += count;
                resultList.add(new Code(code, count, Status.NOT_CHECK));
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: " + e);
            }
        }
        Singleton.getInstance().setCodesList(resultList);
        Singleton.getInstance().setSumCount(sumOfCounts);
        Singleton.getInstance().setSumCode(sumOfCodes);
        return resultList;
    }

    /**
     * DataBase initialization
     *
     * @param context Application context
     */
    public static DatabaseHelper getDbHelper(MainActivity context) {
        return DatabaseHelper.getInstance(context);
    }

    public static void updateCodes(ArrayList<Code> updateList) {
        Singleton.getInstance().getDatabaseHelper().updateDB(updateList);
    }
}
