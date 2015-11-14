package ua.com.ukrelektro.passwordrec.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ua.com.ukrelektro.passwordrec.R;
import ua.com.ukrelektro.passwordrec.control.DatabaseHelper;

public class SplashScreen extends AppCompatActivity {
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressDialog = new ProgressDialog(this);
        new DataInitializationFromDB().execute();


    }

    private class DataInitializationFromDB extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {

            progressDialog.setMessage("Start application... ");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseHelper.initDataBase();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            progressDialog.hide();
            startActivity(intent);
            finish();
        }
    }
}