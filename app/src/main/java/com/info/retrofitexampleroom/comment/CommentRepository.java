package com.info.retrofitexampleroom.comment;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.info.retrofitexampleroom.Comment;
import com.info.retrofitexampleroom.comment.CommentDao;
import com.info.retrofitexampleroom.comment.CommentDatabase;
import com.info.retrofitexampleroom.comment.CommentRepository;

import java.util.List;

public class CommentRepository {
    private CommentDatabase database1;
    private LiveData<List<Comment>> getAllComments;

    public CommentRepository(Application application1){
        database1=CommentDatabase.getInstance(application1);
        getAllComments=database1.commentDao().getAllComments();

    }

    public void insert(List<Comment> commentList){
        new CommentRepository.InsertAsynTask(database1).execute(commentList);
    }

    public LiveData<List<Comment>> getAllComments(){
        return getAllComments;
    }

    static class InsertAsynTask extends AsyncTask<List<Comment>,Void,Void> {
        private CommentDao commentDao;
        InsertAsynTask(CommentDatabase commentDatabase){
            commentDao=commentDatabase.commentDao();
        }
        @Override
        protected Void doInBackground(List<Comment>... lists) {
            commentDao.insert(lists[0]);
            return null;
        }
    }
}


