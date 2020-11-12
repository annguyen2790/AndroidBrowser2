package edu.temple.androidbrowser2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class PageListFragment extends Fragment {
    View v;
    PageListInterface pageListListener;
    ArrayList<PageViewerFragment> viewersList;
    ListView listView;
    ViewPager viewPager;
    PageViewerFragmentAdapter adapter;

    public PageListFragment() {
        // Required empty public constructor
    }

    interface PageListInterface{
        ArrayList<PageViewerFragment> getArray();
        ViewPager getViewPager();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof PageListInterface){
            pageListListener = (PageListInterface) context;
        }else{
            throw new RuntimeException("Please implement PageListInterface");
        }
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
        listView = v.findViewById(R.id.siteList);
        viewersList = pageListListener.getArray();
        viewPager = pageListListener.getViewPager();
        adapter = new PageViewerFragmentAdapter(getActivity(), viewersList, viewPager);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                viewPager.setCurrentItem(i);
            }
        });

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        pageListListener = null;

    }
}