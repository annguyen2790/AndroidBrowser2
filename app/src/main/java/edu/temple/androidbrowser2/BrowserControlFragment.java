package edu.temple.androidbrowser2;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.Serializable;

public class BrowserControlFragment extends Fragment implements Serializable {
    ImageButton addTab;
    ImageButton addBookmark;
    ImageButton goToBookmark;
    View v;

    browserControlInterface browserControlListner;

    public BrowserControlFragment() {
        // Required empty public constructor
    }

    interface browserControlInterface{
        void openNewPage();
        void goToBookmarks();
        void addBookmarks();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof browserControlInterface){
            browserControlListner = (browserControlInterface)context;
        }else{
            throw new RuntimeException("Please implement browserControlInterface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_browser_control, container, false);
        addTab = v.findViewById(R.id.add_tab_button);
        addTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browserControlListner.openNewPage();
            }
        });

        addBookmark =  v.findViewById(R.id.add_bookmark);
        addBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Adding Bookmark", Toast.LENGTH_SHORT).show();
                browserControlListner.addBookmarks();
            }
        });

        goToBookmark = v.findViewById(R.id.bookMark);
        goToBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Going to Bookmark ", Toast.LENGTH_SHORT).show();
                browserControlListner.goToBookmarks();
            }
        });



        return v;
    }
}