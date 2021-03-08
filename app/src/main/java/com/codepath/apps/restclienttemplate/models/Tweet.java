package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId"))
public class Tweet {

    @ColumnInfo
    @PrimaryKey
    public long id;
    @ColumnInfo
    public String body;
    @ColumnInfo
    public String createdAt;
    @ColumnInfo
    public long userId;

    @Ignore
    public String mediaUrl;
    @Ignore
    public String type;

    @Ignore
    public User user;
    //@Ignore
    //public Media media;

    //empty constructor needed by the Parceler Library
    public Tweet() {}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");
        User user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.user = user;
        tweet.userId = user.id;

        /*JSONObject entities = jsonObject.getJSONObject("entities");
        if (entities.has("media")){
            JSONObject media = entities.getJSONArray("media").getJSONObject(0);
            //Log.i("Tweet", "media: " + media);
            tweet.type = media.getString("type");
            tweet.mediaUrl = media.getString("media_url");
            //Log.i("Tweet", "medUrl: " + tweet.mediaUrl);
        } else {
            tweet.type = "";
            tweet.mediaUrl = "empty";
        }*/

        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    public String getTimeDiff() {
        return TimeFormatter.getTimeDifference(createdAt);
    }

    public String getTimestamp() {
        return TimeFormatter.getTimeStamp(createdAt);
    }
}
