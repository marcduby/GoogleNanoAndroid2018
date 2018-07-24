package com.doobs.movieposter.p02_movieposterv1.utils;

import junit.framework.TestCase;

import org.junit.Test;

import java.net.URL;

/**
 * Created by mduby on 7/9/18.
 */

public class MovieUtilsTest extends TestCase {

    @Test
    public void testGetMovieListSortedUri() {
        // instance variables
        String expectedString = "http://dude.com";
        String resultString = null;
        URL resultUrl = null;

        try {
            resultUrl = MovieUtils.getMovieListSortedUri(true, "test");

            // test
            assertNotNull(resultUrl);
            resultString = resultUrl.toString();
            assertNotNull(resultString);
            assertEquals(expectedString, resultString);

        } catch (MovieException exception) {
            fail("got movie exception: " + exception.getMessage());
        }
    }
}
