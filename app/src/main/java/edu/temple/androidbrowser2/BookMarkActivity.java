package edu.temple.androidbrowser2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BookMarkActivity extends AppCompatActivity {
    ListView bookMarkListView;
    ArrayList<BookMark> bookMarksList;

    private static final String SHARED_PREFS= "MY_SHARED_PREF";
    private static final String SAVE_KEY= "TASK_LIST";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);
        setTitle("Book Marks");



        Intent toget = getIntent();
        bookMarksList = toget.getParcelableArrayListExtra("BOOKMARKS_ARRAYLIST");

        bookMarksList = loadArray();

        Log.e("HSHS", bookMarksList.get(0).getSiteTitle());

        bookMarkListView = (ListView) findViewById(R.id.list_bookMark);


     


        BookMarkAdapter adapter = new BookMarkAdapter(this, bookMarksList);
        bookMarkListView.setAdapter(adapter);
        bookMarkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplication(), bookMarksList.get(i).getURL(), Toast.LENGTH_SHORT).show();
                String loadUrl = bookMarksList.get(i).getURL();
                BrowserActivity bi = BrowserActivity.getInstance();
                bi.openNewPage();
                bi.okPress(loadUrl);
                finish();
            }

        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        saveList();


    }

    public void saveList(){
        SharedPreferences sp = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bookMarksList);
        editor.putString(SAVE_KEY, json);
        editor.commit();

    }

    public ArrayList<BookMark> loadArray(){
        SharedPreferences sp = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String json = sp.getString(SAVE_KEY, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<BookMark>>() {}.getType();
        bookMarksList = gson.fromJson(json, type);

        if(bookMarksList == null){
            bookMarksList = new ArrayList<>();
        }
        return  bookMarksList;

    }
}