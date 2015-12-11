package ua.com.ukrelektro.passwordrec.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.HashMap;

import ua.com.ukrelektro.passwordrec.DAO.DataBaseHelper;
import ua.com.ukrelektro.passwordrec.model.State;

public class CodesContentProvider extends ContentProvider {
    private static final int CODES = 1;
    private static final int CODE = 2;
    private static final int CHECKED_CODES = 3;
    private static final int UNCHECK_CODES = 4;

    private static final UriMatcher sUriMatcher;

    private static HashMap sCodesProjectionMap;
    private DataBaseHelper dbHelper;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(ContractClass.AUTHORITY, "codes", CODES);
        sUriMatcher.addURI(ContractClass.AUTHORITY, "codes/#", CODE);
       /* sUriMatcher.addURI(ContractClass.AUTHORITY, "codes", CHECKED_CODES);
        sUriMatcher.addURI(ContractClass.AUTHORITY, "codes", UNCHECK_CODES);*/

        sCodesProjectionMap = new HashMap();
        for (int i = 0; i < ContractClass.Codes.DEFAULT_PROJECTION.length; i++) {
            sCodesProjectionMap.put(
                    ContractClass.Codes.DEFAULT_PROJECTION[i],
                    ContractClass.Codes.DEFAULT_PROJECTION[i]);
        }
    }



    public CodesContentProvider() {
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DataBaseHelper(getContext());
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case CODES:
                return ContractClass.Codes.CONTENT_TYPE;
            case CODE:
                return ContractClass.Codes.CONTENT_ITEM_TYPE;
            case CHECKED_CODES:
                return ContractClass.Codes.CONTENT_TYPE;
            case UNCHECK_CODES:
                return ContractClass.Codes.CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        if (sUriMatcher.match(uri) != CODES) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        }
        else {
            values = new ContentValues();
        }

        long rowId = -1;
        Uri rowUri = Uri.EMPTY;


        if (values.containsKey(ContractClass.Codes.CODE_COLUMN) == false) {
            values.put(ContractClass.Codes.CODE_COLUMN, 0);
        }
        if (values.containsKey(ContractClass.Codes.COUNT_COLUMN) == false) {
            values.put(ContractClass.Codes.COUNT_COLUMN, 0);
        }
        if (values.containsKey(ContractClass.Codes.STATUS_COLUMN) == false) {
            values.put(ContractClass.Codes.STATUS_COLUMN, State.NOT_CHECK.toString());
        }
        if (values.containsKey(ContractClass.Codes.DATE_COLUMN) == false) {
            values.put(ContractClass.Codes.DATE_COLUMN, "");
        }

        if (rowId > 0) {
            rowUri = ContentUris.withAppendedId(ContractClass.Codes.CONTENT_ID_URI_BASE, rowId);
            getContext().getContentResolver().notifyChange(rowUri, null);
        }
        return rowUri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String orderBy = null;

        switch (sUriMatcher.match(uri)) {
            case CODES:
                qb.setTables(ContractClass.Codes.CODES_TABLE_NAME);
                qb.setProjectionMap(sCodesProjectionMap);
                orderBy = ContractClass.Codes.DEFAULT_SORT_ORDER;
                break;
            case CODE:
                qb.setTables(ContractClass.Codes.CODES_TABLE_NAME);
                qb.setProjectionMap(sCodesProjectionMap);
                qb.appendWhere(ContractClass.Codes.CODE_COLUMN + "=" + uri.getPathSegments().get(ContractClass.Codes.Codes_ID_PATH_POSITION));
                orderBy = ContractClass.Codes.DEFAULT_SORT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
