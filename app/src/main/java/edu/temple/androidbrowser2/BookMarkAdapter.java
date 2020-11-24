package edu.temple.androidbrowser2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BookMarkAdapter extends BaseAdapter implements ListAdapter {
    ArrayList<String> bookMarksList;
    Context context;

    public  BookMarkAdapter(Context context, ArrayList<String> bookMarksList){
        this.context = context;
        this.bookMarksList = bookMarksList;
    }
    @Override
    public int getCount() {
        return bookMarksList.size();
    }

    @Override
    public Object getItem(int i) {
        return bookMarksList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = view;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.custom_list, null);
        }
            TextView bookMarkTextView = (TextView) v.findViewById(R.id.list_item_string);

            bookMarkTextView.setText(bookMarksList.get(i).toString());

            Button DelButton = (Button) v.findViewById(R.id.delete_btn);

            DelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bookMarksList.remove(i);
                    notifyDataSetChanged();
                }
            });


        return v;
    }
}
