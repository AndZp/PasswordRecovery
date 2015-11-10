package ua.com.ukrelektro.passwordrec.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.io.InputStream;
import java.util.ArrayList;

import ua.com.ukrelektro.passwordrec.R;
import ua.com.ukrelektro.passwordrec.model.Code;

public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns {
    private static DatabaseHelper mInstance;

    private static final String DATABASE_NAME = "codesdb.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE = "codes";

    public static final String CODE_COLUMN = "code";
    public static final String COUNT_COLUMN = "count";

    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + DATABASE_TABLE + " (" + BaseColumns._ID
            + " integer primary key autoincrement, " + CODE_COLUMN
            + " integer, " + COUNT_COLUMN + " integer);";

    public static final int PASSCODES_FILE = R.raw.passcode;


    private Context context;
    private ArrayList<Code> codesList;

    public static DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.codesList = DbUtils.getListCodesFromCSV(getInputStreamFromCSVFile());
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);
        initDb(db);
    }

    private void initDb(SQLiteDatabase db) {

        ContentValues values;
        for (int i = 0; i < codesList.size(); i++) {
            values = new ContentValues();
            values.put(CODE_COLUMN, codesList.get(i).getCode());
            values.put(COUNT_COLUMN, codesList.get(i).getCount());
            db.insert(DATABASE_TABLE, COUNT_COLUMN, values);
        }
    }

    public InputStream getInputStreamFromCSVFile() {
        return context.getResources().openRawResource(PASSCODES_FILE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
