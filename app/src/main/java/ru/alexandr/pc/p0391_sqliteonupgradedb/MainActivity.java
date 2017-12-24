package ru.alexandr.pc.p0391_sqliteonupgradedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db = null;
    DBHelper dbHelper =  null;
    //String s = null;
    final String publicLog = "LogPub";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(publicLog, "Начало");
        Log.d(publicLog, "БД создана");
        dbHelper = new DBHelper(this);
        //s = "1";

        //Log.d(publicLog, "Bug: " + dbHelper.getWritableDatabase().toString() + " " + db);
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
        } else
            Log.d(publicLog, s + ". Cursor is null!");
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "Baza", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String[] name = {"Иван", "Александр", "Мария", "Александра", "Максим"};
            String[] position = {"дворник", "официант", "таксист", "продавец", "строитель"};

            Log.d(publicLog, "Создание таблицы");
            sqLiteDatabase.execSQL("create table People (" +
                    "id integer primary key autoincrement," +
                    "name text," +
                    "position text)");

            Log.d(publicLog, "Заполняем таблицу");
            ContentValues cv = new ContentValues();
            for (int i = 0; i < name.length; i++) {
                cv.clear();
                cv.put("name", name[i]);
                cv.put("position", position[i]);
                sqLiteDatabase.insert("People", null, cv);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }


}
