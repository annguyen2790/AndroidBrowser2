package edu.temple.androidbrowser2;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BookMarkAdapter extends BaseAdapter implements ListAdapter {
    ArrayList<BookMark> bookMarksList;
    Context context;

    public  BookMarkAdapter(Context context, ArrayList<BookMark> bookMarksList){
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

        bookMarkTextView.setText(bookMarksList.get(i).getSiteTitle());

        Button DelButton = (Button) v.findViewById(R.id.delete_btn);

        DelButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    final AlertDialog alertDialog =  new AlertDialog.Builder(context)
                            .setTitle("Delete Bookmark")
                            .setMessage("Remove bookmarks from the bookmark list?")
                            .setPositiveButton("Yes", null)
                            .setNegativeButton("No", null)
                            .show();

                    Button yesButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    Button noButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                    yesButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(context, "Removed from bookmark list", Toast.LENGTH_SHORT).show();
                            removeBookMark(i);
                            alertDialog.dismiss();
                        }
                    });

                    noButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });


                }
        });
        bookMarkTextView.setPadding(5, 8, 8, 5);
        bookMarkTextView.setGravity(Gravity.CENTER);
        bookMarkTextView.setTextSize(14);



        return v;
    }

    public void removeBookMark(int i ){
        bookMarksList.remove(i);
        notifyDataSetChanged();
    }

}
