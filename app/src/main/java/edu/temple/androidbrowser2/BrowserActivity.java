package edu.temple.androidbrowser2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.io.Serializable;

public class BrowserActivity extends AppCompatActivity implements PageViewerFragment.pageViewerInterface, PageControlFragment.PageControlListener,
    BrowserControlFragment.browserControlInterface, PagerFragment.PagerFragmentInterface, PageListFragment.PageListInterface {

    PagerFragment pagerFragment;
    FragmentManager fragmentManager;
    PageControlFragment pageControlFragment;
    BrowserControlFragment browserControlFragment;
    PageListFragment pageListFragment;
    public ArrayList<PageViewerFragment> viewerArray;
    private static final String LIST_KEY = "fragments";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        if (savedInstanceState != null) {
            viewerArray = (ArrayList<PageViewerFragment>) savedInstanceState.getSerializable(LIST_KEY);
        } else {
            viewerArray = new ArrayList<>();
        }

        addFragments();


    }
    public void addFragments(){
        fragmentManager = getSupportFragmentManager();
        Fragment temp;
        //add page control
        if((temp = fragmentManager.findFragmentById(R.id.page_control)) instanceof PageControlFragment){
            pageControlFragment = (PageControlFragment) temp;
        }else{
            pageControlFragment = new PageControlFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.page_control, pageControlFragment)
                    .commit();
        }
        //add browser control
        if((temp = fragmentManager.findFragmentById(R.id.browser_control) )instanceof BrowserControlFragment){
            browserControlFragment = (BrowserControlFragment) temp;
        }else{
            browserControlFragment = new BrowserControlFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.browser_control, browserControlFragment)
                    .commit();
        }
        //add pager fragment
        if ((temp = fragmentManager.findFragmentById(R.id.page_viewer)) instanceof PagerFragment){
            pagerFragment = (PagerFragment) temp;
        }else {
            pagerFragment = new PagerFragment();
            fragmentManager.beginTransaction().
                    add(R.id.page_viewer, pagerFragment)
                    .commit();

        }
        //add pageList
        if(findViewById(R.id.page_list) != null) {
            if ((temp = fragmentManager.findFragmentById(R.id.page_list)) instanceof PageListFragment) {
                pageListFragment = (PageListFragment) temp;
            } else {
                pageListFragment = new PageListFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.page_list, pageListFragment)
                        .commit();
            }
        }



    }

    @Override
    public void updateURL(String url) {
        pageControlFragment.refreshUrl(url);
    }

    @Override
    public void forwardPress() {

        viewerArray.get(pagerFragment.myViewPager.getCurrentItem()).goFor();
    }

    @Override
    public void backPress() {

        viewerArray.get(pagerFragment.myViewPager.getCurrentItem()).goBackward();
    }

    @Override
    public void okPress(String urlInput) {
        if(viewerArray.size() == 0){
            openNewPage();
        }


        if(!urlInput.startsWith("https://")){

            viewerArray.get(pagerFragment.myViewPager.getCurrentItem()).okPressed("https://" + urlInput);
        }else {

            viewerArray.get(pagerFragment.myViewPager.getCurrentItem()).okPressed(urlInput);
        }


    }

    @Override
    public void openNewPage() {
        if(viewerArray == null){
            viewerArray.add( new PageViewerFragment());
        }
        viewerArray.add(new PageViewerFragment());
        pagerFragment.myViewPager.getAdapter().notifyDataSetChanged();



    }

    @Override
    public ArrayList<PageViewerFragment> getPageViewerList() {
        return viewerArray;
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(LIST_KEY, viewerArray);
    }

    @Override
    public ArrayList<PageViewerFragment> getArray() {
        return viewerArray;
    }

    @Override
    public ViewPager getViewPager() {
        return pagerFragment.myViewPager;
    }
}