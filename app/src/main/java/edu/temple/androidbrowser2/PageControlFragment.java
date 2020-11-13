package edu.temple.androidbrowser2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class PageControlFragment extends Fragment {

    EditText editText;
    ImageButton okButton;
    ImageButton backButton;
    ImageButton nextButton;
    PageControlListener listener;

    interface PageControlListener{
        void forwardPress();
        void backPress();
        void okPress(String urlInput);
    }
    public PageControlFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PageControlListener) {
            listener = (PageControlListener) context;
        } else {
            throw new RuntimeException("Please implement PageControlListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_page_control, container, false);
        editText = v.findViewById(R.id.url_editText);
        nextButton = v.findViewById(R.id.forward_button);
        backButton = v.findViewById(R.id.back_button);
        okButton = v.findViewById(R.id.ok_button);

        //Communication with Browser Activity
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = editText.getText().toString();
                Log.e("check input", input);
                listener.okPress(input);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.forwardPress();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.backPress();
            }
        });



        return v;
    }

}