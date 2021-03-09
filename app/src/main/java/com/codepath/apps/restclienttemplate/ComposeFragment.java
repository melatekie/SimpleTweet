package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

import static android.app.Activity.RESULT_OK;

public class ComposeFragment extends DialogFragment {

    public static final String TAG = "ComposeFragment";
    public static final int MAX_TWEET_LENGTH = 140;
    EditText etCompose;
    Button btnTweet;

    TwitterClient client;
    public ComposeFragment() { }

    public static ComposeFragment newInstance() {
        ComposeFragment fragment = new ComposeFragment();


        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        client = TwitterApp.getRestClient(view.getContext());

        etCompose = view.findViewById(R.id.etCompose);
        btnTweet = view.findViewById(R.id.btnTweet);
        btnTweet.setEnabled(false);

        // Fetch arguments from bundle and set title

        getDialog().setTitle("New Tweet");

        // Show soft keyboard automatically and request focus to field
        etCompose.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        //Displays character count
        etCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i(TAG, "Fires right before text is changing: Called beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right before text is changing
                Log.i(TAG, "Fires right as the text is being changed: Called onTextChanged");

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (text.isEmpty()) {
                    btnTweet.setEnabled(false);
                } else btnTweet.setEnabled(text.length() <= MAX_TWEET_LENGTH);
                Log.i(TAG, "Fires right after the text has changed: Called afterTextChanged");
            }
        });

        //Set click listener on button
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tweetContent = etCompose.getText().toString();
                if (tweetContent.isEmpty()) {
                    Toast.makeText(v.getContext(),"Sorry, your tweet cannot be empty", Toast.LENGTH_LONG).show();

                    return;
                }
                if (tweetContent.length() > MAX_TWEET_LENGTH) {
                    Toast.makeText(v.getContext(),"Sorry, your tweet is too long", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(v.getContext(), tweetContent, Toast.LENGTH_LONG).show();
                //Make an API call to Twitter to publish the tweet
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG, "onSuccess to publish tweet");
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i(TAG, "Published Tweet says: " + tweet.body);
                            Intent intent = new Intent();
                            intent.putExtra("tweet", Parcels.wrap(tweet));
                            //set result code and bundle data for response
                            getActivity().setResult(RESULT_OK, intent);
                            //closes the activity, pass data to parent
                            getActivity().finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "onFailure to publish tweet", throwable);
                    }
                });
            }
        });
    }
}
