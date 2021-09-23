package com.info.retrofitexampleroom.comment;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.info.retrofitexampleroom.Comment;

import java.util.List;
@Dao
public interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Comment> commentList);

    @Query("SELECT * FROM comment")
    LiveData<List<Comment>> getAllComments();

    @Query("DELETE FROM comment")
    void deleteAll();
    
}
