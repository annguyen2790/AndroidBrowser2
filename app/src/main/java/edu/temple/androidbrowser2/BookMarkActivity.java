package edu.temple.androidbrowser2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class BookMarkActivity extends AppCompatActivity {
    ListView bookMarkListView;
    ArrayList<BookMark> bookMarksList;

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
                finish();

            }

        });
    }
}