package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

/**
 * Activity class to display the selected sandwich details
 *
 */
public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // text views
    private TextView nameTextView;
    private TextView akaTextView;
    private TextView originTextView;
    private TextView descriptionTextView;
    private TextView ingredientsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.baseline_fastfood_black_18dp)
                .error(R.drawable.baseline_fastfood_black_18dp)
                .into(ingredientsIv);


        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * main method to add sandwich details to the detail view
     *
     * @param sandwich                  the sandwich to display
     */
    private void populateUI(Sandwich sandwich) {
        // add the name
        nameTextView = findViewById(R.id.main_name_tv);
        if (sandwich.getMainName().isEmpty()) {
            TextView labelView = findViewById(R.id.main_origin_label_tv);
            nameTextView.setVisibility(View.GONE);
            labelView.setVisibility(View.GONE);

        } else {
            nameTextView.setText(sandwich.getMainName());
        }

        // add the description
        descriptionTextView = findViewById(R.id.description_tv);
        if (sandwich.getDescription().isEmpty()) {
            TextView labelView = findViewById(R.id.description_label_tv);
            descriptionTextView.setVisibility(View.GONE);
            labelView.setVisibility(View.GONE);

        } else {
            descriptionTextView.setText(sandwich.getDescription());
        }

        // add the also known as
        // if not aka data, hide the text view
        akaTextView = findViewById(R.id.also_known_tv);
        if (sandwich.getAlsoKnownAs().isEmpty()) {
            TextView labelView = findViewById(R.id.also_known_label_tv);
            akaTextView.setVisibility(View.GONE);
            labelView.setVisibility(View.GONE);

        } else {
            akaTextView.setText(this.buildListString(sandwich.getAlsoKnownAs()));
        }

        // add the ingredient
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        if (sandwich.getIngredients().isEmpty()) {
            TextView labelView = findViewById(R.id.ingredients_label_tv);
            ingredientsTextView.setVisibility(View.GONE);
            labelView.setVisibility(View.GONE);

        } else {
            ingredientsTextView.setText(this.buildListString(sandwich.getIngredients()));
        }

        // add the origin
        originTextView = findViewById(R.id.origin_tv);
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            TextView labelView = findViewById(R.id.main_origin_label_tv);
            originTextView.setVisibility(View.GONE);
            labelView.setVisibility(View.GONE);

        } else {
            originTextView.setText(sandwich.getPlaceOfOrigin());
        }
    }

    /**
     * method to build a CR delimited string from the list of strings
     *
     * @param stringList
     * @return
     */
    private String buildListString(List<String> stringList) {
        // local variables
        StringBuffer buffer = new StringBuffer();

        // loop through string
        if (stringList != null) {
            for (int i = 0; i < stringList.size(); i++) {
                buffer.append(stringList.get(i));
                if (i < (stringList.size() -1)) {
                    buffer.append("\n");
                }
            }
        }

        // return
        return buffer.toString();
    }
}
