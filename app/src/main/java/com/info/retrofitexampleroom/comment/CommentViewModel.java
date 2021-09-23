package com.info.retrofitexampleroom.comment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.info.retrofitexampleroom.Comment;
import com.info.retrofitexampleroom.comment.CommentRepository;

import java.util.List;

public class CommentViewModel extends AndroidViewModel {

    private CommentRepository commentRepository;
    private LiveData<List<Comment>> getAllComments;
    public CommentViewModel(@NonNull Application application1) {
        super(application1);
        commentRepository=new CommentRepository(application1);
        getAllComments= commentRepository.getAllComments();
    }

    public void insert(List<Comment> list1){
        commentRepository.insert(list1);
    }

    public LiveData<List<Comment>> getAllComments(){
        return getAllComments;
    }
}
