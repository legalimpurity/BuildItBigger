package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class jokesSourceClass {


    public static void getJoke(final JokeCallback call) {
        new Thread(new Runnable() {
            public void run() {
                executePost(call,"http://192.168.0.3:8080/_ah/api/myApi/v1/sayHi/tell a joke");
            }
        }).start();
    }

    private static void executePost(JokeCallback call, String targetURL) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            call.returnLatestJoke(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            call.returnLatestJoke("");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
