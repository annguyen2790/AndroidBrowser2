package edu.temple.androidbrowser2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class BookMarkActivity extends AppCompatActivity {
    ListView bookMarkListView;
    ArrayList<BookMark> bookMarksList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);
        Intent toget = getIntent();
        bookMarksList = toget.getParcelableArrayListExtra("BOOKMARKS_ARRAYLIST");
        bookMarkListView = (ListView) findViewById(R.id.list_bookMark);
        BookMarkAdapter adapter = new BookMarkAdapter(this, bookMarksList);
        bookMarkListView.setAdapter(adapter);
    }
}