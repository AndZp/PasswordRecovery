package ua.com.ukrelektro.passwordrec.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ua.com.ukrelektro.passwordrec.R;
import ua.com.ukrelektro.passwordrec.model.Code;
import ua.com.ukrelektro.passwordrec.model.MyApplication;
import ua.com.ukrelektro.passwordrec.model.Singleton;
import ua.com.ukrelektro.passwordrec.model.State;

public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns {
    private static DatabaseHelper mInstance;

    private static final String DATABASE_NAME = "codesdb.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE = "codes";

    private static final String CODE_COLUMN = "code";
    private static final String COUNT_COLUMN = "count";
    private static final String STATUS_COLUMN = "status";
    private static final String DATE_COLUMN = "date";

    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + DATABASE_TABLE + " (" + BaseColumns._ID
            + " integer primary key autoincrement, " + CODE_COLUMN
            + " integer, " + COUNT_COLUMN + " integer, " + STATUS_COLUMN
            + " TEXT not null, " + DATE_COLUMN + " TEXT);";

    public static final int PASSCODES_FILE = R.raw.passcode;


    private Context context;
    private ArrayList<Code> codesFromCSV;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private String dateString;


    public static DatabaseHelper getInstance() {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(MyApplication.getAppContext());

        }
        return mInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);
        codesFromCSV = getListCodesFromCSV(context.getResources().openRawResource(PASSCODES_FILE));
        addListCodesToDataBase(codesFromCSV, db);
        initAllCodesfromDb(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public static void initDataBase() {
        DatabaseHelper databaseHelper = getInstance();
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        databaseHelper.initAllCodesfromDb(sqLiteDatabase);
    }

    /**
     * Parse CSV file to List and add data to Singleton
     *
     * @param inputStream intputStream of CSV file
     * @return ArrayList of Code object from CSV file
     */
    private static ArrayList<Code> getListCodesFromCSV(InputStream inputStream) {


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
        Singleton.getInstance().setCodesList(resultList);
        Singleton.getInstance().setSumCount(sumOfCounts);
        Singleton.getInstance().setSumCode(sumOfCodes);
        return resultList;
    }

    private void addListCodesToDataBase(ArrayList<Code> codesList, SQLiteDatabase db) {

        ContentValues values;
        for (int i = 0; i < codesList.size(); i++) {
            values = new ContentValues();
            Code code = codesList.get(i);
            values.put(CODE_COLUMN, code.getCode());
            values.put(COUNT_COLUMN, code.getCount());
            values.put(STATUS_COLUMN, code.getStatus().toString());
            if (code.getDate() != null)
                values.put(DATE_COLUMN, dateFormat.format(code.getDate()));
            db.insert(DATABASE_TABLE, COUNT_COLUMN, values);
        }
    }

    public void updateCodesInDataBase(ArrayList<Code> updateList) {

        ContentValues values;
        for (int i = 0; i < updateList.size(); i++) {
            values = new ContentValues();
            Code code = updateList.get(i);

            values.put(COUNT_COLUMN, code.getCount());
            getWritableDatabase().update(DATABASE_TABLE, values, CODE_COLUMN + " =" + code.getCode(), null);
        }
    }

    private void initAllCodesfromDb(SQLiteDatabase db) {
        ArrayList<Code> codeList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);


        int sumOfCount = 0;

        if (cursor.moveToFirst()) {
            do {
                int code = cursor.getInt(1);
                int count = cursor.getInt(2);

                State status = State.valueOf(cursor.getString(3));
                Code codeObj = new Code(code, count, status);

                // Date parse. If Data != null, add Data to Code Object
                Date date = null;
                dateString = cursor.getString(4);
                if (dateString != null) {
                    try {
                        date = dateFormat.parse(dateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    codeObj.setDate(date);
                }
                codeList.add(codeObj);

                sumOfCount += count;

            } while (cursor.moveToNext());
        }

        Singleton.getInstance().setSumCount(sumOfCount);
        Singleton.getInstance().setCodesList(codeList);
    }

    public void updateStatusInDataBase(ArrayList<Code> updateList) {
        ContentValues values;
        for (int i = 0; i < updateList.size(); i++) {
            Code code = updateList.get(i);

            values = new ContentValues();

            values.put(STATUS_COLUMN, code.getStatus().toString());

            if (code.getDate() != null) {
                values.put(DATE_COLUMN, dateFormat.format(code.getDate()));
            } else {
                values.put(DATE_COLUMN, (byte[]) null);
            }
            getWritableDatabase().update(DATABASE_TABLE, values, CODE_COLUMN + " =" + code.getCode(), null);
        }
    }


}
