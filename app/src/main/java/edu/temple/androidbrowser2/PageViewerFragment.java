package edu.temple.androidbrowser2;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.Serializable;


public class PageViewerFragment extends Fragment implements Serializable {
    View v;
    WebView webView;
    pageViewerInterface pvListener;

    public PageViewerFragment() {
        // Required empty public constructor
    }

    //declare interface to
    interface pageViewerInterface{
        void updateURL(String url);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof pageViewerInterface){
            pvListener = (pageViewerInterface) context;
        }else{
            throw new RuntimeException("Please implement pageViewerInterface");
        }
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //restore web session
        if(savedInstanceState == null){
            v = inflater.inflate(R.layout.fragment_page_viewer, container, false);
            webView = v.findViewById(R.id.web_view);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    pvListener.updateURL(url);
                }
            });
            if(savedInstanceState != null){
                webView.restoreState(savedInstanceState);
            }
        }

        return v;
    }

    public void goFor(){
        webView.goForward();
    }

    public void goBackward(){
        webView.goBack();
    }

    public void okPressed(String url){
        Log.e("Check", url);
        webView.loadUrl(url);
    }
    public String getLink(){
        return webView.getTitle();
    }
}