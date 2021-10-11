package com.codepath.instagramclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("Qs7hkYDuk8L0NyjPvXw7Uhjq2WyCmMczaDXFUXKj")
                .clientKey("IIhjlqcFsHFSkWEp2k6CadTna0GIy40yKktxYH6w")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
