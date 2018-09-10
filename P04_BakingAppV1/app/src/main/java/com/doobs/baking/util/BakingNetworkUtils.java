package com.doobs.baking.util;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.util.Scanner;

/**
 * Network utility class
 *
 * Created by mduby on 9/9/18.
 */

public class BakingNetworkUtils {
    public static final String TAG = BakingNetworkUtils.class.getName();

    /**
     * test the network connection
     *
     * @throws BakingException
     */
    public static void testNetwork() throws BakingException {
        try {
            int timeoutMilliseconds = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

            sock.connect(sockaddr, timeoutMilliseconds);
            sock.close();

        } catch (IOException exception) {
            String message = "Got network exception: " + exception;
            Log.e(TAG, message);
            throw new BakingException(message);
        }

    }

    /**
     * connect to the REST service and return the results of the query
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String getResponseFromHttpUrl(URL url) throws BakingException {
        // local variables
        HttpURLConnection httpURLConnection = null;
        String responseString = null;

        // connect to the url
        try {
            httpURLConnection = (HttpURLConnection)url.openConnection();

            // open the stream
            try {
                InputStream inputStream = httpURLConnection.getInputStream();

                // get the response string
                Scanner scanner = new Scanner(inputStream);
                scanner.useDelimiter("\\A");

                if (scanner.hasNext()) {
                    responseString = scanner.next();
                }

            } finally {
                httpURLConnection.disconnect();
            }

        } catch (IOException exception) {
            String message = "Got network exception: " + exception;
            Log.e(TAG, message);
            throw new BakingException(message);
        }

        // return
        return responseString;
    }


}
