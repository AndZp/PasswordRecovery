package ua.com.ukrelektro.passwordrec.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by User on 11.12.2015.
 */
public class ContractClass {
    public static final String AUTHORITY = "ua.com.ukrelektro.passwordrec.provider.ContractClass";
    private ContractClass() {}
    public static final class Codes implements BaseColumns {

        private Codes() {}
        public static final String CODES_TABLE_NAME ="codes";
        public static final String STATE_TABLE_NAME ="state";
        private static final String SCHEME = "content://";
        private static final String PATH_CODES = "/codes";
        private static final String PATH_CODES_ID = "/codes/";

        public static final int Codes_ID_PATH_POSITION = 1;
        public static final Uri CONTENT_URI =  Uri.parse(SCHEME + AUTHORITY + PATH_CODES);
        public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_CODES_ID);

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.codes";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.codes";
        public static final String DEFAULT_SORT_ORDER = "count DESC";

        public static final String CODE_COLUMN = "code";
        public static final String COUNT_COLUMN = "count";
        public static final String STATUS_COLUMN = "status";
        public static final String DATE_COLUMN = "date";

        public static final String[] DEFAULT_PROJECTION = new String[] {
                ContractClass.Codes._ID,
                ContractClass.Codes.CODE_COLUMN,
                ContractClass.Codes.COUNT_COLUMN,
                ContractClass.Codes.STATUS_COLUMN,
                ContractClass.Codes.DATE_COLUMN
        };
    }
}
