package com.codepath.instagramclone;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_KEY = "createdAt";
    public static final String KEY_LIKE = "Like";
    public static final String KEY_SHARED = "Shared";


    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile){
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public void setuser(ParseUser user){
        put(KEY_USER,user);
    }

    public String getLike(){
        return getString(KEY_LIKE);
    }

    public void setLike(String like){
        put(KEY_LIKE, like);
    }

    public void incrementLike(ParseUser user){
        int like = Integer.parseInt(getString(KEY_LIKE));
        like++;
        setLike(Integer.toString(like));

    }
    public void decrementLike(ParseUser user) {
        int like = Integer.parseInt(getString(KEY_LIKE));
        like--;
        setLike(Integer.toString(like));
    }

    public String getShared(){
        return getString(KEY_SHARED);
    }

    public void setShared(String share){
        put(KEY_SHARED, share);
    }

    public void incrementShared(){
        int share = Integer.parseInt(getString(KEY_SHARED));
        share++;
        setShared(Integer.toString(share));
    }

    public void decrementShared() {
        int share = Integer.parseInt(getString(KEY_SHARED));
        share--;
        setShared(Integer.toString(share));
    }

}
