package com.info.retrofitexampleroom.post;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.info.retrofitexampleroom.Post;

import java.util.List;

public class PostViewModel extends AndroidViewModel {

    private PostRepesitory postRepesitory;
    private LiveData<List<Post>> getAllPosts;
    public PostViewModel(@NonNull Application application) {
        super(application);
        postRepesitory=new PostRepesitory(application);
        getAllPosts= postRepesitory.getAllPosts();
    }

    public void insert(List<Post> list){
        postRepesitory.insert(list);
    }

    public LiveData<List<Post>> getAllPosts(){
        return getAllPosts;
    }

}
