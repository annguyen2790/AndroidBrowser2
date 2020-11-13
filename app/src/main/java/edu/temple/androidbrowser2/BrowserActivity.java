package edu.temple.androidbrowser2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import java.util.ArrayList;


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
        /*Get back the original reference of the array list of pageViewerFragment*/
        if (savedInstanceState != null) {
            viewerArray = (ArrayList<PageViewerFragment>) savedInstanceState.getSerializable(LIST_KEY);
        } else {
            viewerArray = new ArrayList<>();
        }
        /*To have less confusion in onCreate()*/
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
        pageControlFragment.editText.setText(viewerArray.get(pagerFragment.myViewPager.getCurrentItem()).webView.getUrl());
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
        /*So null pointer error would not crash the app when user did not add a new tab*/
        if(viewerArray.size() == 0){
            openNewPage();
        }
        /*Load the url*/
        if(!urlInput.startsWith("https://")){
            pageControlFragment.editText.setText(urlInput);
            viewerArray.get(pagerFragment.myViewPager.getCurrentItem()).okPressed("https://" + urlInput);
        }else {
            pageControlFragment.editText.setText(urlInput);
            viewerArray.get(pagerFragment.myViewPager.getCurrentItem()).okPressed(urlInput);
        }


    }

    @Override
    public void openNewPage() {
        //just to prevent it from crashing when attempting to open multiple pages in landscape
        if(viewerArray == null){
            viewerArray.add( new PageViewerFragment());
        }
        //add pages. Profit!
        viewerArray.add(new PageViewerFragment());
        pagerFragment.myViewPager.getAdapter().notifyDataSetChanged();
        pagerFragment.myViewPager.setCurrentItem(viewerArray.size()  - 1);



    }

    @Override
    public ArrayList<PageViewerFragment> getPageViewerList() {
        return viewerArray;
    }

    @Override
    public void updateNewUrl(String url) {
        pageControlFragment.editText.setText(url);
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