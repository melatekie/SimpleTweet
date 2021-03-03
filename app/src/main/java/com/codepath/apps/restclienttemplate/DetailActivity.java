package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    ImageView ivProfileImage;
    TextView tvBody;
    TextView tvName;
    TextView tvScreenName;
    TextView tvTimestamp;
    ImageView ivMediaUrl;
    //VideoView videoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvBody = findViewById(R.id.tvBody);
        tvName = findViewById(R.id.tvName);
        tvScreenName = findViewById(R.id.tvScreenName);
        tvTimestamp = findViewById(R.id.tvTimestamp);
        ivMediaUrl = findViewById(R.id.ivMediaUrl);

        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        Glide.with(this).load(tweet.user.profileImageUrl)
                .transform(new CircleCrop())
                .into(ivProfileImage);
        tvBody.setText(tweet.body);
        tvName.setText(tweet.user.name);
        tvScreenName.setText(tweet.user.screenName);
        tvTimestamp.setText(tweet.getFormattedTimestamp());
        Log.d("DetailAct", "Before Image set to visible");

        int radius = 30;
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
        Log.d("DetailAct", "After Image set to visible");

    }
}