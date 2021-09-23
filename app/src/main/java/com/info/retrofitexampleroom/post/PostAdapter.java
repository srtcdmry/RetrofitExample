package com.info.retrofitexampleroom.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.info.retrofitexampleroom.Post;
import com.info.retrofitexampleroom.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private List<Post> postList;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_roe,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.id.setText("Id: "+post.getId());
        holder.userId.setText("User Id: "+post.getUserId());
        holder.text.setText("Text: "+post.getText());
        holder.title.setText("Title: "+post.getTitle());

    }

    public void getAllPosts(List<Post> postList){
        this.postList=postList;
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{
        public TextView id,userId,text,title;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.id);
            userId=itemView.findViewById(R.id.userId);
            text = itemView.findViewById(R.id.text);
            title = itemView.findViewById(R.id.title);

        }
    }
}
