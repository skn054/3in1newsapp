package com.example.bhanu.news;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bhanu kiran on 23/08/2016.
 */
public class News implements Parcelable {
    private String desc;
    private String title;
    private String url;
    private String urlToImage;
    private String date;

    public News(JSONObject obj) throws JSONException {

        desc=obj.getString("description");
        title=obj.getString("title");
        url=obj.getString("url");
        urlToImage=obj.getString("urlToImage");
        date=obj.getString("publishedAt");

    }

    public News(Parcel parcel) {
        desc=parcel.readString();
        title=parcel.readString();
        url=parcel.readString();
        urlToImage=parcel.readString();
        date=parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(desc);
        parcel.writeString(title);
        parcel.writeString(url);
        parcel.writeString(urlToImage);
        parcel.writeString(date);

    }
    public static final Creator<News> CREATOR =new Creator<News>() {
        @Override
        public News createFromParcel(Parcel parcel) {

            return new News(parcel);

        }

        @Override
        public News[] newArray(int i) {
            return new News[0];
        }
    };
    public String getDesc()
    {
        return desc;
    }
    public String getTitle()
    {
        return title;
    }
    public String getUrl()
    {
        return url;
    }
    public String getUrlToImage()
    {
        return urlToImage;
    }
    public String getDate()
    {
        return date;
    }





}
