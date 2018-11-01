package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.doobs.androidjokes.JokeActivity;
import com.doobs.androidjokes.utils.JokeLibraryConstants;
import com.doobs.bestjokes.ScienceJokes;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;


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

    /**
     * handles the joke button click; returns a joke in a toast
     *
     * @param view
     */
    public void tellJoke(View view) {
        // P-05a: adding joke from library
        ScienceJokes scienceJokes = new ScienceJokes();

        // get the text
        String jokeText = scienceJokes.getPirateJoke();

//        Toast.makeText(this, "derp", Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, jokeText, Toast.LENGTH_LONG).show();
//
//        // P-05b: send joke to android library
//        Intent jokeIntent = new Intent(this, JokeActivity.class);
//        jokeIntent.putExtra(JokeLibraryConstants.ExtraKeys.JOKE_KEY, jokeText);
//        this.startActivity(jokeIntent);

        // P-05c: start the new GCP joke geting task
        new JokeGettingEndpointsAsyncTask().execute(new Pair<Context, String>(this, "joke"));
    }

    /**
     * will open a new activity if a non null joke string is provided
     *
     * @param jokeString
     */
    protected void openNewActivityWithJoke(String jokeString) {
        if (jokeString != null) {
            // P-05b: send joke to android library
            Intent jokeIntent = new Intent(this, JokeActivity.class);
            jokeIntent.putExtra(JokeLibraryConstants.ExtraKeys.JOKE_KEY, jokeString);
            this.startActivity(jokeIntent);

        } else {
            Toast.makeText(this, "No joke available; please check the network", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * inner class to fetch a joke from the GCP endpoint
     *
     */
    class JokeGettingEndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
        private MyApi myApiService = null;
        private Context context;

        @Override
        protected String doInBackground(Pair<Context, String>... params) {
            if(myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                myApiService = builder.build();
            }

            context = params[0].first;
            String name = params[0].second;

            try {
                return myApiService.sayHi(name).execute().getData();

            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            openNewActivityWithJoke(result);
        }
    }

}
