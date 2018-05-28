package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

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
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * main method to add sandwich details to the detail view
     */
    private void populateUI(Sandwich sandwich) {
        // add the name
        this.nameTextView = (TextView)this.findViewById(R.id.main_name_tv);
        this.nameTextView.setText(sandwich.getMainName());

        // add the description
        this.descriptionTextView = (TextView)this.findViewById(R.id.description_tv);
        this.descriptionTextView.setText(sandwich.getDescription());

        // add the also known as
        this.akaTextView = (TextView)this.findViewById(R.id.also_known_tv);
        this.akaTextView.setText(this.buildListString(sandwich.getAlsoKnownAs()));

        // add the ingredient
        this.ingredientsTextView = (TextView)this.findViewById(R.id.ingredients_tv);
        this.ingredientsTextView.setText(this.buildListString(sandwich.getIngredients()));

        // add the origin
        this.originTextView = (TextView)this.findViewById(R.id.origin_tv);
        this.originTextView.setText(sandwich.getPlaceOfOrigin());
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
            for (String item : stringList) {
                buffer.append(item);
                buffer.append("\n");
            }
        }

        // return
        return buffer.toString();
    }
}
