package edu.temple.androidbrowser2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import java.util.ArrayList;

public class BrowserActivity extends AppCompatActivity implements PageViewerFragment.pageViewerInterface, PageControlFragment.PageControlListener,
    BrowserControlFragment.browserControlInterface, PagerFragment.PagerFragmentInterface{

    PagerFragment pagerFragment;
    FragmentManager fragmentManager;
    PageControlFragment pageControlFragment;
    BrowserControlFragment browserControlFragment;
    ArrayList<PageViewerFragment> viewerArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        if(savedInstanceState == null){
            viewerArray = new ArrayList<>();
        }else{

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





    }

    @Override
    public void updateURL(String url) {

    }

    @Override
    public void forwardPress() {

    }

    @Override
    public void backPress() {

    }

    @Override
    public void okPress(String urlInput) {

    }

    @Override
    public void openNewPage() {

    }

    @Override
    public ArrayList<PageViewerFragment> getPageViewerList() {
        return viewerArray;
    }
}