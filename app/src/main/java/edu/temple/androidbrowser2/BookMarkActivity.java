package edu.temple.androidbrowser2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
    Button closedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);
        setTitle("Book Marks");



        Intent toget = getIntent();
        bookMarksList = toget.getParcelableArrayListExtra("BOOKMARKS_ARRAYLIST");
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

                Intent resultIntent = new Intent ();
                resultIntent.putParcelableArrayListExtra("resultArray", bookMarksList);
                setResult(RESULT_OK, resultIntent);

                finish();
            }

        });

        closedButton = findViewById(R.id.closeButton);
        closedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(BookMarkActivity.this, BrowserActivity.class);
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try{
            Intent intent = new Intent(BookMarkActivity.this, BrowserActivity.class);
            startActivity(intent);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
        }
    }
}