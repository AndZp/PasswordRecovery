package ua.com.ukrelektro.passwordrec.control;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import ua.com.ukrelektro.passwordrec.model.Code;
import ua.com.ukrelektro.passwordrec.ui.activity.MainActivity;

/**
 * AsyncTask for updating list of codes in CodesSingleton and in DataBase
 */
public class DownloadUpdateTask extends AsyncTask<Void, Void, String> {

    public static final String UPDATE_URL = "https://s3.amazonaws.com/passwordapp/updates.json";
    private MainActivity mainActivity;
    private ProgressDialog progressDialog;


    public DownloadUpdateTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.progressDialog = new ProgressDialog(mainActivity);
    }

    protected void onPreExecute() {
        progressDialog.setMessage("Downloading update...");
        progressDialog.show();
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface arg0) {
                DownloadUpdateTask.this.cancel(true);
            }
        });
    }

    @Override
    protected String doInBackground(Void... params) {
        InputStream inputStream = null;
        try {

            HttpURLConnection conn = getHttpURLConnection(UPDATE_URL);

            inputStream = conn.getInputStream();

            ArrayList<Code> updateList = getCodeListFromJson(inputStream);

            margeUpdateListWithHistoryList(updateList);

            DatabaseHelper.getInstance().updateCodesInDataBase(updateList);

            return "Update complete";
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void margeUpdateListWithHistoryList(ArrayList<Code> updateList) {
        ArrayList<Code> historyList = CodeChecker.getHistoryList();

        for (int i = 0; i < updateList.size(); i++) {
            Code codeFromUpdate = updateList.get(i);

            // if historyList contain code, who was checked, set actual data and status in updateList
            if (historyList.contains(codeFromUpdate)) {
                Code codeFromHistory = historyList.get(historyList.indexOf(codeFromUpdate));
                updateList.get(i).setStatus(codeFromHistory.getStatus());
                updateList.get(i).setDate(codeFromHistory.getDate());
            }
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.hide();
        Toast.makeText(mainActivity.getApplicationContext(), s, Toast.LENGTH_SHORT).show();

    }

    /**
     * Parsing Json with GSON lib.
     *
     * @param inputStream inputStream from JSON file
     * @return list of Codes
     * @throws UnsupportedEncodingException
     */
    private static ArrayList<Code> getCodeListFromJson(InputStream inputStream) throws UnsupportedEncodingException {

        BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        Gson gson = new Gson();
        Code[] codeArr = gson.fromJson(streamReader, Code[].class);
        return new ArrayList<>(Arrays.asList(codeArr));
    }

    @NonNull
    private static HttpURLConnection getHttpURLConnection(String updateUrl) throws IOException {
        URL url = new URL(updateUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        conn.connect();
        return conn;
    }


}
