package edu.temple.androidbrowser2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class PageViewerFragmentAdapter extends BaseAdapter {

    ArrayList<PageViewerFragment> viewerArray;
    final Context context;

    public PageViewerFragmentAdapter(Context context, ArrayList<PageViewerFragment> viewerArray){
        this.viewerArray = viewerArray;
        this.context = context;
    }


    @Override
    public int getCount() {
        return viewerArray.size();
    }

    @Override
    public Object getItem(int i) {
        return viewerArray.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
