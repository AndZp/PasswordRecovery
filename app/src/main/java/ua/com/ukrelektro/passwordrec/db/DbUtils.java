package ua.com.ukrelektro.passwordrec.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ua.com.ukrelektro.passwordrec.model.Code;
import ua.com.ukrelektro.passwordrec.model.Status;

public class DbUtils {


    public static ArrayList<Code> getListCodesFromCSV(InputStream inputStream) {

        ArrayList resultList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                int code = Integer.parseInt(row[0]);
                int count = Integer.parseInt(row[1]);
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
        return resultList;
    }
}
