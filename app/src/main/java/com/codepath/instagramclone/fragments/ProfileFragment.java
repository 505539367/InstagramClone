package com.codepath.instagramclone.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.instagramclone.Post;
import com.codepath.instagramclone.ProfileAdapter;
import com.codepath.instagramclone.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */


public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    private ImageView ivUserIcon;
    private TextView tvUsername;
    private TextView tvPostNum;
    private RecyclerView rvProfile;
    private ProfileAdapter adapter;
    private List<Post> allposts;
    private SwipeRefreshLayout swipeContainer;

    private static int position = 0;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivUserIcon = view.findViewById(R.id.ivUserIcon);
        tvUsername = view.findViewById(R.id.tvPost_profile);
        tvPostNum = view.findViewById(R.id.tvPostNum);
        rvProfile = view.findViewById(R.id.rvProfile);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

        ParseUser currentUser = ParseUser.getCurrentUser();
        tvUsername.setText(currentUser.getUsername());
        ParseFile icon = currentUser.getParseFile("icon");
        if(icon != null){
            Glide.with(getContext()).load(icon.getUrl()).transform(new CenterInside(), new RoundedCorners(100)).into(ivUserIcon);
        }

        allposts = new ArrayList<>();
        adapter = new ProfileAdapter(getContext(),allposts);

        // Steps to use the recycler view:
        // 0.create layout for one row in the list
        // 1.create the adapter
        // 2. create the data source
        // 3. set the adapter on recycler view
        rvProfile.setAdapter(adapter);
        // 4. set the layout manager on the recycler view
        rvProfile.setLayoutManager(new LinearLayoutManager(getContext()));



        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateHomePosts();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        queryPosts();

    }

    private void populateHomePosts() {

        adapter.clear();
        setPosition(0);
        queryPosts();
        swipeContainer.setRefreshing(false);
    }

    private void queryPosts() {
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        // make most recently post show first
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for(Post post: posts){
                    Log.i(TAG, "Post:" + post.getDescription() + ", username:" + post.getUser() );
                }
                allposts.addAll(posts);
                tvPostNum.setText("Total " + String.valueOf(allposts.size()) +" posts");
                adapter.notifyDataSetChanged();
                rvProfile.smoothScrollToPosition(position);
            }
        });

    }

    public static void setPosition(int pos){
        position = pos;
    }

}