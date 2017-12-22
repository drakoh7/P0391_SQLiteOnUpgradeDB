package ru.alexandr.pc.p0391_sqliteonupgradedb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db = null;
    DBHelper dbHelper = null;
    final String publicLog = "LogPub";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(publicLog, "Начало");
        Log.d(publicLog, "БД создана");
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        writeLogs(db);

    }

    private void writeLogs(SQLiteDatabase db) {
        Cursor c = db.rawQuery("select * from People", null);
        logCursor(c, "Table People");
        c.close();
    }

    private void logCursor(Cursor c, String s) {
        if (c != null) {
            if (c.moveToFirst()) {

                StringBuilder stringBuilder = new StringBuilder();
                Log.d(publicLog, s + ". " + c.getCount() + " rows" + ". " + stringBuilder.length());
                do {
                    Log.d(publicLog, "Вход в цикл извлечения данных из Cursor.");
                    stringBuilder.setLength(0);
                    for (String columnName : c.getColumnNames()) {
                        stringBuilder.append(columnName + "=" + c.getString(c.getColumnIndex(columnName)) + "; ");
                    }
                    Log.d(publicLog, stringBuilder.toString());
                }
                while (c.moveToNext());
            }
        }
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "Baza", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table People (" +
                    "");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }


}
