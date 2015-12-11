package ua.com.ukrelektro.passwordrec.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ua.com.ukrelektro.passwordrec.R;
import ua.com.ukrelektro.passwordrec.model.Code;
import ua.com.ukrelektro.passwordrec.provider.ContractClass;

/**
 * Created by User on 11.12.2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    public static final int PASSCODES_FILE = R.raw.passcode;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    private static final String DATABASE_NAME = "codesDB";
    private static final int DATABASE_VERSION = 1;

    private static final String CODE_TABLE_NAME = ContractClass.Codes.CODES_TABLE_NAME;
    private static final String STATE_TABLE_NAME = ContractClass.Codes.STATE_TABLE_NAME;

    private static final String CODE_COLUMN = "code";
    private static final String COUNT_COLUMN = "count";
    private static final String STATUS_COLUMN = "status";
    private static final String DATE_COLUMN = "date";

    private static final String NUMBERS_CODES_COLUMN = "number";
    private static final String NUMBERS_CHEKED_CODES_COLUMN = "checked";
    private static final String SUM_COUNT_COLUMN = "sumCount";
    private static final String SUM_CHEKED_COUNT_COLUMN = "sumCheckedCount";

    private static final String CODES_DATABASE_CREATE_SCRIPT = "create table " + CODE_TABLE_NAME + " ("
            + BaseColumns._ID + " integer primary key autoincrement, "
            + CODE_COLUMN + " integer, "
            + COUNT_COLUMN + " integer, "
            + STATUS_COLUMN  + " TEXT not null, "
            + DATE_COLUMN + " TEXT);";


    private static final String STATUS_DATABASE_CREATE_SCRIPT = "create table " + STATE_TABLE_NAME + " ("
            + BaseColumns._ID + " integer primary key autoincrement, "
            + NUMBERS_CODES_COLUMN + " integer, "
            + NUMBERS_CHEKED_CODES_COLUMN + " integer, "
            + STATUS_COLUMN + " integer, "
            + SUM_COUNT_COLUMN + " integer, "
            + SUM_CHEKED_COUNT_COLUMN + " integer);";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CODES_DATABASE_CREATE_SCRIPT);
        db.execSQL(STATUS_DATABASE_CREATE_SCRIPT);

        ArrayList<Code> codesFromCSV = CSVParser.getListCodesFromCSV(context.getResources().openRawResource(PASSCODES_FILE));
        saveListCodesToDataBase(codesFromCSV, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CODE_TABLE_NAME);
        onCreate(db);
    }

    /**
     * Save list of codes in Database
     *
     * @param codesList list of codes
     * @param db        database
     */
    private void saveListCodesToDataBase(ArrayList<Code> codesList, SQLiteDatabase db) {
        int numbersCodes = 0;
        int sumCounts = 0;

        ContentValues values;
        for (int i = 0; i < codesList.size(); i++) {
            values = new ContentValues();
            Code code = codesList.get(i);

            numbersCodes++;
            sumCounts += code.getCount();

            values.put(CODE_COLUMN, code.getCode());
            values.put(COUNT_COLUMN, code.getCount());
            values.put(STATUS_COLUMN, code.getStatus().toString());

            if (code.getDate() != null)
                values.put(DATE_COLUMN, dateFormat.format(code.getDate()));

            db.insert(CODE_TABLE_NAME, COUNT_COLUMN, values);
        }

        values = new ContentValues();
        values.put(NUMBERS_CODES_COLUMN, numbersCodes);
        values.put(SUM_COUNT_COLUMN, sumCounts);
        db.insert(STATE_TABLE_NAME, NUMBERS_CODES_COLUMN, values );
    }
}
