package com.codepath.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.instagramclone.fragments.ComposeFragment;
import com.codepath.instagramclone.fragments.PostsFragment;
import com.codepath.instagramclone.fragments.ProfileFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainAcitivity";

    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                int position;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        position = getIntent().getIntExtra("Position", 0);
                        PostsFragment.setPosition(position);
                        fragment = new PostsFragment();
                        break;
                    case R.id.action_compose:
                        fragment = new ComposeFragment();
                        break;
                    case R.id.action_profile:
                    default:
                        position = getIntent().getIntExtra("PositionProfile", 0);
                        ProfileFragment.setPosition(position);
                        fragment = new ProfileFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        // Set selection
        String fragment = getIntent().getStringExtra("Fragment");
        boolean delete = getIntent().getBooleanExtra("Delete", false);
        if(delete == true ||fragment!=null){
                bottomNavigationView.setSelectedItemId(R.id.action_profile);
                Log.i(TAG, "Go to profile page");
        }else {
            bottomNavigationView.setSelectedItemId(R.id.action_home);
            Log.i(TAG, "Go to Home Page");
        }

    }


    // display icon on menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // when log out icon has been select
        if(item.getItemId() == R.id.Logout){
            Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show();
            //Navigate to the login activity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            ParseUser.logOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}