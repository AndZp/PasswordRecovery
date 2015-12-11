package ua.com.ukrelektro.passwordrec.DAO;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ua.com.ukrelektro.passwordrec.model.Code;
import ua.com.ukrelektro.passwordrec.model.State;

/**
 * Created by User on 11.12.2015.
 */
public class CSVParser {

    public static ArrayList<Code> getListCodesFromCSV(InputStream inputStream) {


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
                resultList.add(new Code(code, count, State.NOT_CHECK));
            }
        } catch (IOException ex) {
            Log.e("Error in reading CSV", ex.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.e("Error while closing IS", e.getMessage());
            }
        }
        return resultList;
    }
}
