/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.legalimpurity.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.Random;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.legalimpurity.com",
                ownerName = "backend.legalimpurity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        if(name.equalsIgnoreCase("tell a joke"))
            response.setData(getJoke());
        else
            response.setData("Hi, "+name);
        return response;
    }

    public static String getJoke(){
        String[] jokearray =  new String[]{
                "Q: What computer sings the best?\nA: A Dell.",
                "Q: How many light bulbs does it take to change a light bulb?\nA: They can't; they're not bright enough.",
                "I put so much more effort into naming my first Wi-Fi than my first child.",
                "The cool part about naming your kid is you don’t have to add six numbers to make sure the name is available."
        };

        Random rn = new Random();
        return jokearray[rn.nextInt(jokearray.length)];
    }

}
