package edu.temple.androidbrowser2;

import android.os.Parcel;
import android.os.Parcelable;

public class BookMark implements Parcelable {
    String URL;
    String siteTitle;

    public BookMark(String URL, String siteTitle){
        this.URL = URL;
        this.siteTitle = siteTitle;
    }

    public String getURL(){
        return this.URL;
    }

    public String getSiteTitle(){
        return this.siteTitle;
    }

    protected BookMark(Parcel in) {
        URL = in.readString();
        siteTitle = in.readString();
    }

    public static final Creator<BookMark> CREATOR = new Creator<BookMark>() {
        @Override
        public BookMark createFromParcel(Parcel in) {
            return new BookMark(in);
        }

        @Override
        public BookMark[] newArray(int size) {
            return new BookMark[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(URL);
        parcel.writeString(siteTitle);
    }
}
