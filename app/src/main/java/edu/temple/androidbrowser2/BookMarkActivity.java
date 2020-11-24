package edu.temple.androidbrowser2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class BookMarkActivity extends AppCompatActivity {
    ListView bookMarkListView;
    ArrayList<String> bookMarksList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);
        bookMarksList = new ArrayList<>();
        bookMarksList.add("Item 1");
        bookMarksList.add("Item 2");
        bookMarkListView = (ListView) findViewById(R.id.list_bookMark);
        BookMarkAdapter adapter = new BookMarkAdapter(this, bookMarksList);
        bookMarkListView.setAdapter(adapter);
    }
}