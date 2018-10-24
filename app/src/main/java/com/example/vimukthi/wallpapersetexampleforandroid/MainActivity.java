package com.example.vimukthi.wallpapersetexampleforandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle("Posts List");

        recyclerView =findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference("Data");
    }

    //search data
    private  void firebaseSearch(String searchText){
        Query firebaseSearchQuery =databaseReference.orderByChild("title").startAt(searchText).endAt(searchText +"\uf8ff");
        FirebaseRecyclerAdapter<Model,ViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<Model, ViewHolder>(
                        Model.class,R.layout.row,ViewHolder.class,firebaseSearchQuery
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Model model, int position) {
                        viewHolder.setDetails(getApplicationContext(),model.getTitle(),model.getDescription(),model.getImage());

                    }

                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        ViewHolder viewHolder = super.onCreateViewHolder(parent,viewType);
                        viewHolder.setClickListner(new ViewHolder.ClickListner() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                        TextView txtTitle =view.findViewById(R.id.txttitle);
                                        TextView txtDes =view.findViewById(R.id.txtdes);
                                        ImageView imgView=view.findViewById(R.id.imgtitle);

                                        String mTitle =txtTitle.getText().toString();
                                        String mDes =txtDes.getText().toString();
                                        Drawable drawable=imgView.getDrawable();
                                        Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();

                                        Intent intent =new Intent(getApplicationContext(),PostDetailActivity.class);
                                        ByteArrayOutputStream stream=new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                                        byte[] bytes =stream.toByteArray();
                                        intent.putExtra("image",bytes);
                                        intent.putExtra("title",mTitle);
                                        intent.putExtra("des",mDes);
                                        startActivity(intent);


                                    }

                                    @Override
                                    public void onItemLongClick(View view, int position) {

                                    }
                                });

                                return super.onCreateViewHolder(parent, viewType);
                            }
                        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item =menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();
        if(id == R.id.action_setting){
            //TODO
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Model,ViewHolder> firebaseRecyclerAdapter =new FirebaseRecyclerAdapter<Model, ViewHolder>(
                Model.class,R.layout.row,ViewHolder.class,databaseReference
        ) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, Model model, int position) {
                viewHolder.setDetails(getApplicationContext(),model.getTitle(),model.getDescription(),model.getImage());
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                ViewHolder viewHolder = super.onCreateViewHolder(parent,viewType);
                viewHolder.setClickListner(new ViewHolder.ClickListner() {
                    @Override
                    public void onItemClick(View view, int position) {

                        TextView txtTitle =view.findViewById(R.id.txttitle);
                        TextView txtDes =view.findViewById(R.id.txtdes);
                        ImageView imgView=view.findViewById(R.id.imgtitle);

                        String mTitle =txtTitle.getText().toString();
                        String mDes =txtDes.getText().toString();
                        Drawable drawable=imgView.getDrawable();
                        Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();

                        Intent intent =new Intent(getApplicationContext(),PostDetailActivity.class);
                        ByteArrayOutputStream stream=new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                         byte[] bytes =stream.toByteArray();
                         intent.putExtra("image",bytes);
                         intent.putExtra("title",mTitle);
                         intent.putExtra("des",mDes);
                         startActivity(intent);


                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });

                return super.onCreateViewHolder(parent, viewType);
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
