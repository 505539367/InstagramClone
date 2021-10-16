package com.codepath.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "DetailAcitivity";

    private ImageView ivIcon;
    private ImageView ivPost;

    private TextView tvUsername;
    private TextView tvTime;
    private TextView tvDescription;
    private TextView tvLike;
    private TextView tvShared;

    private ImageButton ibDelete;
    private ImageButton ibLike;
    private ImageButton ibComment;
    private ImageButton ibShare;
    private ImageButton ivLike;
    private ImageButton ivComment;
    private ImageButton ivShare;

    private boolean like = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ivIcon = findViewById(R.id.ivIcon_Detail);
        ivPost = findViewById(R.id.ivPost);
        tvUsername = findViewById(R.id.tvUsername_Detail);
        tvTime = findViewById(R.id.tvPostTime_Detail);
        tvDescription = findViewById(R.id.tvDescription_Detail);
        tvLike = findViewById(R.id.tvLike);
        tvShared = findViewById(R.id.tvShare);
        ibDelete = findViewById(R.id.ibDelete);
        ibLike = findViewById(R.id.ibLikeClick);
        ibComment = findViewById(R.id.ibComment);
        ibShare = findViewById(R.id.ibShareClick);
        ivLike = findViewById(R.id.ivLike);
        ivComment = findViewById(R.id.ivComment);
        ivShare = findViewById(R.id.ivShare);

        setInfo();
    }

    private void setInfo() {
        Intent intent = getIntent();
        String fragment = intent.getStringExtra("Fragment");
        Post post = Parcels.unwrap(intent.getParcelableExtra("post"));


        ParseFile icon = post.getUser().getParseFile("icon");
        if(icon != null) {
            Glide.with(this).load(icon.getUrl()).transform(new CenterInside(), new RoundedCorners(100)).into(ivIcon);
        }
        ParseFile image = post.getImage();
        if(image != null) {
            Glide.with(this).load(image.getUrl()).into(ivPost);
        }

        tvUsername.setText(post.getUser().getUsername());
        tvTime.setText(post.getUpdatedAt().toString());
        tvDescription.setText(post.getDescription());
        tvLike.setText(post.getLike());
        tvShared.setText(post.getShared());

        if(fragment.equals("ProfileFragment")){
            ibDelete.setVisibility(ImageButton.VISIBLE);

            ibDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Delete();
                    post.deleteInBackground();
                    Log.i(TAG, "Item Deleted!");
                }
            });
        }

        if(like == true ){
            ivLike.setVisibility(ImageButton.VISIBLE);
        }
        ibLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivLike.setVisibility(ImageButton.VISIBLE);
                ibLike.setVisibility(ImageButton.INVISIBLE);
                post.incrementLike(ParseUser.getCurrentUser());;
                tvLike.setText(post.getLike());
                like = true;
                save(post);
            }
        });
        ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivLike.setVisibility(ImageButton.INVISIBLE);
                ibLike.setVisibility(ImageButton.VISIBLE);
                Log.i(TAG, "Like:"+post.getLike());
                post.decrementLike(post.getUser());
                Log.i(TAG, "Like:"+post.getLike());
                tvLike.setText(post.getLike());
                like = false;
                save(post);
            }
        });

        ibComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivComment.setVisibility(ImageButton.VISIBLE);
                ibComment.setVisibility(ImageButton.INVISIBLE);

            }
        });

        ibShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivShare.setVisibility(ImageButton.VISIBLE);
                ibShare.setVisibility(ImageButton.INVISIBLE);
                post.incrementShared();
                tvShared.setText(post.getShared());
            }
        });

    }

    private void Delete() {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("Delete", true);
        startActivity(i);
    }

    private void save(Post post){
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                }
                Log.i(TAG, "Post save was successful!!");
            }
        });
    }

    @Override
    public void onBackPressed() {
        String fragment = getIntent().getStringExtra("Fragment");
        int position = getIntent().getIntExtra("Position", 0);
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("Position", position);
        if(fragment.equals("ProfileFragment")) {
            Log.i(TAG, "IN Profile!");
            i.putExtra("Fragment", fragment);
            int positionProf = getIntent().getIntExtra("PositionProfile", 0);
            i.putExtra("PositionProfile", positionProf);
        }
        startActivity(i);
    }
}