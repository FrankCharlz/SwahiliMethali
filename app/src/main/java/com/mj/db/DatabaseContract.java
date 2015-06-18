package com.mj.db;

import android.provider.BaseColumns;

public final class DatabaseContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DatabaseContract() {}

    /* Inner class that defines the table contents */
    public static abstract class Entry implements BaseColumns {
        public static final String TABLE_NAME = "methali_table";
        public static final String COLUMN_NAME_KISW = "kisw";
        public static final String COLUMN_NAME_ENG = "eng";
        public static final String COLUMN_NAME_ALPHA = "alpha";
        public static final String COLUMN_NAME_USE = "use";
        public static final String COLUMN_NAME_CATEGORY = "category";

    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String VARCHAR_TYPE = " VARCHAR(5)";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Entry.TABLE_NAME + " (" +
                    Entry._ID + " INTEGER PRIMARY KEY," +
                    Entry.COLUMN_NAME_KISW + TEXT_TYPE + COMMA_SEP +
                    Entry.COLUMN_NAME_ENG + TEXT_TYPE + COMMA_SEP +
                    Entry.COLUMN_NAME_ALPHA + VARCHAR_TYPE + COMMA_SEP +
                    Entry.COLUMN_NAME_USE + INTEGER_TYPE + COMMA_SEP +
                    Entry.COLUMN_NAME_CATEGORY + TEXT_TYPE +
            " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;
}