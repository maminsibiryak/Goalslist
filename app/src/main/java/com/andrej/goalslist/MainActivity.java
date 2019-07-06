package com.$username$.goalslist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button buttonRead;
    private Button buttonDelete;
    private ListView list;
    private EditText editText;
    private final ArrayList<String> catNames = new ArrayList<>();
    private final ArrayList<Item> items = new ArrayList<>();
    private MyListAdapter myListAdapter;
    private DBHelper dbHelper;
    private final String LOG_TAG = "myLogs";
    private ContentValues cv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);


        // создаем объект для данных
        cv = new ContentValues();


        editText = (EditText) findViewById(R.id.editText);

        buttonRead = findViewById(R.id.read);
        buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // делаем запрос всех данных из таблицы mytable, получаем
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                /*

                int clearCount = db.delete("mytable", null, null);
                Log.d(LOG_TAG, "deleted rows count = " + clearCount);*/
                Cursor c = db.query("mytable", null, null, null, null, null, null);
                // ставим позицию курсора на первую строку выборки // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {
// определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("name");
                    do {
// получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(idColIndex) +
                                        ", name = " + c.getString(nameColIndex));

                        items.add(new Item(c.getString(nameColIndex)));

// переход на следующую строку
// а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                myListAdapter.notifyDataSetChanged();
            }
        });


        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item_temp = editText.getText().toString();
                items.add(0, new Item(item_temp));
                myListAdapter.notifyDataSetChanged();
                editText.setText("");

                // получаем данные из полей ввода

                Log.d(LOG_TAG, "string :" + item_temp);
                // подключаемся к БД
                SQLiteDatabase dbm = dbHelper.getWritableDatabase();
                cv.put("name", item_temp);
                // вставляем запись и получаем ее ID
                long rowID = dbm.insert("mytable", null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);

            }
        });

        myListAdapter = new MyListAdapter(this, items);
// настраиваем список
        ListView lvMain = (ListView) findViewById(R.id.list);
        lvMain.setAdapter(myListAdapter);

        buttonDelete = findViewById(R.id.delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                int clearCount = db.delete("mytable", null, null);
                Log.d(LOG_TAG, "deleted rows count = " + clearCount);
                items.removeAll(items);
                myListAdapter.notifyDataSetChanged();
            }
        });
        dbHelper.close();

/*// получаем экземпляр элемента ListView
        list= (ListView) findViewById(R.id.list);
        editText = (EditText) findViewById(R.id.editText);

        // Создаём пустой массив для хранения имен котов



        // Создаём адаптер ArrayAdapter, чтобы привязать массив к ListView
         final ArrayAdapter<String>  adapter = new ArrayAdapter<>(this,
                R.layout.my_list_item, catNames);

         myListAdapter = new MyListAdapter(this,items);

         list.setAdapter(myListAdapter);

        // Привяжем массив через адаптер к ListView
        //list.setAdapter(adapter);

        // Прослушиваем нажатия клавиш
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        catNames.add(0, editText.getText().toString());
                        myListAdapter.notifyDataSetChanged();
                        editText.setText("");
                        return true;
                    }
                return false;
            }
        });*/


    }


}
