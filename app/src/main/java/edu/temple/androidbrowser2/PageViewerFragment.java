package edu.temple.androidbrowser2;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class PageViewerFragment extends Fragment {
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
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    pvListener.updateURL(url);

                }
            });
        }
        return v;
    }
}