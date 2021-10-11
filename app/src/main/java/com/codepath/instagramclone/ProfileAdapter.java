package com.codepath.instagramclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.parse.ParseFile;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder>{
    private Context context;
    private List<Post> posts;

    public ProfileAdapter(Context context, List<Post>posts){
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post_user, parent, false);
        return new ProfileAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivIcon;
        private TextView tvUsername;
        private ImageView ivImage_user;
        private TextView tvDescription_user;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage_user = itemView.findViewById(R.id.ivImage_user);
            tvDescription_user = itemView.findViewById(R.id.tvDescription_user);
            ivIcon = itemView.findViewById(R.id.ivIcon_user);
            tvUsername = itemView.findViewById(R.id.tvUsername_profile);
        }
        public void bind (Post post){
            tvDescription_user.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());

            ParseFile icon = post.getUser().getParseFile("icon");
            if(icon != null) {
                Glide.with(context).load(icon.getUrl()).transform(new CenterInside(), new RoundedCorners(100)).into(ivIcon);
            }

            ParseFile image = post.getImage();
            if(image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage_user);
            }

        }
    }
}
