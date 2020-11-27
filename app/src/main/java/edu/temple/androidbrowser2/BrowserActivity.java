package edu.temple.androidbrowser2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
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
    private static final String BOOK_KEY = "books";
    private static final String SHARED_PREFS= "MY_SHARED_PREF";
    private static final String SAVE_KEY= "TASK_LIST";
    public ArrayList<BookMark> bookmarks;
    public static BrowserActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        instance = this;
        /*Get back the original reference of the array list of pageViewerFragment*/
        if (savedInstanceState != null) {
            viewerArray = (ArrayList<PageViewerFragment>) savedInstanceState.getSerializable(LIST_KEY);
            bookmarks = (ArrayList<BookMark>) savedInstanceState.getSerializable(BOOK_KEY);
        } else {
            viewerArray = new ArrayList<>();
            bookmarks = new ArrayList<>();
        }
        loadArray();
        /*To have less confusion in onCreate()*/
        addFragments();


    }
    public static BrowserActivity getInstance() {
        return instance;
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
        pagerFragment.myViewPager.getAdapter().notifyDataSetChanged();
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
            pagerFragment.myViewPager.getAdapter().notifyDataSetChanged();
        }
        /*Load the url*/
        if(!urlInput.startsWith("https://")){
            viewerArray.get(pagerFragment.myViewPager.getCurrentItem()).okPressed("https://" + urlInput);
            String yolo = viewerArray.get(pagerFragment.myViewPager.getCurrentItem()).webView.getUrl();
            pagerFragment.myViewPager.getAdapter().notifyDataSetChanged();
            pageControlFragment.editText.setText(yolo);

        }else {
            viewerArray.get(pagerFragment.myViewPager.getCurrentItem()).okPressed(urlInput);
            String yolo = viewerArray.get(pagerFragment.myViewPager.getCurrentItem()).webView.getUrl();
            pageControlFragment.editText.setText(yolo);
        }


    }

    @Override
    public void openNewPage() {
        //just to prevent it from crashing when attempting to open multiple pages in landscape
        if(viewerArray == null){
            viewerArray.add( new PageViewerFragment());
            pagerFragment.myViewPager.getAdapter().notifyDataSetChanged();
        }
        //add pages. Profit!
        viewerArray.add(new PageViewerFragment());
        pagerFragment.myViewPager.getAdapter().notifyDataSetChanged();
        pagerFragment.myViewPager.setCurrentItem(viewerArray.size()  - 1);



    }

    @Override
    public void goToBookmarks() {
        Intent goToBookMarkActivity = new Intent(getApplicationContext(), BookMarkActivity.class);
        /*Put parcelable array list here*/
        goToBookMarkActivity.putParcelableArrayListExtra("BOOKMARKS_ARRAYLIST", bookmarks);
        startActivity(goToBookMarkActivity);
    }

    @Override
    public void addBookmarks() {
        String URL = viewerArray.get(pagerFragment.myViewPager.getCurrentItem()).webView.getUrl();
        String siteTitle = viewerArray.get(pagerFragment.myViewPager.getCurrentItem()).webView.getTitle() ;
        BookMark toAdd = new BookMark(URL, siteTitle);
        bookmarks.add(toAdd);
        //saveList();
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
        outState.putSerializable(BOOK_KEY, bookmarks);
    }

    @Override
    public ArrayList<PageViewerFragment> getArray() {
        return viewerArray;
    }

    @Override
    public ViewPager getViewPager() {
        return pagerFragment.myViewPager;
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveList();
    }

    public void saveList(){
        SharedPreferences sp = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bookmarks);
        editor.putString(SAVE_KEY, json);
        editor.commit();

    }

    public void loadArray(){
        SharedPreferences sp = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String json = sp.getString(SAVE_KEY, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<BookMark>>() {}.getType();
        bookmarks = gson.fromJson(json, type);

        if(bookmarks == null){
            bookmarks = new ArrayList<>();
        }

    }


}