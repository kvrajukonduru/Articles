package com.venkat.articles.carousel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.venkat.articles.R;

public class ProductDetailsActivity extends AppCompatActivity {

    public static final String PARAMA_URL="ProductDetailsActivity.URL";
    public static final String PARAMA_TITLE1="ProductDetailsActivity.title1";
    public static final String PARAMA_TITLE2="ProductDetailsActivity.title2";

    public static void newIntent(Context ctx, String url, String title1, String title2) {
        Intent intent=new Intent(ctx,ProductDetailsActivity.class);
        intent.putExtra(PARAMA_URL,url);
        intent.putExtra(PARAMA_TITLE1,title1);
        intent.putExtra(PARAMA_TITLE2,title2);
        ctx.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SimpleDraweeView dv= (SimpleDraweeView) findViewById(R.id.image);
        TextView title= (TextView) findViewById(R.id.title1);
        TextView title2= (TextView) findViewById(R.id.title2);
        dv.setImageURI(Uri.parse(getIntent().getStringExtra(ProductDetailsActivity.PARAMA_URL)));
        title.setText(getIntent().getStringExtra(ProductDetailsActivity.PARAMA_TITLE1));
        title2.setText(getIntent().getStringExtra(ProductDetailsActivity.PARAMA_TITLE2));
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
