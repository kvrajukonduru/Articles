package com.venkat.articles;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.venkat.articles.utils.AppConstants;


/*
   ArticleDetailsActivity.java class is responsible to view the details of article.
   Details : Article image,Title and  details of  the article.
   Features : 1) Fancy style of image view(Collapse style)
              2) Native Style of Detailed  Article info
 */

public class ArticleDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String imageUrl = getIntent().getStringExtra(AppConstants.INTENT_EXTRA_KEY_IMAGE_URL);
        ImageView image = (ImageView) findViewById(R.id.item_image);
        if (imageUrl != null) {
            image.setImageURI(Uri.parse(imageUrl));
        }

        TextView titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(getIntent().getStringExtra(AppConstants.INTENT_EXTRA_KEY_TITLE));

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getIntent().getStringExtra(AppConstants.INTENT_EXTRA_KEY_AUTHOR));
        //collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
