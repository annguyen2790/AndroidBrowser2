package edu.temple.androidbrowser2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class PageListFragment extends Fragment {
    View v;
    PageListInterface pageListListener;
    ArrayList<PageViewerFragment> viewersList;

    public PageListFragment() {
        // Required empty public constructor
    }

    interface PageListInterface{
        ArrayList<PageViewerFragment> getArray();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_page_list, container, false);
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        pageListListener = null;

    }
}