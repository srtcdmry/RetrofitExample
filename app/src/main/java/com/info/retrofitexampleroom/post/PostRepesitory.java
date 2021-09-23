package com.info.retrofitexampleroom.post;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.info.retrofitexampleroom.Post;

import java.util.List;

public class PostRepesitory {
    private PostDatabase database;
    private LiveData<List<Post>> getAllPosts;

    public PostRepesitory(Application application){
        database=PostDatabase.getInstance(application);
        getAllPosts=database.postDao().getAllPosts();

    }

    public void insert(List<Post> postList){
        new InsertAsynTask(database).execute(postList);
    }

    public LiveData<List<Post>> getAllPosts(){
        return getAllPosts;
    }

   static class InsertAsynTask extends AsyncTask<List<Post>,Void,Void>{
        private PostDao postDao;
        InsertAsynTask(PostDatabase postDatabase){
            postDao=postDatabase.postDao();
        }
        @Override
        protected Void doInBackground(List<Post>... lists) {
            postDao.insert(lists[0]);
            return null;
        }
    }
}
