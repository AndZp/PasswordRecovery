package ua.com.ukrelektro.passwordrec.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import ua.com.ukrelektro.passwordrec.R;
import ua.com.ukrelektro.passwordrec.control.CodeChecker;
import ua.com.ukrelektro.passwordrec.control.DatabaseHelper;
import ua.com.ukrelektro.passwordrec.control.DownloadUpdateTask;
import ua.com.ukrelektro.passwordrec.model.Singleton;
import ua.com.ukrelektro.passwordrec.model.State;
import ua.com.ukrelektro.passwordrec.ui.adapter.TabsPagerFragmentAdapter;

/**
 * Main Activity Java Class
 */
public class MainActivity extends AppCompatActivity {
    public static final String APP_PREFERENCES = "mysettings";

    final String CURRENT_COUNT_NUMBER = "CURRENT_COUNT_NUMBER";
    final String SUM_PASSED_COUNT = "SUM_PASSED_COUNT";

    private static final int LAYOUT = R.layout.activity_main;

    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        LoadPreferences();

        DatabaseHelper.initDataBase();
        initToolbar();
        initTabLayout();
        initActionButton();


    }

    @Override
    protected void onStop() {
        super.onStop();
        SavePreferences();
        DatabaseHelper.getInstance().updateStatusInDataBase(Singleton.getInstance().getCodesList());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.update:
                if (checkInternetConnection()) {
                    new DownloadUpdateTask(MainActivity.this).execute();
                } else {
                    Toast.makeText(this, "Internet connection error", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.restart:
                CodeChecker.restartCount();
                initTabLayout();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * onClick handler for FloatingActionButton
     */
    private void initActionButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInternetConnection()) {
                    new DownloadUpdateTask(MainActivity.this).execute();
                } else {
                    Snackbar.make(view, "Internet connection error", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    /**
     * Initialization TabLayout with TabsPagerFragmentAdapter
     */
    private void initTabLayout() {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * Toolbar initialization
     */
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * onClick handler for button "Worked and Next"
     *
     * @param view current view
     */
    public void nextNumber(View view) {

        switch (view.getId()) {
            case R.id.btnNext:
                CodeChecker.checkCode(State.FAIL);
                initTabLayout();
                break;
            case R.id.btnWorked:
                CodeChecker.checkCode(State.PASS);
                initTabLayout();
                break;
            default:
                break;
        }
    }

    private boolean checkInternetConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void SavePreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(
                APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CURRENT_COUNT_NUMBER, Singleton.getInstance().getCurrentCountNumber());
        editor.putInt(SUM_PASSED_COUNT, Singleton.getInstance().getSumPassCount());
        editor.apply();
    }

    private void LoadPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(
                APP_PREFERENCES, MODE_PRIVATE);
        Singleton.getInstance().setCurrentCountNumber(sharedPreferences.getInt(CURRENT_COUNT_NUMBER, 0));
        Singleton.getInstance().setSumPassCount(sharedPreferences.getInt(SUM_PASSED_COUNT, 0));

    }


}


