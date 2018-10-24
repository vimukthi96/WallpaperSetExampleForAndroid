package com.example.vimukthi.wallpapersetexampleforandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PostDetailActivity extends AppCompatActivity {

    TextView txtTitle,txtDes;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle("Posts Deatils");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        txtTitle =(TextView)findViewById(R.id.titleTxt);
        txtDes=(TextView)findViewById(R.id.desTxt);
        imgView=(ImageView)findViewById(R.id.titleImage);


        byte[] bytes =getIntent().getByteArrayExtra("image");
        String title=getIntent().getStringExtra("title");
        String des=getIntent().getStringExtra("des");
        Bitmap btmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);

        txtTitle.setText(title);
        txtDes.setText(des);
        imgView.setImageBitmap(btmap);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
