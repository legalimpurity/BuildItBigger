package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.JokeCallback;
import com.example.jokesSourceClass;
import com.legalimpurity.jokedisplaylibrary.JokeDisplayActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // This complicated approach was adopted because the step 3 in Instruction of the project said that the joke source library (Java library) should be pulling the jokes from the endpoint.
    // Java has no async task.
    // Also, we had to push the response back to the UI thread.
    public void tellJoke(final View view) {
        final JokeCallback newJokeCallback = new JokeCallback() {
            @Override
            public void returnLatestJoke(final String jokeobj) {

                String joke = "";

                try {
                    JSONObject responseObj = new JSONObject(jokeobj);
                    joke = responseObj.getString("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(!TextUtils.isEmpty(joke)) {
                    final String finalJoke = joke;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tellingJoke(finalJoke);
                        }
                    });
                }
                else
                {
                    Toast.makeText(view.getContext(),R.string.no_internet,Toast.LENGTH_LONG).show();
                }
            }
        };
        jokesSourceClass.getJoke(newJokeCallback);
    }

    public void tellingJoke(String joke) {
        Intent jokesDisplayMainIntent = new Intent(this,JokeDisplayActivity.class);
        Bundle extraData = new Bundle();
        extraData.putString(JokeDisplayActivity.JOKE_KEY,joke);
        jokesDisplayMainIntent.putExtras(extraData);
        startActivity(jokesDisplayMainIntent);
    }
}
