package edu.temple.androidbrowser2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class PagerFragment extends Fragment {

    View v;
    ViewPager myViewPager;
    PagerFragmentInterface pagerFragmentListener;
    ArrayList<PageViewerFragment> viewerFragmentsArray;

    public PagerFragment() {
        // Required empty public constructor
    }
    interface PagerFragmentInterface{
        ArrayList<PageViewerFragment> getPageViewerList();
        void updateNewUrl(String url);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof PagerFragmentInterface){
            pagerFragmentListener = (PagerFragmentInterface) context;
        }else {
            throw new RuntimeException("Please implement PagerFragmentInterface");
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
        v = inflater.inflate(R.layout.fragment_pager, container, false);
        myViewPager = v.findViewById(R.id.viewPager);
        //get array list from activity
        viewerFragmentsArray = pagerFragmentListener.getPageViewerList();
        //communicate with activity
        myViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return viewerFragmentsArray.get(position);
            }

            @Override
            public int getCount() {
                return viewerFragmentsArray.size();
            }
        });

        myViewPager.addOnPageChangeListener( new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                if(viewerFragmentsArray.get(position).webView.getTitle() != null ){
                    getActivity().setTitle(viewerFragmentsArray.get(position).webView.getTitle());
                }
                String url = viewerFragmentsArray.get(position).webView.getUrl();
                if(url == null){
                    pagerFragmentListener.updateNewUrl("https:// Enter a url");
                }
                if(url != null){
                    pagerFragmentListener.updateNewUrl(url);
                }



            }
        });


        return v;
    }
}