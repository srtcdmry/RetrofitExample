package com.info.retrofitexampleroom.comment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.info.retrofitexampleroom.Comment;
import com.info.retrofitexampleroom.R;
import com.info.retrofitexampleroom.comment.CommentAdapter;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    private Context context1;
    private List<Comment> commentList;

    public CommentAdapter(Context context1, List<Comment> commentList) {
        this.context1 = context1;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent2, int viewType1) {
        return new CommentAdapter.CommentViewHolder(LayoutInflater.from(parent2.getContext())
                .inflate(R.layout.each_roe2,parent2,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder1, int position2) {
        Comment comment = commentList.get(position2);
        holder1.id.setText("Id: "+comment.getId());
        holder1.postId.setText("Post Id: "+comment.getPostId());
        holder1.text.setText("Name: "+comment.getName());
       // holder1.email.setText("Email: "+comment.getEmail());
        //holder1.text.setText("Text: "+comment.getText());

    }

    public void getAllComments(List<Comment> commentList){
        this.commentList=commentList;
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder{
        public TextView id,postId,name,email,text;
        public CommentViewHolder(@NonNull View itemView1) {
            super(itemView1);
            id=itemView1.findViewById(R.id.id2);
            postId=itemView1.findViewById(R.id.userId2);
            name=itemView1.findViewById(R.id.name2);
           // text = itemView1.findViewById(R.id.text2);
           // email = itemView1.findViewById(R.id.email2);

        }
    }
}
    
    

