package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import com.codepath.apps.restclienttemplate.databinding.ActivityDetailBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {
    //data binding
    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        binding.setTweet(tweet);

        binding.tvScreenName.setText(String.format("@%s", tweet.user.screenName));

    }

    @BindingAdapter("detailProfileImage")
    public static void setProfileImage(ImageView image, String Url) {
        Context context = image.getContext();
        Glide.with(context).load(Url)
                .transform(new CircleCrop())
                .into(image);
    }

        /*int radius = 30;
        if(tweet.type.equals("photo")) {
            Log.d("DetailAct", "Image set to visible " + tweet.mediaUrl);
            ivMediaUrl.setVisibility(View.VISIBLE);
            //if (tweet.type == "video") {

            //} else {

            Glide.with(this).load(tweet.mediaUrl)
                    .transform(new FitCenter(), new RoundedCorners(radius))
                    .into(ivMediaUrl);
            //}
        } else {
            Log.d("DetailAct", "Image set to invisible " + tweet.mediaUrl);
            ivMediaUrl.setVisibility(View.GONE);
        }
        Log.d("DetailAct", "After Image set to visible");*/


}