package edu.temple.androidbrowser2;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(savedInstanceState != null){

        }else {
            v = inflater.inflate(R.layout.fragment_page_viewer, container, false);
            webView = v.findViewById(R.id.web_view);
            webView.getSettings().setJavaScriptEnabled(true);
            myWebClient wb = new myWebClient();
            webView.setWebViewClient(wb);
        }
        return v;
    }
    public class myWebClient extends WebViewClient{
        @Override
        public void onPageFinished (WebView webView, String url) {
            super.onPageFinished(webView, url);
            pvListener.updateURL(url);
        }
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
}