package ua.com.ukrelektro.passwordrec.ui.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ua.com.ukrelektro.passwordrec.R;
import ua.com.ukrelektro.passwordrec.model.Code;
import ua.com.ukrelektro.passwordrec.model.State;
import ua.com.ukrelektro.passwordrec.provider.ContractClass;
import ua.com.ukrelektro.passwordrec.ui.adapter.TabsPagerFragmentAdapter;

/**
 * Main Activity Java Class
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    private static final int LAYOUT = R.layout.activity_main;

    private ViewPager mViewPager;

    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        ButterKnife.bind(this);

        initToolbar();
        initTabLayout();

        getSupportLoaderManager().initLoader(0, null, this);


    }

    @Override
    protected void onStop() {
        super.onStop();
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
        return super.onOptionsItemSelected(item);

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
        setSupportActionBar(toolbar);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        //в этом методе мы создаем CursorLoader c определенным sql-запросом. В данном случае нам нужно выбрать все записи из таблицы Classes, и вместо условий выборки и сортировки задаем null.
        return new CursorLoader(
                this,
                ContractClass.Codes.CONTENT_URI, //uri для таблицы Classes
                ContractClass.Codes.DEFAULT_PROJECTION, //список столбцов, которые должны присутствовать в выборке
                null, // условие WHERE для выборки
                null, // аргументы для условия WHERE
                null); // порядок сортировки
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        Cursor cursor = (Cursor) data;
        ArrayList<Code> codesArr = new ArrayList<Code>();
        if(cursor.moveToFirst()) {
            int code = cursor.getInt(cursor.getColumnIndex(ContractClass.Codes.CODE_COLUMN));
            int count = cursor.getInt(cursor.getColumnIndex(ContractClass.Codes.COUNT_COLUMN));

            Code codeObj = new Code(code, count, State.NOT_CHECK);
            codesArr.add(codeObj);
            } while (cursor.moveToNext());

        cursor.close();
    }



    @Override
    public void onLoaderReset(Loader loader) {

    }
}


