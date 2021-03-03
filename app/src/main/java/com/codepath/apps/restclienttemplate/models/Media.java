package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Media {

    public String type;
    public String mediaUrl;

    Media(){}

    public static Media fromJson(JSONObject jsonObject) throws JSONException {
        Media media = new Media();
        media.type = jsonObject.getString("type");
        media.mediaUrl = jsonObject.getString("media_url");

        return media;
    }
}
