package com.mj.methali;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mj.db.DatabaseContract;
import com.mj.db.DatabaseHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.text);

        checkAndMakeDb();

    }

    private void checkAndMakeDb() {
        DatabaseHelper dbh = new DatabaseHelper(this);
        SQLiteDatabase db = dbh.getReadableDatabase();

        boolean canWrite = !db.isReadOnly();
        boolean isEmpty = isDatabaseEmpty(db);

        Toast.makeText(this, "Empty : "+isEmpty+"\n Writeable : "+canWrite, Toast.LENGTH_SHORT).show();

        if (canWrite && isEmpty) {
            Methali[] methali = getMethaliFromFile();
            if (methali != null) {
                writeEmToDB(db, getMethaliFromFile());
            }
        }

        if (!isEmpty) {
            Cursor cursor = db.query(
                    DatabaseContract.Entry.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            cursor.moveToFirst();
            cursor.move(new Random().nextInt(cursor.getCount()));//random entry

            String kisw = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Entry.COLUMN_NAME_KISW));
            cursor.close();

            kisw += "";
            tv.setText(kisw);
        }

    }

    private void writeEmToDB(SQLiteDatabase db, Methali[] methalis) {
        for (Methali methali : methalis) {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.Entry.COLUMN_NAME_KISW, methali.kisw);
            values.put(DatabaseContract.Entry.COLUMN_NAME_ENG, methali.eng);
            values.put(DatabaseContract.Entry.COLUMN_NAME_ALPHA, methali.alpha);
            values.put(DatabaseContract.Entry.COLUMN_NAME_CATEGORY, methali.cat);
            values.put(DatabaseContract.Entry.COLUMN_NAME_USE, methali.use);


            long newRowId = db.insert(
                    DatabaseContract.Entry.TABLE_NAME,
                    DatabaseContract.Entry.COLUMN_NAME_USE,
                    values);
        }
    }

    private Methali[] getMethaliFromFile() {
        Methali[] methali = null;
        try {
            InputStream stream = getAssets().open("methali.json");

            Gson gson = new Gson();
            methali = gson.fromJson(new InputStreamReader(stream), Methali[].class);

        } catch (IOException e) {
            e.printStackTrace();
            toast("Failed I/O: "+e.getLocalizedMessage());
        }

        return methali;
    }

    class Methali {
        String kisw;
        String eng;
        String alpha;
        String cat;
        int use;
    }


    private void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private boolean isDatabaseEmpty(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT * FROM "+DatabaseContract.Entry.TABLE_NAME, null);
        return !cursor.moveToFirst();
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
}
