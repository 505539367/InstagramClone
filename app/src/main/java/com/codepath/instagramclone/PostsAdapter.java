package com.codepath.instagramclone;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post>posts){
        this.context = context;
        this.posts = posts;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post, position);

    }
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout container;
        private ImageView ivIcon;
        private TextView tvUsername;
        private TextView tvPostTime;
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvLike;
        private TextView tvShared;
        /*
        private ImageButton ibShare;
        private ImageButton ibLike;
        private ImageButton ibComment;
         */

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.Container);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvPostTime = itemView.findViewById(R.id.tvPostTime);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);

            tvLike = itemView.findViewById(R.id.tvPostLike);
            tvShared = itemView.findViewById(R.id.tvPostShare);

            /*
            ibLike = itemView.findViewById(R.id.ibPostLikeClick);
            ibComment = itemView.findViewById(R.id.ibPostComment);
            ibShare = itemView.findViewById(R.id.ibPostShareClick);
            */

        }
        public void bind (Post post, int position){
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            tvPostTime.setText(post.getUpdatedAt().toString());
            tvLike.setText(post.getLike());
            tvShared.setText(post.getShared());

            ParseFile icon = post.getUser().getParseFile("icon");
            if(icon != null) {
                Glide.with(context).load(icon.getUrl()).transform(new CenterInside(), new RoundedCorners(100)).into(ivIcon);
            }
            ParseFile image = post.getImage();
            if(image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("Fragment","PostsFragment");
                    i.putExtra("Position",position);
                    i.putExtra("post", Parcels.wrap(post));
                    context.startActivity(i);

                }
            });

        }

    }

}
