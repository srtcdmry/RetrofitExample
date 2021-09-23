package com.info.retrofitexampleroom.post;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.info.retrofitexampleroom.Post;

import java.util.List;


@Dao
public interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Post> postList);

    @Query("SELECT * FROM post")
    LiveData<List<Post>> getAllPosts();

    @Query("DELETE FROM post")
    void deleteAll();
}
