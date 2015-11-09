package ua.com.ukrelektro.passwordrec.ui;

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

import ua.com.ukrelektro.passwordrec.R;
import ua.com.ukrelektro.passwordrec.db.DatabaseHelper;
import ua.com.ukrelektro.passwordrec.model.CodeChecker;
import ua.com.ukrelektro.passwordrec.model.Status;
import ua.com.ukrelektro.passwordrec.ui.adapter.TabsPagerFragmentAdapter;

/**
 * Main Activity Java Class
 */
public class MainActivity extends AppCompatActivity {


    private static final int LAYOUT = R.layout.activity_main;

    private ViewPager mViewPager;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        initDb();
        initToolbar();
        initTabLayout();
        initActionButton();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * onClick handler for button "Worked and Next"
     *
     * @param view current view
     */
    public void nextNumber(View view) {

        switch (view.getId()) {
            case R.id.btnNext:
                CodeChecker.checkCode(Status.FAIL);
                initTabLayout();
                break;
            case R.id.btnWorked:
                CodeChecker.checkCode(Status.PASS);
                initTabLayout();
                break;
            default:
                break;
        }
    }

    /**
     * DataBase initialization
     */
    private void initDb() {
        mDatabaseHelper = DatabaseHelper.getInstance(this);
    }

    /**
     * onClick handler for FloatingActionButton
     */
    private void initActionButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

}
