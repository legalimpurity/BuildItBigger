package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

import com.example.JokeCallback;
import com.example.jokesSourceClass;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rajatkhanna on 02/09/17.
 */

public class JokeCheck extends AndroidTestCase {
    public void test(){
        JokeCallback newJokeCallback = new JokeCallback() {
            @Override
            public void returnLatestJoke(final String jokeobj) {

                String joke = "";

                try {
                    JSONObject responseObj = new JSONObject(jokeobj);
                    joke = responseObj.getString("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                assertNotNull(joke);
                assertNotSame(joke,"");
            }
        };
        jokesSourceClass.getJoke(newJokeCallback);
    }
}
