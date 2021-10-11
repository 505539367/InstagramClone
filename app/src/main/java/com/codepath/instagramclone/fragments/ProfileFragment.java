package com.codepath.instagramclone.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.instagramclone.Post;
import com.codepath.instagramclone.ProfileAdapter;
import com.codepath.instagramclone.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */


public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    private TextView tvUsername;
    private TextView tvPostNum;
    private RecyclerView rvProfile;
    private ProfileAdapter adapter;
    private List<Post> allposts;

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
        tvUsername = view.findViewById(R.id.tvUsername_profile);
        tvPostNum = view.findViewById(R.id.tvPostNum);
        rvProfile = view.findViewById(R.id.rvProfile);

        ParseUser currentUser = ParseUser.getCurrentUser();
        tvUsername.setText(currentUser.getUsername());

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
        queryPosts();
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
            }
        });
    }
}