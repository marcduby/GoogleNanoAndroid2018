package com.udacity.gradle.builditbigger.backend;

import com.doobs.bestjokes.ScienceJokes;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
//        MyBean response = new MyBean();
//        response.setData("Hi sir, " + name);
//
//        return response;

        // local variables
        ScienceJokes scienceJokes = new ScienceJokes();
        MyBean joke = new MyBean();

        // get the joke
        joke.setData(scienceJokes.getPirateJoke());

        return joke;
    }



    /**
     * returns the joke
     *
     * @param name
     * @return
     */
    @ApiMethod(name = "getJoke")
    public MyBean getJoke(@Named("name") String name) {
        // local variables
        ScienceJokes scienceJokes = new ScienceJokes();
        MyBean joke = new MyBean();

        // get the joke
        joke.setData(scienceJokes.getPirateJoke());

        return joke;
    }
}
