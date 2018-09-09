package com.doobs.baking;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.doobs.baking.adapter.RecipeStepRecyclerAdapter;
import com.doobs.baking.bean.RecipeBean;
import com.doobs.baking.bean.RecipeStepBean;
import com.doobs.baking.util.BakingAppConstants;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

/**
 * Fragment class to display the recipe step and ingredient lists
 *
 * Created by mduby on 8/26/18.
 */

public class RecipeStepDetailFragment extends Fragment {
    // instance variables
    private final String TAG = this.getClass().getName();
    private RecipeStepBean recipeStepBean;
    private SimpleExoPlayer simpleExoPlayer;
    private SimpleExoPlayerView simpleExoPlayerView;
    private long exoplayerPosition = -1;

    /**
     * default constructor
     *
     */
    public RecipeStepDetailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // local variables
        View rootView = null;

        // if state was saved
        if (savedInstanceState != null) {
            this.recipeStepBean = savedInstanceState.getParcelable(BakingAppConstants.ActivityExtras.RECIPE_STEP_BEAN);

            // set the exoplayer position
            long temp = savedInstanceState.getLong(BakingAppConstants.ActivityExtras.MEDIA_PLAYER_POSITION);
            this.exoplayerPosition = savedInstanceState.getLong(BakingAppConstants.ActivityExtras.MEDIA_PLAYER_POSITION);
        }

        // inflate the view
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // get the root view and inflate it
            rootView = inflater.inflate(R.layout.fragment_recipe_detail_landscape, container, false);

        } else {
            // get the root view and inflate it
            rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

            // get the text view and how the name
            TextView nameTextView = rootView.findViewById(R.id.recipe_step_detail_name_fragment_tv);
            nameTextView.setText(recipeStepBean.getShortDescription());

            // get the text view and how the description
            TextView descriptionTextView = rootView.findViewById(R.id.recipe_step_detail_description_fragment_tv);

            // skip setting description if ssame as short description
            if ((recipeStepBean.getShortDescription() != null) && (recipeStepBean.getDescription() != null) &&
                    !recipeStepBean.getShortDescription().equalsIgnoreCase(recipeStepBean.getDescription())) {
                descriptionTextView.setText(recipeStepBean.getDescription());
            }
        }

        // create the exo player
        this.simpleExoPlayerView = (SimpleExoPlayerView)rootView.findViewById(R.id.exoplayer_view);

        // create the uri from the recipe url string
        if (recipeStepBean.getVideoUrl() != null) {
            if (recipeStepBean.getVideoUrl().length() > 0) {
                Uri reciperUri = Uri.parse(recipeStepBean.getVideoUrl());

                // set the uri on the media player
                // initialize the player
                this.initializePlayer(rootView.getContext(), reciperUri, savedInstanceState);

//                Toast.makeText(rootView.getContext(), "started video: " + recipeStepBean.getVideoUrl(), Toast.LENGTH_LONG).show();

            } else {
                // if thumbnail, display the image

                Toast.makeText(rootView.getContext(), "No video for this recipe step", Toast.LENGTH_SHORT).show();
            }
        }

        // return the view
        return rootView;
    }

    /**
     * initialize the media player
     *
     * @param context
     * @param mediaUri
     */
    private void initializePlayer(Context context, Uri mediaUri, Bundle savedInstanceState) {
        if (this.simpleExoPlayer == null) {
            // create the exoplayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            this.simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            this.simpleExoPlayerView.setPlayer(this.simpleExoPlayer);

            // prepare the media source
            String userAgent = Util.getUserAgent(context, "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(context, userAgent),
                    new DefaultExtractorsFactory(), null, null);

            this.simpleExoPlayer.prepare(mediaSource);

            if (this.exoplayerPosition != -1) {
                this.simpleExoPlayer.seekTo(this.exoplayerPosition);
                this.simpleExoPlayer.setPlayWhenReady(savedInstanceState.getBoolean(BakingAppConstants.ActivityExtras.MEDIA_PLAYER_STATE));

            } else {
                this.simpleExoPlayer.setPlayWhenReady(true);
            }
        }
    }

    /**
     * releases the media player
     *
     */
    private void releasePlayer() {
        // NOTE - also call this in onPause and onStop when the app is not visible
        if (this.simpleExoPlayer != null) {
            this.simpleExoPlayer.stop();
            this.simpleExoPlayer.release();
            this.simpleExoPlayer = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // release the media player
        this.releasePlayer();;
    }

    @Override
    public void onStop() {
        super.onStop();

        // release the media player
        this.releasePlayer();;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // release the media player
        this.releasePlayer();;
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//
//        // release the media player
//        this.releasePlayer();;
//    }

    /**
     * for state changes
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(BakingAppConstants.ActivityExtras.RECIPE_STEP_BEAN, this.recipeStepBean);

        // save the exoplayer state
        if (this.simpleExoPlayer != null) {
            long currentPosition = this.simpleExoPlayer.getCurrentPosition();
            boolean readyToPlay = this.simpleExoPlayer.getPlayWhenReady();
            outState.putLong(BakingAppConstants.ActivityExtras.MEDIA_PLAYER_POSITION, currentPosition);
            outState.putBoolean(BakingAppConstants.ActivityExtras.MEDIA_PLAYER_STATE, readyToPlay);
        }
    }

    public void setRecipeStepBean(RecipeStepBean recipeStepBean) {
        this.recipeStepBean = recipeStepBean;
    }
}
