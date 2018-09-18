package com.doobs.baking.util;

import com.doobs.baking.BuildConfig;

/**
 * Constants class to hold baking application condtants
 *
 * Created by mduby on 8/24/18.
 */
public class BakingAppConstants {
    /**
     * holds recipe html urls
     */
    public static final class WebUrls {
        public static final String RECIPEDB_URL = BuildConfig.RECIPEDB_URL;
    }

    /**
     * final string for the service actions
     *
     */
    public static final class ServiceActions {
        public static final String UPDATE_INGREDIENTS = "com.doobs.baking.widget.update_ingredients";
    }

    /**
     * holds the json key constants
     *
     */
    public static final class JsonKeys {
        public static final String ID                   = "id";
        public static final String NAME                 = "name";
        public static final String INGREDIENTS          = "ingredients";
        public static final String QUANTITY             = "quantity";
        public static final String MEASURE              = "measure";
        public static final String INGREDIENT           = "ingredient";
        public static final String STEPS                = "steps";
        public static final String DESCRIPTION          = "description";
        public static final String SHORT_DESCRIPTION    = "shortDescription";
        public static final String VIDEO_URL            = "videoURL";
        public static final String THUMBNAIL_URL        = "thumbnailURL";
        public static final String SERVINGS             = "servings";
        public static final String IMAGE                = "image";

    }

    /**
     * holds the keys for the activity extras
     */
    public static final class ActivityExtras {
        public static final String RECIPE_BEAN              = "recipeBean";
        public static final String RECIPE_STEP_BEAN         = "recipeStepBean";
        public static final String RECIPE_STEP_POSITION     = "recipeStepListPosition";

        public static final String MEDIA_PLAYER_POSITION    = "mediaPlayerPosition";
        public static final String MEDIA_PLAYER_STATE       = "mediaPlayerState";

        public static final String INGREDIENTS_ARRAY        = "ingredientsArray";
    }

    /**
     * holds the constants for the recipe step types
     */
    public static final class RecipeStepType {
        public static final String STEP                 = "step";
        public static final String INGREDIENT           = "ingredient";
    }
}
